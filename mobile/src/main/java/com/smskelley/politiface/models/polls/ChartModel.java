package com.smskelley.politiface.models.polls;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

public class ChartModel {
    private static String BASE_URL = "http://elections.huffingtonpost.com/";

    private static String TRUMP_CLINTON = "2016-general-election-trump-vs-clinton";
    private final HuffPostPollsterService service;

    private final BehaviorSubject<Chart.EstimateByDate> estimateByDate = BehaviorSubject.create();


    public ChartModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        service = retrofit.create(HuffPostPollsterService.class);
        getNewEstimate()
                .subscribe(estimateByDate::onNext);
    }

    public Observable<Chart.EstimateByDate> getEstimates() {
        return estimateByDate;
    }

    private Observable<Chart.EstimateByDate> getNewEstimate() {
        return service.getChart(TRUMP_CLINTON)
                .subscribeOn(Schedulers.io())
                .flatMap(c -> Observable.from(c.estimates_by_date))
                .doOnNext(e -> Log.d("ASDF", "Received estimates for date: " + e.date))
                .take(1);
    }

}
