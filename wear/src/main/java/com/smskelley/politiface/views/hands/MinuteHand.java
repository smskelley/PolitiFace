package com.smskelley.politiface.views.hands;

import android.content.res.Resources;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.time.TimeModel;

import javax.inject.Inject;

/**
 */
public class MinuteHand extends WatchHand {

  private static final float LENGTH = 0.30f;

  @Inject
  protected MinuteHand(Resources res, TimeModel timeModel, HandPaint ambientPaint,
                     HandPaint normalPaint) {
    super(res, timeModel, ambientPaint, normalPaint);
    ambientPaint.setColor(res.getColor(R.color.minute_hand));
    ambientPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.minute_hand_width));
    normalPaint.setColor(res.getColor(R.color.minute_hand));
    normalPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.minute_hand_width));
  }

  @Override
  protected float getRotation() {
    return timeModel.getMinute() / 30f * (float) Math.PI;
  }

  @Override
  protected float getLength() {
    return LENGTH;
  }

  @Override
  protected boolean shouldDrawInAmbient() {
    return true;
  }
}
