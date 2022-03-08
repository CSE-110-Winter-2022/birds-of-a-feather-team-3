package com.example.BirdsOfFeather.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Transaction;

import com.example.BirdsOfFeather.Course;
import com.example.BirdsOfFeather.Session;

import java.util.List;

@Dao
public interface SessionDao {

//    @Transaction
//    @Insert
//    void insertSessionEntity(SessionEntity sessionEntity);
//
//    @Insert
//    void insertProfileEntities(List<ProfileEntity> profileEntities);

    @Transaction
    @Query("SELECT * FROM sessions")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    List<Session> getAll();

    @Query("SELECT * FROM sessions WHERE id=:id")
    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    Session getSession(int id);

    @Insert
    void insert(SessionEntity session);

}
