package com.smskelley.politiface.views;

import com.smskelley.politiface.application.App;
import com.smskelley.politiface.models.Models;
import com.smskelley.politiface.views.background.Background;
import com.smskelley.politiface.views.background.BackgroundPaint;
import com.smskelley.politiface.views.hands.Cap;
import com.smskelley.politiface.views.hands.HourHand;
import com.smskelley.politiface.views.hands.MinuteHand;
import com.smskelley.politiface.views.hands.SecondHand;
import com.smskelley.politiface.views.people.Clinton;
import com.smskelley.politiface.views.people.Trump;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

import dagger.Component;

@Views.ViewsScope
@Component(dependencies = { App.class, Models.class })
public interface Views {
        HourHand hourHand();
        MinuteHand minuteHand();
        SecondHand secondHand();

        Clinton clinton();
        Trump trump();

        Background background();
        BackgroundPaint backgroundPaint();
        Cap cap();

        @Scope @Retention(RetentionPolicy.RUNTIME) @interface ViewsScope {}
}
