package com.smskelley.politiface.views.hands;

import android.content.res.Resources;

import com.smskelley.politiface.R;
import com.smskelley.politilib.models.time.TimeModel;

import javax.inject.Inject;

public class SecondHand extends WatchHand {

  private static final float LENGTH = 0.45f;

  @Inject
  protected SecondHand(Resources res, TimeModel timeModel, HandPaint ambientPaint,
                     HandPaint normalPaint) {
    super(res, timeModel, ambientPaint, normalPaint);
    ambientPaint.setColor(res.getColor(R.color.second_hand));
    ambientPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.second_hand_width));
    normalPaint.setColor(res.getColor(R.color.second_hand));
    normalPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.second_hand_width));
  }

  @Override
  protected float getRotation() {
    return timeModel.getSecond() / 30f * (float) Math.PI;
  }

  @Override
  protected float getLength() {
    return LENGTH;
  }

  @Override
  protected boolean shouldDrawInAmbient() {
    return false;
  }
}
