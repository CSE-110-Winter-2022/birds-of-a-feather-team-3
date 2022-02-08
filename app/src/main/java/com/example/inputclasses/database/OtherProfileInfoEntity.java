package com.example.inputclasses.database;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;


@Entity(tableName = "OtherProfileInfo")
public class OtherProfileInfoEntity {
    @PrimaryKey
    @ColumnInfo(name = "")
    public String profileAttribute;

    @ColumnInfo(name = "firstName")
    public String firstName;
    @ColumnInfo(name = "profilePictureLink")
    public String profilePictureLink;

    public OtherProfileInfoEntity(String firstName, String profilePictureLink) {
        this.firstName = firstName;
        this.profilePictureLink = profilePictureLink;
    }
}
