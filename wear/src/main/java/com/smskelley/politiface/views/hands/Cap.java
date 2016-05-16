package com.smskelley.politiface.views.hands;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.smskelley.politiface.R;
import com.smskelley.politiface.views.CanvasDrawable;

import javax.inject.Inject;

/**
 */
public class Cap implements CanvasDrawable {

    Paint paint;
    float radius;

    @Inject
    public Cap(Resources res, HandPaint handPaint) {
        radius = res.getDimensionPixelSize(R.dimen.cap_radius);
        paint = handPaint;
        paint.setColor(res.getColor(R.color.cap_color));
        paint.setShadowLayer(
            res.getDimensionPixelOffset(R.dimen.shadow_radius),
            res.getDimensionPixelOffset(R.dimen.shadow_x),
            res.getDimensionPixelOffset(R.dimen.shadow_y),
            res.getColor(R.color.shadow));
    }

    @Override
    public void draw(Canvas canvas, float centerX, float centerY) {
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    @Override
    public void setChinSizePx(int chinSize) {}

    @Override
    public void setIsRound(boolean isRound) {}
}
