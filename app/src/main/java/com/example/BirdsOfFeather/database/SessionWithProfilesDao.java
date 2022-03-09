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

    @Query("SELECT * FROM sessions WHERE id=:id")
    SessionWithProfiles get(long id);

    @Query("SELECT COUNT(*) from sessions")
    int count();

}
