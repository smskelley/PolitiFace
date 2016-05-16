package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.smskelley.politiface.R;

import javax.inject.Inject;

public class Trump extends Person{

  private final Drawable drawable;

  @Inject
  public Trump(Resources resources) {
    super(resources);
    drawable = resources.getDrawable(R.drawable.trump);
  }

  @Override
  protected Drawable getDrawable() {
    return drawable;
  }

  @Override
  protected Drawable getDrawable(int border, int top, int imgWidth, int screenWidth) {
    int right = screenWidth - border;
    drawable.setBounds(right - imgWidth, top, right, top + imgWidth);
    return drawable;
  }
}
