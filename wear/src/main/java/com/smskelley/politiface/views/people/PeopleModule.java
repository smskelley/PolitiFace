package com.smskelley.politiface.views.people;

import android.content.res.Resources;
import android.util.Log;

import com.smskelley.politiface.R;
import com.smskelley.politilib.models.polls.ChartModel;
import com.smskelley.politilib.models.polls.EstimateModel;
import com.smskelley.politilib.models.polls.EstimateOnDate;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

@Module
public class PeopleModule {

  @Provides
  @Named("clinton") Person getClinton(ChartModel model, Resources resources) {
    return new Person(model, resources, R.drawable.clinton, Person.Position.LEFT);
  }

  @Provides
  @Named("trump") Person getTrump(ChartModel model, Resources resources) {
    return new Person(model, resources, R.drawable.trump, Person.Position.RIGHT);
  }

}
