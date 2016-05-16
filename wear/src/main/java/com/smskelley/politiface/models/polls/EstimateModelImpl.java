package com.smskelley.politiface.models.polls;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 */
public class EstimateModelImpl implements ResultCallback<DataItemBuffer>,EstimateModel, GoogleApiClient.ConnectionCallbacks {

  private static final String ESTIMATE_PATH = "/estimate";
  private static final String DATE_KEY = "date";

  private BehaviorSubject<EstimateByDate> estimateByDateObservable =
      BehaviorSubject.create(EstimateByDate.EMPTY);
  private final GoogleApiClient apiClient;

  @Inject
  public EstimateModelImpl(Context context) {
    apiClient = createGoogleApiClient(context);
    apiClient.connect();
  }

  @Override
  public void update() {
    Wearable.DataApi
        .getDataItems(
            apiClient,
            new Uri.Builder()
                .scheme(PutDataRequest.WEAR_URI_SCHEME)
                .path(ESTIMATE_PATH)
                .build())
        .setResultCallback(this);
  }

  @Override
  public Observable<EstimateByDate> getEstimateByDate() {
    return estimateByDateObservable.asObservable();
  }

  private GoogleApiClient createGoogleApiClient(Context context) {
    return new GoogleApiClient.Builder(context)
        .addApi(Wearable.API)
        .addConnectionCallbacks(this)
        .build();
  }

  @Override
  public void onResult(@NonNull DataItemBuffer dataItems) {
    Log.d("ASDF", "got dataitems, count: " + dataItems.getCount());
    for (DataItem dataItem : dataItems) {
      Log.d("ASDF", "Got dataitem on path: " + dataItem.getUri().getPath());
      DataMap dataMap = DataMapItem.fromDataItem(dataItem).getDataMap();
      EstimateByDate estimateByDate = new EstimateByDate();
      for (String key : dataMap.keySet()) {
        if (DATE_KEY.equals(key)) {
          estimateByDate.setDate(dataMap.getString(key));
        } else {
          estimateByDate.addEstimate(new EstimateByDate.Estimate(key, dataMap.getFloat(key)));
        }
      }
      estimateByDateObservable.onNext(estimateByDate);
    }
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    Log.d("ASDF", "connected to gcore");
    update();
  }

  @Override
  public void onConnectionSuspended(int i) {
    Log.d("ASDF", "gcore connection suspended");
  }
}
