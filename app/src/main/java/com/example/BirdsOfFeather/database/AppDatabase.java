package com.example.BirdsOfFeather.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities= {ClassEntity.class, SessionEntity.class}, version = 2, exportSchema = false)
//@Database(entities = {ClassEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context) {
        if (singletonInstance == null) {
            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "BoF.db")
                    //.createFromAsset("starter-classes.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return singletonInstance;
    }

    public abstract ClassesDao classesDao();
    public abstract SessionDao sessionDao();
    //public abstract SessionWithProfilesDao sessionWithProfilesDao();
}
