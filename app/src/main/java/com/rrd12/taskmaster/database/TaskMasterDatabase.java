package com.rrd12.taskmaster.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.rrd12.taskmaster.dao.TaskDao;
import com.rrd12.taskmaster.models.Task;

@Database(entities = {Task.class}, version = 1)
@TypeConverters({TaskMasterDatabaseConverters.class})
public abstract class TaskMasterDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
