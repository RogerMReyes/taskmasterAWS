package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
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
import com.rrd12.taskmaster.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTask extends AppCompatActivity {
    public static final String TAG = "AddTask";
    Spinner teamSpinner = null;
    CompletableFuture<List<Team>> teamFuture;
    List<Team> teams = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        teamFuture = new CompletableFuture<>();
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
}