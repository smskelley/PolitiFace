package com.smskelley.politilib.models.polls.services;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.smskelley.politilib.application.App;
import com.smskelley.politilib.application.AppModule;
import com.smskelley.politilib.application.DaggerApp;
import com.smskelley.politilib.interactors.gcore.EstimateSyncInteractor;
import com.smskelley.politilib.models.DaggerModels;
import com.smskelley.politilib.models.polls.Chart;
import com.smskelley.politilib.models.polls.ChartModel;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;

public class ChartRefreshJobService extends JobService {

  public ChartRefreshJobService() {
    super();
    Log.d("ASDF", "Constructed job service");
  }

  public static JobInfo getJobInfo(Context context) {
    return new JobInfo
        .Builder(1, new ComponentName(context, ChartRefreshJobService.class))
        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        .setPeriodic(TimeUnit.HOURS.toMillis(4))
        .setPersisted(true)
        //.setOverrideDeadline(TimeUnit.SECONDS.toMillis(10))
        .build();
  }

  @Inject ChartModel chartModel;
  private EstimateSyncInteractor interactor;

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d("ASDF", "ChartRefreshJobService.onCreate: ");
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    Log.d("ASDF", "ChartRefreshJobService.onStartCommand: ");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onTaskRemoved(Intent rootIntent) {
    super.onTaskRemoved(rootIntent);
    Log.d("ASDF", "ChartRefreshJobService.onTaskRemoved: ");
  }

  @Override
  public boolean onStartJob(JobParameters params) {
    Log.w("ASDF", "onStartJob");

    DaggerModels
        .builder()
        .app(DaggerApp
            .builder()
            .appModule(new AppModule(getApplication()))
            .build())
        .build()
        .inject(this);
    Observable<Chart.EstimateByDate> estimates = chartModel.getEstimates();
    interactor = new EstimateSyncInteractor(getApplicationContext(), estimates);

    interactor
        .onSuccessfulSync()
        .subscribe(success -> {
          Log.d("ASDF", "finished, success = " + success);
          jobFinished(params, !success /* reschedule */);
        });

    chartModel.update();

    return true /* still processing on another thread. */;
  }

  @Override
  public boolean onStopJob(JobParameters params) {
    Log.d("ASDF", "onStopJob");
    return true /* reschedule */;
  }
}
