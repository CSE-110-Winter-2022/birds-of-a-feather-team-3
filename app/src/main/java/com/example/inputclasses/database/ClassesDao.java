package com.example.inputclasses.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.inputclasses.Course;

import java.util.List;

@Dao
public interface ClassesDao {
    @Transaction
    @Query("SELECT * FROM classes")
    List<Course> getAll();

    @Query("SELECT * FROM classes WHERE id=:id")
    Course getCourse(int id);

    @Insert
    void insert(ClassEntity course);
}
