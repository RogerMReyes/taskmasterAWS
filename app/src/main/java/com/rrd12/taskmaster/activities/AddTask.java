package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.StateEnum;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.rrd12.taskmaster.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTask extends AppCompatActivity {
    public static final String TAG = "AddTask";
    private Spinner teamSpinner = null;
    private String currentLat = null;
    private String currentLon = null;
    private CompletableFuture<List<Team>> teamFuture;
    private CompletableFuture<String> latFuture;
    private CompletableFuture<String> lonFuture;
    private List<Team> teams = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient = null;
    private Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        teamFuture = new CompletableFuture<>();
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        fusedLocationClient.flushLocations();
        setUpLocation();
        setUpSpinner();
        setUpTotalTask();
        setUpAddButton();
    }

    private void setUpSpinner() {
        teamSpinner = findViewById(R.id.taskTeamSpinner);
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

        Spinner taskStateSpinner = findViewById(R.id.taskStateSpinner);
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                StateEnum.values()
        ));
    }

    private void setUpAddButton(){
        Button addTask = AddTask.this.findViewById(R.id.addTaskB);
        Spinner taskStateSpinner = findViewById(R.id.taskStateSpinner);
        addTask.setOnClickListener(v -> {
//           setUpLocation();
            String taskTitleInput = ((EditText)findViewById(R.id.taskTitleInput)).getText().toString();
            String taskBodyInput = ((EditText) findViewById(R.id.taskBodyInput)).getText().toString();
            String currentDateString = com.amazonaws.util.DateUtils.formatISO8601Date(new Date());
            try{
                teams = teamFuture.get();
            } catch (InterruptedException ie){
                Log.e(TAG, "InterruptedException while getting teams");
                Thread.currentThread().interrupt();
            } catch (ExecutionException ee) {
                Log.e(TAG, "ExecutionException while getting teams");
            }
            String selectedTeamString = teamSpinner.getSelectedItem().toString();
            Team selectedTeam = teams.stream()
                    .filter(team -> team.getName().equals(selectedTeamString)).findAny().orElseThrow(RuntimeException::new);

            TaskModel newTask = TaskModel.builder()
                    .title(taskTitleInput)
                    .body(taskBodyInput)
                    .dateCreated(new Temporal.DateTime(currentDateString))
                    .state((StateEnum)taskStateSpinner.getSelectedItem())
                    .team(selectedTeam)
                    .lat(currentLat)
                    .lon(currentLon)
                    .build();

            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    successResponse -> Log.i(TAG, "AddTaskModelActivity.onCreate(): made a task successfully"),
                    failureResponse -> Log.i(TAG, "AddTaskModelActivity.onCreate(): failed with this response: " + failureResponse)
            );

            Toast.makeText(AddTask.this, "Task Added", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void setUpTotalTask(){
        Intent callingIntent = getIntent();
        int size = callingIntent.getIntExtra(MainActivity.TASK_SIZE, 0);
        TextView totalTaskView = findViewById(R.id.totalTaskView);
        totalTaskView.setText("Total Tasks: " + size);
    }

    private void setUpLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if(location == null){
                Log.e(TAG, "Location was null");
            }
            currentLat = Double.toString(location.getLatitude());
            currentLon = Double.toString(location.getLongitude());
            Log.i(TAG, "Our Lat: " + currentLat);
            Log.i(TAG, "Our Lon: " + currentLon);
        });
    }
}