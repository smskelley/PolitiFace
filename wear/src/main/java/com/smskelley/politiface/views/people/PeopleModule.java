package com.smskelley.politiface.views.people;

import android.content.res.Resources;

import com.smskelley.politiface.R;
import com.smskelley.politiface.models.polls.EstimateModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class PeopleModule {

  @Provides
  @Named("clinton") Person getClinton(EstimateModel model, Resources resources) {
    return new Person(model, resources, R.drawable.clinton, Person.Position.LEFT);
  }

  @Provides
  @Named("trump") Person getTrump(EstimateModel model, Resources resources) {
    return new Person(model, resources, R.drawable.trump, Person.Position.RIGHT);
  }

}
