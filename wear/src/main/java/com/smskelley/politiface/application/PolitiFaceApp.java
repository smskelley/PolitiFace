package com.smskelley.politiface.application;

import android.app.Application;

/**
 */
public class PolitiFaceApp extends Application {

  private App app;

  @Override
  public void onCreate() {
    super.onCreate();
    app = DaggerApp.builder()
        .appModule(new AppModule(this))
        .build();
  }

  public App component() {
    return app;
  }
}
