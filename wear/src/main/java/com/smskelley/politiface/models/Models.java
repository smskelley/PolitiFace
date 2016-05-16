package com.smskelley.politiface.models;

import com.smskelley.politiface.application.App;
import com.smskelley.politiface.models.polls.EstimateModel;
import com.smskelley.politiface.models.time.TimeModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
    modules = ModelsModule.class,
    dependencies = App.class)
public interface Models {
    TimeModel time();
    EstimateModel estimate();
}
