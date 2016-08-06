package com.smskelley.politilib.interactors.gcore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.Wearable;
import com.smskelley.politilib.models.polls.Chart;

import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 */
public class EstimateSyncInteractor implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String ESTIMATE_PATH = "/estimate";
    private static final String DATE_KEY = "date";

    private enum Status {
        CONNECTED,
        DISCONNECTED,
        FAILED,
    }

    private final Observable<Chart.EstimateByDate> estimateByDateObservable;
    private final BehaviorSubject<Status> apiClientStatusObservable = BehaviorSubject.create();
    private final BehaviorSubject<Boolean> successfulSyncObservable = BehaviorSubject.create();
    private final GoogleApiClient apiClient;

    public EstimateSyncInteractor(Context context,
                                  Observable<Chart.EstimateByDate> estimateByDateObservable) {
        this.estimateByDateObservable = estimateByDateObservable;

        apiClient = createGoogleApiClient(context);
        connectClient(apiClient);
        apiClientStatusObservable
            .takeFirst(s -> s == Status.CONNECTED)
            .subscribe(notUsed -> {
                Log.d("ASDF", "connected to gcore");
                this.estimateByDateObservable
                    .observeOn(Schedulers.io())
                    .subscribe(this::sendEstimate);
            });
    }

    public Observable<Boolean> onSuccessfulSync() {
        return successfulSyncObservable;
    }

    private void sendEstimate(Chart.EstimateByDate estimateByDate) {
        Log.d("ASDF", "Sending estimate for date: " + estimateByDate.date);
        PutDataMapRequest dataMapRequest = PutDataMapRequest.create(ESTIMATE_PATH);
        DataMap dataMap = dataMapRequest.getDataMap();

        dataMap.putString(DATE_KEY, estimateByDate.date);
        for (Chart.Estimate estimate : estimateByDate.estimates) {
            dataMap.putFloat(estimate.choice, estimate.value);
        }

        Wearable.DataApi
                .putDataItem(apiClient, dataMapRequest.asPutDataRequest())
                .setResultCallback(result -> {
                    if (result.getStatus().isSuccess()) {
                        Log.d("ASDF", "Sent dataitem on path: " + ESTIMATE_PATH);
                        successfulSyncObservable.onNext(true);
                    } else {
                        Log.wtf("ASDF", "couldn't send dataitem on path: " + ESTIMATE_PATH);
                        successfulSyncObservable.onNext(false);
                    }
                });
    }

    private GoogleApiClient createGoogleApiClient(Context context) {
        GoogleApiClient client = new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        return client;
    }

    private void connectClient(GoogleApiClient client) {
        Observable.just(client)
                .observeOn(Schedulers.io())
                .subscribe(GoogleApiClient::blockingConnect);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("ASDF", "connected to gcore");
        apiClientStatusObservable.onNext(Status.CONNECTED);
    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClientStatusObservable.onNext(Status.DISCONNECTED);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.w("ASDF", "failed to connect to gcore");
        apiClientStatusObservable.onNext(Status.FAILED);
    }
}
