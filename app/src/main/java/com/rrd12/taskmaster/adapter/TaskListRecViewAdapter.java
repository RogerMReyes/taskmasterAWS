package com.rrd12.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rrd12.taskmaster.R;
import com.rrd12.taskmaster.activities.MainActivity;
import com.rrd12.taskmaster.activities.TaskDetail;
import com.rrd12.taskmaster.models.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TaskListRecViewAdapter extends RecyclerView.Adapter<TaskListRecViewAdapter.TaskListViewHolder> {

    List<Task> tasks;
    Context callingActivity;

    public TaskListRecViewAdapter(List<Task> _tasks, Context _callingActivity) {
        this.tasks = _tasks;
        this.callingActivity = _callingActivity;
    }

    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFrag = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);
        return new TaskListViewHolder(taskFrag);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, int position) {
        TextView taskFragView = holder.itemView.findViewById(R.id.taskFragView);
        String taskTitle = tasks.get(position).getTitle();
        String taskBody = tasks.get(position).getBody();
        Date taskDate = tasks.get(position).getDateCreated();
        String date = DateFormat.getDateInstance().format(taskDate);
        String taskState = tasks.get(position).getState().toString();
        taskFragView.setText("-" + taskTitle + "\n"
                                + date + "\n"
                                + taskState);

        View taskView = holder.itemView;
        taskView.setOnClickListener(v -> {
            Intent goToTaskDetail = new Intent(callingActivity, TaskDetail.class);
            goToTaskDetail.putExtra(MainActivity.TASK_TITLE, taskTitle);
            goToTaskDetail.putExtra(MainActivity.TASK_BODY, taskBody);
            goToTaskDetail.putExtra(MainActivity.TASK_STATE, taskState);
            callingActivity.startActivity(goToTaskDetail);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder{
        public TaskListViewHolder(View fragmentTaskView){
            super(fragmentTaskView);
        }
    }
}
