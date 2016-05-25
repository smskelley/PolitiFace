package com.smskelley.politiface.views.people;

import android.content.res.Resources;

import com.smskelley.politiface.R;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class PeopleModule {

  @Provides
  @Named("clinton") Person getClinton(Resources resources) {
    return new Person(resources, R.drawable.clinton, Person.Position.LEFT);
  }

  @Provides
  @Named("trump") Person getTrump(Resources resources) {
    return new Person(resources, R.drawable.trump, Person.Position.RIGHT);
  }

}
