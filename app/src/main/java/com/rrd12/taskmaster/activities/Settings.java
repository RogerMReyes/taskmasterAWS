package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rrd12.taskmaster.R;

public class Settings extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        showCurrentUser();

        Button updateButton = findViewById(R.id.updateInfoB);
        updateButton.setOnClickListener(v -> {
            updateUsername();
            showCurrentUser();
        });
    }

    private void updateUsername(){
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        EditText usernameInput = findViewById(R.id.usernameInput);
        String usernameString = usernameInput.getText().toString();

        preferenceEditor.putString(USERNAME, usernameString);
        preferenceEditor.apply();

        Toast.makeText(Settings.this, "Username Updated!", Toast.LENGTH_SHORT).show();
    }

    private void showCurrentUser(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString(USERNAME, "");
        if(!username.isEmpty()){
            TextView currentUser = findViewById(R.id.currentUser);
            EditText usernameInput = findViewById(R.id.usernameInput);
            currentUser.setText(username);
            usernameInput.setText(username);
        }
    }
}