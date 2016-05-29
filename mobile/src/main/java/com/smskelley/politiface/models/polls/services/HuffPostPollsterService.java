package com.smskelley.politiface.models.polls.services;

import com.smskelley.politiface.models.polls.Chart;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface HuffPostPollsterService {
    // Example URL:
    // [...].huffingtonpost.com/pollster/api/charts/2012-general-election-romney-vs-obama.json
    @GET("pollster/api/charts/{slug}")
    Observable<Chart> getChart(@Path("slug") String slug);
}
