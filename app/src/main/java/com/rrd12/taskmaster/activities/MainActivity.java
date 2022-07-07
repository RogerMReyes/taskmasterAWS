package com.rrd12.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.rrd12.taskmaster.R;
import com.rrd12.taskmaster.adapter.TaskListRecViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String TASK_ID = "Task";
    public static final String TASK_TITLE = "Task Title";
    public static final String TASK_BODY = "Task Body";
    public static final String TASK_STATE = "Task State";
    public static final String TASK_SIZE = "size";
    public static final String TAG = "homeactivity";
    TaskListRecViewAdapter adapter;
    List<TaskModel> tasks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tasks = new ArrayList<>();

        pullTasksFromDB();
        taskListRecView();
        setUpAddTask();
        setUpSettings();
        setUpLogout();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUsername();

        pullTasksFromDB();
        taskListRecView();
    }

    private void setUpSettings(){
        ImageView settings = findViewById(R.id.settingB);
        settings.setOnClickListener(v -> {
            Intent goToSettings = new Intent(MainActivity.this, Settings.class);
            startActivity(goToSettings);
        });
    }

    private void setUpAddTask(){
        findViewById(R.id.addTaskButton).setOnClickListener(v ->{
            Intent goToAddTask = new Intent(MainActivity.this, AddTask.class);
            goToAddTask.putExtra(TASK_SIZE,tasks.size());
            startActivity(goToAddTask);
        });
    }

    private void updateUsername(){
        String username = preferences.getString(Settings.USERNAME, "My Tasks");
        String formattedTitle = String.format("%s's Tasks",username);
        TextView usernameTaskTitle = findViewById(R.id.usernameTaskTitle);
        usernameTaskTitle.setText(formattedTitle);
    }

    private void taskListRecView(){
        RecyclerView taskList = findViewById(R.id.taskList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        taskList.setLayoutManager(layoutManager);
        adapter = new TaskListRecViewAdapter(tasks, this);
        taskList.setAdapter(adapter);
    }

    private void pullTasksFromDB(){
        String username = preferences.getString(Settings.TEAM, "SealSix");
        Amplify.API.query(
                ModelQuery.list(TaskModel.class),
                success ->{
                    Log.i(TAG, "Read tasks successfully!");
                    tasks.clear();
                    for(TaskModel task : success.getData()){
                        String taskTeam = task.getTeam().getName();
                        if(taskTeam.equals(username))
                        tasks.add(task);
                    }
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                    });
                },
                failure -> Log.i(TAG,"Did not read tasks successfully!")
        );
    }

    private void setUpLogout(){
        ImageView logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(v -> {
            Amplify.Auth.signOut(
                    () ->
                    {
                        Log.i(TAG, "Logout succeeded!");
                    },
                    failure ->
                    {
                        Log.i(TAG, "Logout failed: " + failure);
                    }
            );
            Toast.makeText(MainActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}