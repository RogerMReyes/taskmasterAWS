package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.rrd12.taskmaster.R;

public class Login extends AppCompatActivity {
    public static final String TAG = "loginactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login();
        signUp();
        verifyUserEmail();
    }

    private void login(){
        Button logInButton = findViewById(R.id.logInButton);
        logInButton.setOnClickListener(v -> {
        String email = ((EditText)findViewById(R.id.loginEmailInput)).getText().toString();
        String password = ((EditText)findViewById(R.id.loginPasswordInput)).getText().toString();
            Amplify.Auth.signIn(
                    email,
                    password,
                    success ->{
                        Log.i(TAG, "Login succeeded: " + success);
                        Intent goToMainIntent = new Intent(Login.this,MainActivity.class);
                        startActivity(goToMainIntent);
                    },
                    failure ->{
                        Log.i(TAG, "Login failed: " + failure);
                    });
        });
    }

    private void signUp(){
        Button signup = findViewById(R.id.signUpButton);
        signup.setOnClickListener(v -> {
            Intent goToSignUp = new Intent(Login.this, SignUp.class);
            startActivity(goToSignUp);
        });
    }

    private void verifyUserEmail(){
        Button verify = findViewById(R.id.verifyEmailLogin);
        verify.setOnClickListener(v -> {
            Intent goToVerifyIntent = new Intent(Login.this, VerifyUser.class);
            startActivity(goToVerifyIntent);
        });
    }
}