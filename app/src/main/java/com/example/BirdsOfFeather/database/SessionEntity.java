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
    public int sessionIndex;

    @ColumnInfo(name = "sessionName")
    public String sessionName;

    public SessionEntity(String sessionName){
        this.sessionName = sessionName;
    }


//    public int getSessionId(){
//        return sessionIndex;
//    }
//
//    public void setSessionId(int sessionId){
//        this.sessionId = sessionId;
//    }
//
//    public String getSessionName(){
//        return sessionName;
//    }
//
//    public void setSessionName(String sessionName){
//        this.sessionName = sessionName;
//    }
//
//    public String getSessionTimeStamp(){
//        return timeStamp;
//    }
//
//    public void setSessionTimeStamp(String timeStamp){
//        this.timeStamp = timeStamp;
//    }




}
