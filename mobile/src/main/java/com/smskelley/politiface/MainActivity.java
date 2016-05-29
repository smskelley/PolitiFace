package com.smskelley.politiface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smskelley.politiface.interactors.wear.EstimateSyncInteractor;
import com.smskelley.politiface.models.DaggerModels;
import com.smskelley.politiface.models.polls.ChartModel;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private EstimateSyncInteractor estimateSyncInteractor;
    @Inject ChartModel chartModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerModels.create().inject(this);

        estimateSyncInteractor = new EstimateSyncInteractor(this, chartModel.getEstimates());
    }
}
