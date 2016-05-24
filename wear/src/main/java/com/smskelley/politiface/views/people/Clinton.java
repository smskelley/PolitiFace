package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.smskelley.politiface.R;

import javax.inject.Inject;

public class Clinton extends Person {

  private final Drawable drawable;

  @Inject
  public Clinton(Resources resources) {
    super(resources);
    drawable = resources.getDrawable(R.drawable.clinton);
  }

  @Override
  protected Drawable getDrawable() {
    return drawable;
  }

  @Override
  protected Drawable getDrawable(int border, int top, int imgWidth, int screenWidth) {
    drawable.setBounds(border, top, border + imgWidth, top + imgWidth);
    return drawable;
  }
}
