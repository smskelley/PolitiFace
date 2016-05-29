package com.smskelley.politiface.models.polls;

import rx.Observable;

public interface EstimateModel {
  void update();

  Observable<EstimateOnDate> getEstimateOnDate();
}
