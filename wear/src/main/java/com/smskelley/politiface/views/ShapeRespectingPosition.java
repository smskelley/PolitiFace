package com.smskelley.politiface.views;

/**
 * Position on screen that respects the shape of the screen.
 *
 * This means that we must restrict the valid ways to specify a position to rotation and distance
 * from edge.
 */
public class ShapeRespectingPosition {
  private float mX;
  private float mY;
  private float mChin;
  private boolean isRound;

  public float getY() {
    return mY;
  }

  public float getX() {
    return mX;
  }

  public void setChin(float chin) {
    mChin = chin;
  }

  public void setRound(boolean round) {
    isRound = round;
  }
}
