package com.shallcheek.timetale;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimeTableModelDao {
    @Insert
    void insertTimeTableModel(TimeTableModel... timeTableModels);
    @Update
    void  updateTimeTableModel(TimeTableModel... timeTableModels);
    @Delete
    void deleteTimeTableModel(TimeTableModel... timeTableModels);
    @Query("delete from TimeTableModel")
    void  deleteAllTimeTableModel();
    @Query("select * from TimeTableModel")
    LiveData<List<TimeTableModel>> getAllTimeTableModelsLive();
}
