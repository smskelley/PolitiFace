package com.smskelley.politiface.views.hands;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.time.TimeModel;
import com.smskelley.politiface.views.CanvasDrawable;

/**
 */
public abstract class WatchHand implements CanvasDrawable {

    private static final float SHADOW_OFFSET = 0.030f;
    /**
     * As a percentage of screen width;
     */
    private final float length;
    private final Paint paint;

    protected boolean isRound = false;
    protected int chinSizePx = 0;
    protected final TimeModel timeModel;

    protected WatchHand(Resources res, TimeModel timeModel, HandPaint handPaint) {
        this.timeModel = timeModel;
        this.length = getLength();
        paint = handPaint;
        paint.setShadowLayer(
            res.getDimensionPixelOffset(R.dimen.shadow_radius),
            res.getDimensionPixelOffset(R.dimen.shadow_x),
            res.getDimensionPixelOffset(R.dimen.shadow_y),
            res.getColor(R.color.shadow));
    }

    @Override
    public void draw(Canvas canvas, float centerX, float centerY) {
        if (isRound) {
            drawRound(canvas, centerX, centerY);
        } else {
            drawSquare(canvas, centerX, centerY);
        }
    }

    private void drawRound(Canvas canvas, float centerX, float centerY) {
        float rotation = getRotation();
        float lengthPx = centerX * 2 * length;
        float x = (float) Math.sin(rotation) * lengthPx;
        float y = (float) -Math.cos(rotation) * lengthPx;
        canvas.drawLine(centerX, centerY, centerX + x, centerY + y, paint);
    }

    private void drawSquare(Canvas canvas, float centerX, float centerY) {
        float rotation = getRotation();
        float lengthPx = centerX * 2 * length;

        float cos = (float) Math.cos(rotation);
        float sin = (float) Math.sin(rotation);
        float abs_cos = Math.abs(cos);
        float abs_sin = Math.abs(sin);

        float scale;
        if (abs_cos >= abs_sin) {
            scale = lengthPx / abs_cos;
        } else {
            scale = lengthPx / abs_sin;
        }
        float x = centerX + sin * scale;
        float y = centerY + -cos * scale;

        canvas.drawLine(centerX, centerY, x, y, paint);
    }


    @Override
    public void setIsRound(boolean isRound) {
        this.isRound = isRound;
    }

    @Override
    public void setChinSizePx(int chinSize) {
        this.chinSizePx = chinSize;
    }

    protected abstract float getRotation();
    protected abstract float getLength();
}
