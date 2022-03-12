package com.example.BirdsOfFeather.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities= {ClassEntity.class, SessionEntity.class, ProfileEntity.class}, version = 1, exportSchema = false)
@TypeConverters({CourseListTypeConverter.class})
//@Database(entities = {ClassEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context) {
        if (singletonInstance == null) {
            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "BoF.db")
                    .allowMainThreadQueries()
                    .build();
        }
        return singletonInstance;
    }

    public static void useTestSingleton(Context context) {
        singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    public abstract ClassesDao classesDao();
    public abstract SessionDao sessionDao();
    public abstract ProfilesDao profilesDao();
    public abstract SessionWithProfilesDao sessionWithProfilesDao();
}
