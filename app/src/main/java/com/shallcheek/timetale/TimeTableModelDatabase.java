package com.shallcheek.timetale;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {TimeTableModel.class},version = 1,exportSchema = false)
public abstract class TimeTableModelDatabase extends RoomDatabase {
    private static TimeTableModelDatabase INSTANCE;
    static synchronized TimeTableModelDatabase getDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),TimeTableModelDatabase.class,"TimeTableModel_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return INSTANCE;
    }

    TimeTableModelDao getTimeTableModelDao() {
        return null;
    }

    static final Migration MIGRATION_1_2 =new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE TimeTableModel ADD COLUMN chineseInvisible INTEGER not null default 0");
        }
    };
}
