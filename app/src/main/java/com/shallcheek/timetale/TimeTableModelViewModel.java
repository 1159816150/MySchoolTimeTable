package com.shallcheek.timetale;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TimeTableModelViewModel  extends AndroidViewModel {
TimeTableModelDao timeTableModelDao;
TimeTableModelRepository timeTableModelRepository;
    public TimeTableModelViewModel(@NonNull Application application) {
        super(application);
        timeTableModelRepository=new TimeTableModelRepository(application);
    }
    LiveData<List<TimeTableModel>> getAllTimeTableModelLive() {
        return timeTableModelRepository.getAllTimeTableModelLive();
    }

    void insertTimeTableModel(TimeTableModel... timeTableModels) {
        timeTableModelRepository.insertTimeTableModel(timeTableModels);
    }

    void updateTimeTableModel(TimeTableModel... timeTableModels) {
        timeTableModelRepository.updateTimeTableModel(timeTableModels);
    }

    void deleteTimeTableModel(TimeTableModel... timeTableModels) {
        timeTableModelRepository.deleteTimeTableModel(timeTableModels);
    }

    void deleteAllTimeTableModel() {
        timeTableModelRepository.deleteAllTimeTableModel();
    }
}
