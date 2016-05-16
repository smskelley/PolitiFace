package com.smskelley.politiface.views.hands;

import android.graphics.Paint;

import javax.inject.Inject;

public class HandPaint extends Paint {

    @Inject
    public HandPaint() {
        super();

        setStrokeCap(Paint.Cap.ROUND);
        setAntiAlias(true);
    }
}
