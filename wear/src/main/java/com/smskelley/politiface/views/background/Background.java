package com.smskelley.politiface.views.background;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.polls.EstimateByDate;
import com.smskelley.politiface.models.polls.EstimateModel;
import com.smskelley.politiface.views.CanvasDrawable;

import javax.inject.Inject;

import rx.functions.Action1;
import rx.functions.Func1;

public class Background implements CanvasDrawable {
  private final EstimateModel estimateModel;
  private final Paint rightPaint;
  private final Paint leftPaint;
  private final int shadowRadius;
  private final int shadowX;
  @ColorInt private final int shadowColor;


  private EstimateByDate estimate = EstimateByDate.EMPTY;


  @Inject
  public Background(Resources res, EstimateModel estimateModel) {
    this.estimateModel = estimateModel;
    rightPaint = new Paint();
    rightPaint.setColor(res.getColor(R.color.republican));

    leftPaint = new Paint();
    leftPaint.setColor(res.getColor(R.color.democrat));

    shadowRadius = res.getDimensionPixelOffset(R.dimen.shadow_radius);
    shadowX = res.getDimensionPixelOffset(R.dimen.shadow_x);
    shadowColor = res.getColor(R.color.shadow);

    estimateModel
        .getEstimateByDate()
        .first(new Func1<EstimateByDate, Boolean>() {
          @Override
          public Boolean call(EstimateByDate estimateByDate) {
            return estimateByDate != EstimateByDate.EMPTY;
          }
        })
        .doOnNext(new Action1<EstimateByDate>() {
          @Override
          public void call(EstimateByDate estimateByDate) {
            Background.this.estimate = estimateByDate;
          }
        }).subscribe();
  }

  @Override
  public void draw(Canvas canvas, float centerX, float centerY) {

    float midPoint = getMidPoint(canvas.getWidth());

    // We want a shadow to cast over the lower score person.
    // So, they must be drawn first and their opponent must gain a shadow.
    if (midPoint >= centerX) {
      // left is winning.
      // these paint updates should be moved to where estimate is set.
      leftPaint.setShadowLayer(shadowRadius, shadowX, 0f, shadowColor);
      rightPaint.setShadowLayer(0f, 0f, 0f, 0);
      drawRight(canvas, midPoint);
      drawLeft(canvas, midPoint);
    } else {
      // right is winning.
      // these paint updates should be moved to where estimate is set.
      rightPaint.setShadowLayer(shadowRadius, shadowX, 0f, shadowColor);
      leftPaint.setShadowLayer(0f, 0f, 0f, 0);
      drawLeft(canvas, midPoint);
      drawRight(canvas, midPoint);
    }
  }

  private void drawRight(Canvas canvas, float midPoint) {
    canvas.drawRect(
        midPoint /* left */,
        0f /* top */,
        canvas.getWidth() /* right */,
        canvas.getHeight() /* bottom */,
        rightPaint);
  }

  private void drawLeft(Canvas canvas, float midPoint) {
    canvas.drawRect(
        0f /* left */,
        0f /* top */,
        midPoint /* right */,
        canvas.getHeight() /* bottom */,
        leftPaint);
  }

  private float getMidPoint(float width) {
    float left = estimate.getClinton();
    float right = estimate.getTrump();
    float total = left + right;

    float percent = left / total;

/*    Log.d("ASDF", String.format(
        "Midpoint: left: %f, right: %f, percent = %.2f, midPoint = %.2f",
        left, right, percent, percent * centerX));*/
    return percent * width;
  }

  @Override
  public void setChinSizePx(int chinSize) {}

  @Override
  public void setIsRound(boolean isRound) {}
}
