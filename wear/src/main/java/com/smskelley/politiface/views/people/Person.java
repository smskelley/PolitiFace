package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.polls.EstimateModel;
import com.smskelley.politiface.models.polls.EstimateOnDate;
import com.smskelley.politiface.views.CanvasDrawable;

import java.util.Locale;

public class Person implements CanvasDrawable {

  protected final static float SIZE_PERCENT = 0.25f;
  protected final static float MARGIN_PERCENT = 0.05f;

  private final Paint shadowPaint;
  private final Paint textPaint;
  private final Paint textBackgroundPaint;
  private final Paint textBackgroundStrokePaint;
  private final Drawable drawable;
  private final Position position;
  private final float textSize;
  private final float textMargin;
  private float lastCenterX = 0f;
  private float embededPadding;
  private EstimateOnDate estimate = EstimateOnDate.EMPTY;

  public Person(EstimateModel model, Resources res, @DrawableRes int drawableId,
                Position position) {
    this.position = position;
    drawable = res.getDrawable(drawableId);
    textSize = res.getDimensionPixelSize(R.dimen.text_size);
    textMargin = res.getDimensionPixelSize(R.dimen.text_margin);

    textPaint = new Paint();
    textPaint.setAntiAlias(true);
    textPaint.setColor(res.getColor(R.color.text));
    textPaint.setTextAlign(Paint.Align.CENTER);
    textPaint.setTextSize(textSize);
    textPaint.setElegantTextHeight(false);
    textPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));
    textPaint.setShadowLayer(
        res.getDimensionPixelOffset(R.dimen.shadow_radius),
        res.getDimensionPixelOffset(R.dimen.shadow_x),
        res.getDimensionPixelOffset(R.dimen.shadow_y),
        res.getColor(R.color.shadow));

    textBackgroundPaint = new Paint();
    textBackgroundPaint.setAntiAlias(true);
    textBackgroundPaint.setColor(res.getColor(R.color.text_background));
    textBackgroundPaint.setPathEffect(new CornerPathEffect(textMargin));
    textBackgroundPaint.setStyle(Paint.Style.FILL);
    textBackgroundStrokePaint = new Paint();
    textBackgroundStrokePaint.setAntiAlias(true);
    textBackgroundStrokePaint.setColor(res.getColor(R.color.text_background_stroke));
    textBackgroundStrokePaint.setPathEffect(new CornerPathEffect(textMargin));
    textBackgroundStrokePaint.setStyle(Paint.Style.STROKE);
    textBackgroundStrokePaint.setStrokeWidth(
        res.getDimensionPixelSize(R.dimen.text_background_strike));


    shadowPaint = new Paint();
    shadowPaint.setAntiAlias(true);
    shadowPaint.setColor(res.getColor(R.color.shadow));
    shadowPaint.setShadowLayer(
        res.getDimensionPixelOffset(R.dimen.shadow_radius),
        res.getDimensionPixelOffset(R.dimen.shadow_x),
        res.getDimensionPixelOffset(R.dimen.shadow_y),
        res.getColor(R.color.shadow));

    model
        .getEstimateOnDate()
        .filter(estimateOnDate -> estimateOnDate != EstimateOnDate.EMPTY)
        .doOnNext(estimateOnDate -> Person.this.estimate = estimateOnDate)
        .subscribe();
  }

  private void setBounds(float centerX, float centerY) {

    int screenWidth = (int) (centerX * 2);
    int imgWidth = getWidth(centerX);
    int margin = (int) (screenWidth * MARGIN_PERCENT);
    int top = (int) (centerY - imgWidth / 2);
    embededPadding = imgWidth / 18f;

    switch (position) {
      case LEFT:
        drawable.setBounds(margin, top, margin + imgWidth, top + imgWidth);
        break;
      case RIGHT:
        int right = screenWidth - margin;
        drawable.setBounds(right - imgWidth, top, right, top + imgWidth);
        break;
    }
  }

  @Override
  public void draw(Canvas canvas, boolean isAmbient, float centerY, float centerX) {
    if (isAmbient) {
      return;
    }

    if (lastCenterX != centerX) {
      lastCenterX = centerX;
      setBounds(centerX, centerY);
    }
    Rect bounds = drawable.getBounds();

    // number string
    float votePercent =
        100f * (position == Position.LEFT
            ? estimate.getLeftPercent()
            : 1f - estimate.getLeftPercent());
    String numberString = String.format(Locale.getDefault(), "%.1f", votePercent);
    Rect textBounds = new Rect();
    textPaint.getTextBounds(numberString, 0, numberString.length(), textBounds);

    // number bg
    canvas.drawRect(
        bounds.centerX() - textBounds.centerX() - textMargin /*left*/,
        bounds.centerY() /*top*/,
        bounds.centerX() + textBounds.centerX() + textMargin /*right*/,
        bounds.bottom + textSize + 2*textMargin /*bot*/,
        textBackgroundPaint
    );
    canvas.drawRect(
        bounds.centerX() - textBounds.centerX() - textMargin /*left*/,
        bounds.centerY() /*top*/,
        bounds.centerX() + textBounds.centerX() + textMargin /*right*/,
        bounds.bottom + textSize + 2*textMargin /*bot*/,
        textBackgroundStrokePaint
    );

    // number
    canvas.drawText(numberString,
        bounds.centerX() /* x (center) */,
        bounds.bottom + textSize + textMargin - embededPadding /* y (bottom) */,
        textPaint);

    // avatar shadow
    canvas.drawCircle(bounds.centerX(), bounds.centerY(), bounds.width() / 2, shadowPaint);
    // avatar
    drawable.draw(canvas);
  }

  @Override
  public void setChinSizePx(int chinSize) {}

  @Override
  public void setIsRound(boolean isRound) {}

  protected int getWidth(float centerX) {
    return (int) (2 * centerX * SIZE_PERCENT);
  }

  enum Position {
    LEFT,
    RIGHT,
  }
}
