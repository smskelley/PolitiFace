package com.smskelley.politilib.models;

import android.content.Context;

import com.smskelley.politilib.models.polls.DataApiEstimateModel;
import com.smskelley.politilib.models.polls.EstimateModel;
import com.smskelley.politilib.models.time.TimeModel;
import com.smskelley.politilib.models.time.TimeModelImpl;

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
    return new DataApiEstimateModel(context);
  }

}
