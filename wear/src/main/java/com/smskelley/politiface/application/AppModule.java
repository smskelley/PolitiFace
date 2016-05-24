package com.smskelley.politiface.application;

import android.content.Context;
import android.content.res.Resources;

import dagger.Module;
import dagger.Provides;

/**
 */
@Module
public class AppModule {
  private final PolitiFaceApp app;

  public AppModule(PolitiFaceApp politiFaceApp) {
    this.app = politiFaceApp;
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
