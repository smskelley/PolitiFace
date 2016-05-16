package com.smskelley.politiface.application;

import android.content.Context;
import android.content.res.Resources;

import dagger.Component;

/**
 */
@Component(modules = AppModule.class)
public interface App {
    Context context();
    Resources resources();
}
