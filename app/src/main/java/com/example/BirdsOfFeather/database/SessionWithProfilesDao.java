package com.example.BirdsOfFeather.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SessionWithProfilesDao {
    @Transaction
    @Query("SELECT * FROM sessions")
    List<SessionWithProfiles> getAll();

    @Query("SELECT * FROM sessions WHERE sessionId=:sessionId")
    SessionWithProfiles get(int sessionId);

//    @Insert
//    void insertSession(SessionEntity sessionEntity);

//    @Update
//    void updateSession(SessionEntity sessionEntity);

//    @Delete
//    void deleteSession(SessionEntity sessionEntity);

}
