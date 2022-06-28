package com.rrd12.taskmaster.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class TaskMasterDatabaseConverters {
    @TypeConverter
    public static Date fromTimeStamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimeStamps(Date date){
        return date == null ? null : date.getTime();
    }
}
