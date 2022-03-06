package com.example.BirdsOfFeather.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class SessionWithProfiles {
    @Embedded
    public SessionEntity sessionEntity;

    @Relation(
            parentColumn = "sessionId",
            entityColumn = "profileSessionId",
            entity = ProfileEntity.class
    )
    public List<ProfileEntity> profileEntities;

    public SessionWithProfiles(SessionEntity sessionEntity, List<ProfileEntity> profileEntities){
        this.sessionEntity = sessionEntity;
        this.profileEntities = profileEntities;
    }
}
