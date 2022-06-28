package com.rrd12.taskmaster.database;

import java.util.Date;

public class TaskMasterDatabaseConverters {
    public static Date fromTimeStamp(Long value){
        return value == null ? null : new Date(value);
    }

    public static Long dateToTimeStamps(Date date){
        return date == null ? null : date.getTime();
    }
}
