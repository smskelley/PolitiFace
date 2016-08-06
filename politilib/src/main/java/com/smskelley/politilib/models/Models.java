package com.smskelley.politilib.models;

import android.app.Activity;

import com.smskelley.politilib.application.App;
import com.smskelley.politilib.models.polls.ChartModel;
import com.smskelley.politilib.models.polls.EstimateModel;
import com.smskelley.politilib.models.polls.services.ChartRefreshJobService;
import com.smskelley.politilib.models.time.TimeModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
    modules = ModelsModule.class,
    dependencies = App.class)
public interface Models {
  TimeModel time();
  EstimateModel estimate();
  ChartModel getChartModel();

  void inject(Activity activity);
  void inject(ChartRefreshJobService jobService);
}
