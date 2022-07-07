package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.rrd12.taskmaster.R;

public class VerifyUser extends AppCompatActivity {
    public static final String TAG = "verifyactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_user);
        verifyUser();
    }

    private void verifyUser(){
        Button verify = findViewById(R.id.verifyButton);

        verify.setOnClickListener(v -> {
        String verifyCode = ((EditText)findViewById(R.id.verificationInput)).getText().toString();
        String verifyEmail = ((EditText)findViewById(R.id.verifyEmail)).getText().toString();

            Amplify.Auth.confirmSignUp(
                    verifyEmail,
                    verifyCode,
                    success -> {
                        Log.i(TAG, "Verification succeeded: " + success);
                    },
                    failure -> {
                        Log.i(TAG, "Verification failed: " + failure);
                    }
            );
            Toast.makeText(VerifyUser.this, "User Verified!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}