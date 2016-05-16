package com.smskelley.politiface.views;

import android.graphics.Canvas;

/**
 */
public interface CanvasDrawable {
    void draw(Canvas canvas, float centerX, float centerY);
    void setChinSizePx(int chinSize);
    void setIsRound(boolean isRound);
}
