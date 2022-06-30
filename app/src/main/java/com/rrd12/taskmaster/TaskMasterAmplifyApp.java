package com.rrd12.taskmaster;

import android.app.Application;
import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.core.Amplify;

public class TaskMasterAmplifyApp extends Application {
    public static final String TAG = "taskmasterapplication";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Log.i(TAG, "Initialized Amplify successfully");
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
        } catch (AmplifyException ae) {
            Log.e(TAG, "Error initializing Amplify " + ae.getMessage(), ae);
        }
    }
}
