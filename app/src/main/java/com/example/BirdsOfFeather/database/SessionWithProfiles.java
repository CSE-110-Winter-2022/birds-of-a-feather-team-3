package com.example.BirdsOfFeather.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.BirdsOfFeather.ProfileInfo;

import java.util.List;

public class SessionWithProfiles{
    @Embedded
    public SessionEntity sessionEntity;

    @Relation(
            parentColumn = "id",
            entityColumn = "session_id",
            entity = ProfileEntity.class)
//            projection = {"name"}
    public List<ProfileInfo> profileEntities;

    public int getId(){return this.sessionEntity.sessionIndex;}

    public String getName(){return this.sessionEntity.sessionName;}

    public List<ProfileInfo> getProfiles(){return this.profileEntities;}




}
