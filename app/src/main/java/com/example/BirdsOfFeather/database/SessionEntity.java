package com.example.BirdsOfFeather.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.BirdsOfFeather.ProfileInfo;

import java.util.List;

@Entity(tableName = "sessions")
public class SessionEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "sessionName")
    public String sessionName;

    public SessionEntity(String sessionName){
        this.sessionName = sessionName;
    }
}
