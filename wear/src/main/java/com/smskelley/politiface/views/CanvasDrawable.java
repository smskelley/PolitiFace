package com.smskelley.politiface.views;

import android.graphics.Canvas;

/**
 */
public interface CanvasDrawable {
  void draw(Canvas canvas, boolean isAmbient, float centerY, float centerX);

  void setChinSizePx(int chinSize);

  void setIsRound(boolean isRound);
}
