package com.smskelley.politiface.views.hands;

import android.content.res.Resources;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.time.TimeModel;

import javax.inject.Inject;

public class HourHand extends WatchHand {

  private static final float LENGTH = 0.20f;

  @Inject
  protected HourHand(Resources res, TimeModel timeModel, HandPaint ambientPaint,
                     HandPaint normalPaint) {
    super(res, timeModel, ambientPaint, normalPaint);
    normalPaint.setColor(res.getColor(R.color.hour_hand));
    normalPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.hour_hand_width));
    ambientPaint.setColor(res.getColor(R.color.hour_hand));
    ambientPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.hour_hand_width));
  }

  @Override
  protected float getRotation() {
    return timeModel.getHour() / 6f * (float) Math.PI;
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
