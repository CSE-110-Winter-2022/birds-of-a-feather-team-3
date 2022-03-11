package com.example.BirdsOfFeather.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.BirdsOfFeather.ProfileInfo;

import java.util.List;

@Dao
public interface ProfilesDao {

    @Insert
    long insert(ProfileEntity profile);

    @Transaction
    @Query("SELECT * FROM profiles WHERE session_id=:sessionId")
    List<ProfileInfo> getForSession(long sessionId);

    @Query("SELECT * FROM profiles WHERE id=:id")
    ProfileInfo get(long id);

    @Query("SELECT * FROM profiles WHERE id=:id")
    ProfileEntity getEntity(long id);

    @Query("SELECT * FROM profiles WHERE uniqueId=:uniqueId AND session_id=:sessionId")
    ProfileEntity searchFavorite(String uniqueId, long sessionId);

    @Query("SELECT COUNT(*) from profiles")
    int count();

    @Query("UPDATE profiles SET isWaving=:wave WHERE id=:id")
    void updateWave(boolean wave, long id);

    @Delete
    void delete(ProfileEntity profileEntity);
}
