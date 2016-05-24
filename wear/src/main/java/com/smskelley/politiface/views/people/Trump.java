package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.smskelley.politiface.R;

import javax.inject.Inject;

public class Trump extends Person {

  @Inject
  public Trump(Resources resources) {
    super(resources, R.drawable.trump, Position.RIGHT);
  }
}
