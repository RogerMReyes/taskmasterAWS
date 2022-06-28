package com.rrd12.taskmaster.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.rrd12.taskmaster.models.Task;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insertATask(Task task);

    @Query("SELECT * FROM Task")
    List<Task> findAll();

    @Query("SELECT * FROM Task WHERE id = :id")
    Task findByAnId(Long id);
}
