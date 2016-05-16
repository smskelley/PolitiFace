package com.smskelley.politiface.views.background;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;

import com.smskelley.politiface.R;

import javax.inject.Inject;

public class BackgroundPaint extends Paint {

    @Inject
    public BackgroundPaint(Context context) {
        super();

        Resources res = context.getResources();
        setColor(res.getColor(R.color.background));
    }
}
