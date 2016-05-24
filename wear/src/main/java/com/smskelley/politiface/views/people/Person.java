package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.smskelley.politiface.R;
import com.smskelley.politiface.views.CanvasDrawable;

public abstract class Person implements CanvasDrawable {

  protected final static float SIZE_PERCENT = 0.25f;
  protected final static float MARGIN_PERCENT = 0.05f;

  private final Paint shadowPaint;

  private float lastCenterX = 0f;

  protected Person(Resources res) {
    shadowPaint = new Paint();
    shadowPaint.setAntiAlias(true);
    shadowPaint.setColor(res.getColor(R.color.shadow));
    shadowPaint.setShadowLayer(
        res.getDimensionPixelOffset(R.dimen.shadow_radius),
        res.getDimensionPixelOffset(R.dimen.shadow_x),
        res.getDimensionPixelOffset(R.dimen.shadow_y),
        res.getColor(R.color.shadow));
  }

  @Override
  public void draw(Canvas canvas, boolean isAmbient, float centerY, float centerX) {
    if (isAmbient) {
      return;
    }

    if (lastCenterX == centerX) {
      getDrawable().draw(canvas);
      return;
    }

    int screenWidth = (int) (centerX * 2);
    int imgWidth = getWidth(centerX);
    int border = (int) (centerX * 2 * MARGIN_PERCENT);
    int top = (int) (centerY - imgWidth / 2);
    Drawable drawable = getDrawable(border, top, imgWidth, screenWidth);
    Rect bounds = drawable.getBounds();
    canvas.drawCircle(bounds.centerX(), bounds.centerY(), bounds.width() / 2, shadowPaint);
    drawable.draw(canvas);
  }

  @Override
  public void setChinSizePx(int chinSize) {
  }

  @Override
  public void setIsRound(boolean isRound) {
  }

  protected int getWidth(float centerX) {
    return (int) (2 * centerX * SIZE_PERCENT);
  }

  protected abstract Drawable getDrawable();

  protected abstract Drawable getDrawable(int border, int top, int imgWidth, int screenWidth);
}
