package com.rrd12.taskmaster.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.rrd12.taskmaster.R;

public class AllTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_tasks);

        Button allTaskHome = AllTasks.this.findViewById(R.id.allTaskHomeButton);
        allTaskHome.setOnClickListener(v -> {
            Intent goHome = new Intent(AllTasks.this, MainActivity.class);
            startActivity(goHome);
        });
    }
}