/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smskelley.politiface;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;
import android.view.WindowInsets;

import com.smskelley.politiface.application.App;
import com.smskelley.politiface.application.PolitiFaceApp;
import com.smskelley.politiface.models.DaggerModels;
import com.smskelley.politiface.models.Models;
import com.smskelley.politiface.models.time.TimeModel;
import com.smskelley.politiface.views.CanvasDrawable;
import com.smskelley.politiface.views.DaggerViews;
import com.smskelley.politiface.views.Views;

import java.lang.ref.WeakReference;

/**
 * Analog watch face with a ticking second hand. In ambient mode, the second hand isn't shown. On
 * devices with low-bit ambient mode, the hands are drawn without anti-aliasing in ambient mode.
 */
public class PolitiFace extends CanvasWatchFaceService {
  /**
   * Update rate in milliseconds for interactive mode. We update once a second to advance the
   * second hand.
   */
  private static final long TARGET_FPS = 60;

  /**
   * Handler message id for updating the time periodically in interactive mode.
   */
  private static final int MSG_UPDATE_TIME = 0;

  @Override
  public Engine onCreateEngine() {
    return new Engine();
  }

  private static class EngineHandler extends Handler {
    private final WeakReference<PolitiFace.Engine> mWeakReference;

    public EngineHandler(PolitiFace.Engine reference) {
      mWeakReference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
      PolitiFace.Engine engine = mWeakReference.get();
      if (engine != null) {
        switch (msg.what) {
          case MSG_UPDATE_TIME:
            engine.handleUpdateTimeMessage();
            break;
        }
      }
    }
  }

  private class Engine extends CanvasWatchFaceService.Engine {
    final Handler mUpdateTimeHandler = new EngineHandler(this);
    Paint mBackgroundPaint;
    boolean mAmbient;

    /**
     * Whether the display supports fewer bits for each color in ambient mode. When true, we
     * disable anti-aliasing in ambient mode.
     */
    boolean mLowBitAmbient;
    private TimeModel timeModel;
    private App app;
    private Models models;
    private Views views;
    private CanvasDrawable[] canvasDrawables;

    @Override
    public void onCreate(SurfaceHolder holder) {
      super.onCreate(holder);

      setWatchFaceStyle(new WatchFaceStyle.Builder(PolitiFace.this)
          .setCardPeekMode(WatchFaceStyle.PEEK_MODE_SHORT)
          .setBackgroundVisibility(WatchFaceStyle.BACKGROUND_VISIBILITY_INTERRUPTIVE)
          .setShowSystemUiTime(false)
          .build());

      app = ((PolitiFaceApp) getApplication()).component();
      models = DaggerModels.builder()
          .app(app)
          .build();

      timeModel = models.time();
      views = DaggerViews.builder()
          .app(app)
          .models(models)
          .build();

      mBackgroundPaint = views.backgroundPaint();
      canvasDrawables = new CanvasDrawable[]{
          views.background(),
          views.clinton(),
          views.trump(),
          views.hourHand(),
          views.minuteHand(),
          views.secondHand(),
          views.cap(),
      };
    }

    @Override
    public void onDestroy() {
      mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
      super.onDestroy();
    }

    @Override
    public void onApplyWindowInsets(WindowInsets insets) {
      int chinSize = insets.getStableInsetBottom();
      boolean isRound = insets.isRound();

      for (CanvasDrawable drawable : canvasDrawables) {
        drawable.setChinSizePx(chinSize);
        drawable.setIsRound(isRound);
      }

      super.onApplyWindowInsets(insets);
    }

    @Override
    public void onPropertiesChanged(Bundle properties) {
      super.onPropertiesChanged(properties);
      mLowBitAmbient = properties.getBoolean(PROPERTY_LOW_BIT_AMBIENT, false);
    }

    @Override
    public void onTimeTick() {
      super.onTimeTick();
      invalidate();
    }

    @Override
    public void onAmbientModeChanged(boolean inAmbientMode) {
      super.onAmbientModeChanged(inAmbientMode);
      if (mAmbient != inAmbientMode) {
        mAmbient = inAmbientMode;
        invalidate();
      }

      // Whether the timer should be running depends on whether we're visible (as well as
      // whether we're in ambient mode), so we may need to start or stop the timer.
      updateTimer();
    }

    @SuppressWarnings("ForLoopReplaceableByForEach")
    @Override
    public void onDraw(Canvas canvas, Rect bounds) {
      // Draw the background.
      if (isInAmbientMode()) {
        canvas.drawColor(Color.BLACK);
      }

      timeModel.update(System.currentTimeMillis());

      // Find the center. Ignore the window insets so that, on round watches with a
      // "chin", the watch face is centered on the entire screen, not just the usable
      // portion.
      float centerX = bounds.width() / 2f;
      float centerY = bounds.height() / 2f;

      for (int i = 0; i < canvasDrawables.length; i++) {
        canvasDrawables[i].draw(canvas, mAmbient, centerY, centerX);
      }
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
      super.onVisibilityChanged(visible);

      // Whether the timer should be running depends on whether we're visible (as well as
      // whether we're in ambient mode), so we may need to start or stop the timer.
      updateTimer();
    }

    /**
     * Starts the {@link #mUpdateTimeHandler} timer if it should be running and isn't currently
     * or stops it if it shouldn't be running but currently is.
     */
    private void updateTimer() {
      mUpdateTimeHandler.removeMessages(MSG_UPDATE_TIME);
      if (shouldTimerBeRunning()) {
        mUpdateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
      }
    }

    /**
     * Returns whether the {@link #mUpdateTimeHandler} timer should be running. The timer should
     * only run when we're visible and in interactive mode.
     */
    private boolean shouldTimerBeRunning() {
      return isVisible() && !isInAmbientMode();
    }

    /**
     * Handle updating the time periodically in interactive mode.
     */
    private void handleUpdateTimeMessage() {
      invalidate();
      if (shouldTimerBeRunning()) {
        long timeMs = System.currentTimeMillis();
        long frameTime = 1000 / TARGET_FPS;
        long delayMs = frameTime - (timeMs % frameTime);
        mUpdateTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
      }
    }
  }
}
