package com.smskelley.politiface.models;

import android.content.Context;

import com.smskelley.politiface.models.polls.EstimateModel;
import com.smskelley.politiface.models.polls.EstimateModelImpl;
import com.smskelley.politiface.models.time.TimeModel;
import com.smskelley.politiface.models.time.TimeModelImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class ModelsModule {

  @Provides
  @Singleton
  static TimeModel provideTimeModel() {
    return new TimeModelImpl();
  }

  @Provides
  @Singleton
  static EstimateModel provideEstimateModel(Context context) {
    return new EstimateModelImpl(context);
  }

}
