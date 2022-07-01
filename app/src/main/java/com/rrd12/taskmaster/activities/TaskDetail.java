package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.rrd12.taskmaster.R;

public class TaskDetail extends AppCompatActivity {
    public static final String TAG = "taskdetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        setTaskTitle();
    }

    private void setTaskTitle(){
        Button deleteButton = findViewById(R.id.deleteTaskB);
        Intent callingIntent = getIntent();
        String taskTitle = "";
        String taskBody = "";
        String taskState = "";
        String taskId = "";
        if(callingIntent != null){
            taskTitle = callingIntent.getStringExtra(MainActivity.TASK_TITLE);
            taskBody = callingIntent.getStringExtra(MainActivity.TASK_BODY);
            taskState = callingIntent.getStringExtra(MainActivity.TASK_STATE);
            taskId = callingIntent.getStringExtra(MainActivity.TASK_ID);
        }
        String finalTaskId = taskId;
        deleteButton.setOnClickListener(v -> {
            Amplify.API.query(
                    ModelQuery.get(TaskModel.class, finalTaskId),
                    response -> {
                        Log.i(TAG, "successfully got task");
                        Amplify.API.mutate(
                                ModelMutation.delete(response.getData()),
                                success -> Log.i(TAG, "successfully deleted"),
                                error -> Log.e(TAG, "failed to delete")
                        );
                    },
                    error -> Log.e(TAG, "failed getting task")
            );
            finish();
        });


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