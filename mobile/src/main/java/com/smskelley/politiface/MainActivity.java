package com.smskelley.politiface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.smskelley.politiface.interactors.wear.EstimateSyncInteractor;
import com.smskelley.politiface.models.polls.ChartModel;

public class MainActivity extends AppCompatActivity {

    private EstimateSyncInteractor estimateSyncInteractor;
    private ChartModel chartModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chartModel = new ChartModel();
        estimateSyncInteractor = new EstimateSyncInteractor(this, chartModel.getEstimates());
    }
}
