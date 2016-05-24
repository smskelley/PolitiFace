package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.smskelley.politiface.R;

import javax.inject.Inject;

public class Clinton extends Person {

  @Inject
  public Clinton(Resources resources) {
    super(resources, R.drawable.clinton, Position.LEFT);
  }
}
