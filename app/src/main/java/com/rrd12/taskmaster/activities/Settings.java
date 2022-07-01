package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.rrd12.taskmaster.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Settings extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String TAG = "Settings";
    public static final String USERNAME = "username";
    public static final String TEAM = "team";
    Spinner teamSpinner = null;
    CompletableFuture<List<Team>> teamFuture;
    List<Team> teams = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        showCurrentUser();
        teamFuture = new CompletableFuture<>();
        setUpSpinner();

        Button updateButton = findViewById(R.id.updateInfoB);
        updateButton.setOnClickListener(v -> {
            updateUsername();
            updateTeam();
            showCurrentUser();
            Toast.makeText(Settings.this, "Updated!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpSpinner() {
        teamSpinner = findViewById(R.id.settingsTeamSpinner);
        Amplify.API.query(
                ModelQuery.list(Team.class),
                success -> {
                    Log.i(TAG, "Read Teams successfully!");
                    ArrayList<String> teamNames = new ArrayList<>();

                    for (Team team : success.getData()) {
                        teams.add(team);
                        teamNames.add(team.getName());
                    }
                    teamFuture.complete(teams);

                    runOnUiThread(() -> {
                        teamSpinner.setAdapter(new ArrayAdapter<>(
                                this,
                                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                                teamNames));
                    });
                },
                failure -> {
                    teamFuture.complete(null);
                    Log.e(TAG, "Did not read teams successfully");
                }
        );
    }

    private void updateUsername() {
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        EditText usernameInput = findViewById(R.id.usernameInput);
        String usernameString = usernameInput.getText().toString();

        preferenceEditor.putString(USERNAME, usernameString);
        preferenceEditor.apply();
    }

    private void updateTeam(){
        SharedPreferences.Editor preferenceEditor = preferences.edit();
        String team = teamSpinner.getSelectedItem().toString();

        preferenceEditor.putString(TEAM, team);
        preferenceEditor.apply();
    }

    private void showCurrentUser() {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String username = preferences.getString(USERNAME, "");
        if (!username.isEmpty()) {
            TextView currentUser = findViewById(R.id.currentUser);
            EditText usernameInput = findViewById(R.id.usernameInput);
            currentUser.setText(username);
            usernameInput.setText(username);
        }
    }
}