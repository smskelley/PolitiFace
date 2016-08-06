package com.smskelley.politiface;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.smskelley.politilib.interactors.gcore.EstimateSyncInteractor;
import com.smskelley.politilib.models.polls.ChartModel;
import com.smskelley.politilib.models.polls.services.ChartRefreshJobService;

import java.util.List;

import javax.inject.Inject;

import static android.Manifest.permission.RECEIVE_BOOT_COMPLETED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "PolitiFace";

    private EstimateSyncInteractor estimateSyncInteractor;
    @Inject ChartModel chartModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyPermissions();
    }

    private void scheduleChartRefreshJob() {
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        List<JobInfo> allPendingJobs = jobScheduler.getAllPendingJobs();
        Log.d(TAG, String.format("Already %d jobs: ", allPendingJobs.size()));
        for (JobInfo info : allPendingJobs) {
            Log.d(TAG, info.toString());
        }
        jobScheduler.cancelAll();

        int result = jobScheduler.schedule(ChartRefreshJobService.getJobInfo(this));
        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "MainActivity.scheduleChartRefreshJob: SUCCESSFULLY SCHEDULED");
        } else {
            Log.d(TAG, "MainActivity.scheduleChartRefreshJob: ERROR: " + result);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int result = grantResults[0];
        if (result == PERMISSION_GRANTED) {
            scheduleChartRefreshJob();
        }
    }

    private void verifyPermissions() {
        Log.d(TAG, "Verifying Permissions.");
        int permissionStatus = ContextCompat.checkSelfPermission(this, RECEIVE_BOOT_COMPLETED);
        if (permissionStatus != PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, RECEIVE_BOOT_COMPLETED)) {
                Log.d(TAG, "We should show rationale.");
            } else {
                Log.d(TAG, "No need to show rationale.");
                ActivityCompat.requestPermissions(this, new String[]{RECEIVE_BOOT_COMPLETED}, 0);
            }
        } else {
            Log.d(TAG, "Have permission, scheduling job.");
            scheduleChartRefreshJob();
        }
    }
}
