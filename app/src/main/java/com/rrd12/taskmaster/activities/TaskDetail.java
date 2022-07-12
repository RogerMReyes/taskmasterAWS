package com.rrd12.taskmaster.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.StateEnum;
import com.amplifyframework.datastore.generated.model.TaskModel;
import com.amplifyframework.datastore.generated.model.Team;
import com.rrd12.taskmaster.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TaskDetail extends AppCompatActivity {
    public static final String TAG = "taskdetail";
    Spinner teamSpinner = null;
    Spinner stateSpinner = null;
    TaskModel taskToEdit = null;
    private String s3ImageKey = "";
    CompletableFuture<List<Team>> teamFuture;
    CompletableFuture<TaskModel> taskFuture;
    List<Team> teams = new ArrayList<>();
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
        teamFuture = new CompletableFuture<>();
        taskFuture = new CompletableFuture<>();
        activityResultLauncher = getImagePickingActivityResultLauncher();

        setTaskTitle();
        setUpSpinner();
        setUpAddImageButton();
    }

    private void setTaskTitle(){

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
        setUpDeleteButton(finalTaskId);

        TextView taskTitleView = findViewById(R.id.taskDetailTitle);
        TextView taskBodyView = findViewById(R.id.taskDetails);
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
    }

    private void setUpSpinner() {
        teamSpinner = findViewById(R.id.taskDetailTeamSpin);
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

        stateSpinner = findViewById(R.id.taskDetailStateSpin);
        stateSpinner.setAdapter(new ArrayAdapter<>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                StateEnum.values()
        ));
    }

    private void setUpDeleteButton(String id){
        Button deleteButton = findViewById(R.id.deleteTaskB);
        deleteButton.setOnClickListener(v -> {
            Amplify.API.query(
                    ModelQuery.get(TaskModel.class, id),
                    response -> {
                        Log.i(TAG, "successfully got task");
                        taskFuture.complete(response.getData());
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
    }

    private void setUpAddImageButton(){
        Button addImageButton = findViewById(R.id.taskDetailAddImageB);
        addImageButton.setOnClickListener(v -> {
            launchImageSelectionIntent();
        });
    }

    private void launchImageSelectionIntent(){
        Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
        imageFilePickingIntent.setType("*/*");
        imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg","image/png"});
        activityResultLauncher.launch(imageFilePickingIntent);
    }

    private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Uri pickedImageUri = result.getData().getData();
                        try{
                            InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageUri);
                            String pickedImageFileName = getFileNameFromUri(pickedImageUri);
                            uploadInputStreamToS3(pickedImageInputStream, pickedImageFileName, pickedImageUri);
                            Log.i(TAG, "Succeeded in getting input stream from a file on our phone");
                        } catch (FileNotFoundException fnfe){
                            Log.e(TAG, "Could not get file from phone: " + fnfe.getMessage(), fnfe);
                        }
                    }
                }
        );
    }

    private void uploadInputStreamToS3(InputStream pickedImageInputStream, String pickedImageFileName, Uri pickedImageUri){
        Amplify.Storage.uploadInputStream(
                pickedImageFileName,
                pickedImageInputStream,
                success -> {
                    Log.i(TAG, "Succeeded in uploading file to s3: " + success.getKey());
                    s3ImageKey = success.getKey();
//                    ImageView taskImageView = findViewById() //TODO Display image
                    InputStream pickedImageInputStreamCopy = null;
                    try{
                        pickedImageInputStreamCopy = getContentResolver().openInputStream(pickedImageUri);
                    } catch(FileNotFoundException fnfe){
                        Log.e(TAG, "Could not get input stream from uri: " + fnfe.getMessage(), fnfe);
                    }
//                    productImageView.setImageBitmap(BitmapFactory.decodeStream(pickedImageInputStreamCopy)); TODO Display image
                },
                failure -> Log.e(TAG, "Failure in uploading file to S3 with filename: " + pickedImageFileName + " with error: " + failure.getMessage())
        );
    }

    // Taken from https://stackoverflow.com/a/25005243/16889809
    @SuppressLint("Range")
    public String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                assert cursor != null;
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}