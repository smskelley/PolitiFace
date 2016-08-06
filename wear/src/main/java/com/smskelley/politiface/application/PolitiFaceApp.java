package com.smskelley.politiface.application;

import android.app.Application;

import com.smskelley.politilib.application.App;
import com.smskelley.politilib.application.AppModule;
import com.smskelley.politilib.application.DaggerApp;

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
