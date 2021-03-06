package com.example.BirdsOfFeather.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import com.example.BirdsOfFeather.Course;

import java.util.List;

@Dao
public interface ClassesDao {
    @Transaction
    @Query("SELECT * FROM classes")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<Course> getAll();

    @Query("SELECT * FROM classes WHERE id=:id")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    Course getCourse(int id);

    @Insert
    long insert(ClassEntity course);

    // for testing purposes
    @Delete
    void delete(ClassEntity course);

//    // for testing
//    @Query("SELECT COUNT(*) FROM classes")
//    int count();

}
