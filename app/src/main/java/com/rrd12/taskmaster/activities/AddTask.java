package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.rrd12.taskmaster.R;
import com.rrd12.taskmaster.database.TaskMasterDatabase;
import com.rrd12.taskmaster.models.StateEnum;
import com.rrd12.taskmaster.models.Task;

import java.util.Date;

public class AddTask extends AppCompatActivity {

    TaskMasterDatabase taskMasterDatabase;
    public static final String DATABASE_NAME = "task_list";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskMasterDatabase = Room.databaseBuilder(
                        getApplicationContext(),
                        TaskMasterDatabase.class,
                        DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        setUpSpinner();
        setUpTotalTask();
        setUpAddButton(taskMasterDatabase);
    }

    private void setUpSpinner() {
        Spinner taskStateSpinner = findViewById(R.id.taskStateSpinner);
        taskStateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                StateEnum.values()
        ));
    }

    private void setUpAddButton(TaskMasterDatabase database){
        Button addTask = AddTask.this.findViewById(R.id.addTaskB);
        Spinner taskStateSpinner = findViewById(R.id.taskStateSpinner);
        addTask.setOnClickListener(v -> {
            String taskTitleInput = ((EditText)findViewById(R.id.taskTitleInput)).getText().toString();
            String taskBodyInput = ((EditText) findViewById(R.id.taskBodyInput)).getText().toString();
            Date newDate = new Date();
            StateEnum state = StateEnum.fromString(taskStateSpinner.getSelectedItem().toString());

            Task newTask = new Task(taskTitleInput,taskBodyInput,newDate, state);
            database.taskDao().insertATask(newTask);
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