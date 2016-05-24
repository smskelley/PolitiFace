package com.smskelley.politiface.views.hands;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.smskelley.politiface.R;
import com.smskelley.politiface.views.CanvasDrawable;

import javax.inject.Inject;

/**
 * A small circle above hands where they intersect.
 */
public class Cap implements CanvasDrawable {

  private final HandPaint ambientPaint;
  private final Paint normalPaint;
  private final float radius;

  @Inject
  public Cap(Resources res, HandPaint ambientPaint, HandPaint normalPaint) {
    radius = res.getDimensionPixelSize(R.dimen.cap_radius);

    this.ambientPaint = ambientPaint;
    this.ambientPaint.setColor(res.getColor(R.color.cap_color));
    this.ambientPaint.setAntiAlias(false);

    this.normalPaint = normalPaint;
    this.normalPaint.setColor(res.getColor(R.color.cap_color));
    this.normalPaint.setShadowLayer(
        res.getDimensionPixelOffset(R.dimen.shadow_radius),
        res.getDimensionPixelOffset(R.dimen.shadow_x),
        res.getDimensionPixelOffset(R.dimen.shadow_y),
        res.getColor(R.color.shadow));
  }

  @Override
  public void draw(Canvas canvas, boolean isAmbient, float centerY, float centerX) {
    canvas.drawCircle(centerX, centerY, radius, isAmbient ? ambientPaint : normalPaint);
  }

  @Override
  public void setChinSizePx(int chinSize) {}

  @Override
  public void setIsRound(boolean isRound) {}
}
