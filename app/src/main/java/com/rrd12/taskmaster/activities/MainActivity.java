package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

import com.rrd12.taskmaster.R;
import com.rrd12.taskmaster.adapter.TaskListRecViewAdapter;
import com.rrd12.taskmaster.database.TaskMasterDatabase;
import com.rrd12.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences preferences;
    TaskMasterDatabase taskMasterDatabase;
    public static final String TASK_TITLE = "Task Title";
    public static final String TASK_BODY = "Task Body";
    public static final String TASK_STATE = "Task State";
    public static final String DATABASE_NAME = "task_list";
    public static final String TASK_SIZE = "size";
    TaskListRecViewAdapter adapter;
    List<Task> tasks = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        taskMasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskMasterDatabase.class,
                        DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        tasks = taskMasterDatabase.taskDao().findAll();
        taskListRecView();

        setUpAddTask();
        simpleButtonActivity(R.id.allTasksButton, AllTasks.class);
        simpleButtonActivity(R.id.settingB, Settings.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUsername();

        tasks.clear();
        tasks.addAll(taskMasterDatabase.taskDao().findAll());
        adapter.notifyDataSetChanged();
    }

    private <T> void simpleButtonActivity(int id, Class<T> c){
        buttonGo(findViewById(id), new Intent(MainActivity.this, c));
    }
    private void buttonGo(Button button, Intent goWhere){
        button.setOnClickListener(v->{
            startActivity(goWhere);
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
}