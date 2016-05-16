package com.smskelley.politiface.models.polls;

import rx.Observable;

/**
 * Created by smskelley on 5/15/2016.
 */
public interface EstimateModel {
  void update();

  Observable<EstimateByDate> getEstimateByDate();
}
