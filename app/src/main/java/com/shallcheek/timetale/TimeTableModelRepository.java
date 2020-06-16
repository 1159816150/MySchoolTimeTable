package com.shallcheek.timetale;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TimeTableModelRepository {
    private LiveData<List<TimeTableModel>> allTimeTableModelLive;
    private TimeTableModelDao timeTableModelDao;
    TimeTableModelRepository(Context context){
        TimeTableModelDatabase timeTableModelDatabase = TimeTableModelDatabase.getDatabase(context.getApplicationContext());
        timeTableModelDao =  timeTableModelDatabase.getTimeTableModelDao();
          allTimeTableModelLive= timeTableModelDao.getAllTimeTableModelsLive();
    }
    public LiveData<List<TimeTableModel>> getAllTimeTableModelLive() {
        return allTimeTableModelLive;
    }
    void insertTimeTableModel(TimeTableModel...timeTableModels){
        new InsertAsyncTask(timeTableModelDao).execute(timeTableModels);
    }
    void updateTimeTableModel(TimeTableModel...timeTableModels){
        new UpdateAsyncTask(timeTableModelDao).execute(timeTableModels);
    }
    void deleteTimeTableModel(TimeTableModel...timeTableModels){
        new DeleteAsyncTask(timeTableModelDao).execute(timeTableModels);
    }
    void deleteAllTimeTableModel(TimeTableModel...timeTableModels){
        new DeleteAllAsyncTask(timeTableModelDao).execute();
    }

    static class InsertAsyncTask extends AsyncTask<TimeTableModel,Void,Void>
    {
        private   TimeTableModelDao timeTableModelDao;
        public InsertAsyncTask(TimeTableModelDao timeTableModelDao) {
            this.timeTableModelDao = timeTableModelDao;
        }
        @Override
        protected Void doInBackground(TimeTableModel... timeTableModels) {
            timeTableModelDao.insertTimeTableModel(timeTableModels);
            return null;
        }
    }
    static class UpdateAsyncTask extends AsyncTask<TimeTableModel,Void,Void>
    {
        private TimeTableModelDao timeTableModelDao;
        public UpdateAsyncTask(TimeTableModelDao timeTableModelDao) {
            this.timeTableModelDao = timeTableModelDao;
        }

        @Override
        protected Void doInBackground(TimeTableModel... timeTableModels) {
            timeTableModelDao.updateTimeTableModel(timeTableModels);
            return null;
        }
    }
    static class DeleteAsyncTask extends AsyncTask<TimeTableModel,Void,Void>
    {
        private TimeTableModelDao timeTableModelDao;
        public DeleteAsyncTask(TimeTableModelDao timeTableModelDao) {
            this.timeTableModelDao = timeTableModelDao;
        }

        @Override
        protected Void doInBackground(TimeTableModel... timeTableModels) {
            timeTableModelDao.deleteTimeTableModel(timeTableModels);
            return null;
        }
    }
    static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private TimeTableModelDao timeTableModelDao;
        public DeleteAllAsyncTask(TimeTableModelDao timeTableModelDao) {
            this.timeTableModelDao = timeTableModelDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            timeTableModelDao.deleteAllTimeTableModel();
            return null;
        }
    }
}
