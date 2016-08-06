package com.smskelley.politilib.application;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class AppModule {
  private final Application app;

  public AppModule(Application app) {
    this.app = app;
  }

  @Provides
  public Context provideContext() {
    return app;
  }

  @Provides
  public Resources provideResources() {
    return app.getResources();
  }
}
