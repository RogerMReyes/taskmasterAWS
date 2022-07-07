package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.rrd12.taskmaster.R;

public class SignUp extends AppCompatActivity {
    public static final String TAG = "signupactivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        createUser();
    }

    private void createUser(){
        Button signUpButton = findViewById(R.id.createUserButton);
        signUpButton.setOnClickListener(v -> {
            String email = ((EditText)findViewById(R.id.signUpEmailInput)).getText().toString();
            String password = ((EditText)findViewById(R.id.signUpPasswordInput)).getText().toString();
                Amplify.Auth.signUp(
                        email,
                        password,
                        AuthSignUpOptions.builder()
                                .userAttribute(AuthUserAttributeKey.email(), email)
                                .build(),
                        success -> {
                            Log.i(TAG, "Signup succeeded " + success);
                        },
                        failure -> {
                            Log.i(TAG, "Signup failed with message: " + failure);
                        }
                );
            Toast.makeText(SignUp.this, "User Created!", Toast.LENGTH_SHORT).show();
            Intent goToVerifyIntent = new Intent(SignUp.this,VerifyUser.class);
            startActivity(goToVerifyIntent);
        });
    }
}