package com.smskelley.politiface.models;

import com.smskelley.politiface.MainActivity;
import com.smskelley.politiface.models.polls.ChartModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component
public interface Models {
  ChartModel getChartModel();

  void inject(MainActivity mainActivity);
}
