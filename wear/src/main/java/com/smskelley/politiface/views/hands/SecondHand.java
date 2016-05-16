package com.smskelley.politiface.views.hands;

import android.content.res.Resources;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.time.TimeModel;

import javax.inject.Inject;

/**
 */
public class SecondHand extends WatchHand {

    private static final float LENGTH = 0.45f;

    @Inject
    protected SecondHand(Resources res, TimeModel timeModel, HandPaint handPaint) {
        super(res, timeModel, handPaint);
        handPaint.setColor(res.getColor(R.color.second_hand));
        handPaint.setStrokeWidth(res.getDimensionPixelSize(R.dimen.second_hand_width));
    }

    @Override
    protected float getRotation() {
        return timeModel.getSecond() / 30f * (float) Math.PI;
    }

    @Override
    protected float getLength() {
        return LENGTH;
    }
}
