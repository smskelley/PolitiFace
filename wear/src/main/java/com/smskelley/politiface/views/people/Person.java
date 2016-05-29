package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.polls.EstimateModel;
import com.smskelley.politiface.models.polls.EstimateOnDate;
import com.smskelley.politiface.views.CanvasDrawable;

import java.util.Locale;

import rx.functions.Action1;
import rx.functions.Func1;

public class Person implements CanvasDrawable {

  protected final static float SIZE_PERCENT = 0.25f;
  protected final static float MARGIN_PERCENT = 0.05f;

  private final Paint shadowPaint;
  private final Paint textPaint;
  private final Drawable drawable;
  private final Position position;
  private final float textSize;
  private final float textMargin;
  private float lastCenterX = 0f;
  private EstimateOnDate estimate = EstimateOnDate.EMPTY;

  public Person(EstimateModel model, Resources res, @DrawableRes int drawableId, Position position) {
    this.position = position;
    drawable = res.getDrawable(drawableId);
    textSize = res.getDimensionPixelSize(R.dimen.text_size);
    textMargin = res.getDimensionPixelSize(R.dimen.text_margin);

    textPaint = new Paint();
    textPaint.setAntiAlias(true);
    textPaint.setColor(res.getColor(R.color.text));
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTextSize(textSize);
    textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
    textPaint.setShadowLayer(
        res.getDimensionPixelOffset(R.dimen.shadow_radius),
        res.getDimensionPixelOffset(R.dimen.shadow_x),
        res.getDimensionPixelOffset(R.dimen.shadow_y),
        res.getColor(R.color.shadow));

    shadowPaint = new Paint();
    shadowPaint.setAntiAlias(true);
    shadowPaint.setColor(res.getColor(R.color.shadow));
    shadowPaint.setShadowLayer(
        res.getDimensionPixelOffset(R.dimen.shadow_radius),
        res.getDimensionPixelOffset(R.dimen.shadow_x),
        res.getDimensionPixelOffset(R.dimen.shadow_y),
        res.getColor(R.color.shadow));

    model
        .getEstimateOnDate()
        .filter(new Func1<EstimateOnDate, Boolean>() {
          @Override
          public Boolean call(EstimateOnDate estimateOnDate) {
            return estimateOnDate != EstimateOnDate.EMPTY;
          }
        })
        .doOnNext(new Action1<EstimateOnDate>() {
          @Override
          public void call(EstimateOnDate estimateOnDate) {
            Person.this.estimate = estimateOnDate;
          }
        })
        .subscribe();
  }

  private void setBounds(float centerX, float centerY) {

    int screenWidth = (int) (centerX * 2);
    int imgWidth = getWidth(centerX);
    int border = (int) (centerX * 2 * MARGIN_PERCENT);
    int top = (int) (centerY - imgWidth / 2);

    switch (position) {
      case LEFT:
        drawable.setBounds(border, top, border + imgWidth, top + imgWidth);
        break;
      case RIGHT:
        int right = screenWidth - border;
        drawable.setBounds(right - imgWidth, top, right, top + imgWidth);
        break;
    }
  }

  @Override
  public void draw(Canvas canvas, boolean isAmbient, float centerY, float centerX) {
    if (isAmbient) {
      return;
    }

    if (lastCenterX != centerX) {
      lastCenterX = centerX;
      setBounds(centerX, centerY);
    }
    Rect bounds = drawable.getBounds();
    canvas.drawCircle(bounds.centerX(), bounds.centerY(), bounds.width() / 2, shadowPaint);

    float votePercent =
        100f * (position == Position.LEFT
          ? estimate.getLeftPercent()
          : 1f - estimate.getLeftPercent());
    canvas.drawText(String.format(Locale.getDefault(), "%.1f", votePercent),
        bounds.centerX(), bounds.bottom + textSize + textMargin, textPaint);
    drawable.draw(canvas);
  }

  @Override
  public void setChinSizePx(int chinSize) {}

  @Override
  public void setIsRound(boolean isRound) {}

  protected int getWidth(float centerX) {
    return (int) (2 * centerX * SIZE_PERCENT);
  }

  enum Position {
    LEFT,
    RIGHT,
  }
}
