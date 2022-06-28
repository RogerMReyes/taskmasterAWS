package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.rrd12.taskmaster.R;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        setTaskTitle();
    }

    private void setTaskTitle(){
        Intent callingIntent = getIntent();
        String taskTitle = "";
        String taskBody = "";
        String taskState = "";
        if(callingIntent != null){
            taskTitle = callingIntent.getStringExtra(MainActivity.TASK_TITLE);
            taskBody = callingIntent.getStringExtra(MainActivity.TASK_BODY);
            taskState = callingIntent.getStringExtra(MainActivity.TASK_STATE);
        }

        TextView taskTitleView = findViewById(R.id.taskDetailTitle);
        TextView taskBodyView = findViewById(R.id.taskDetails);
        TextView taskStateView = findViewById(R.id.taskState);
        if(taskTitle != null){
            taskTitleView.setText(taskTitle);
        }
        else{
            taskTitleView.setText(R.string.no_title);
        }
        if(taskBody != null){
            taskBodyView.setText(taskBody);
        }
        else{
            taskBodyView.setText(R.string.no_body);
        }
        if(taskState != null){
            taskStateView.setText(taskState);
        }
        else{
            taskStateView.setText(R.string.no_state);
        }
    }
}