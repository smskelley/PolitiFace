package com.smskelley.politilib.models.polls;

import rx.Observable;

public interface EstimateModel {
  void update();

  Observable<EstimateOnDate> getEstimateOnDate();
}
