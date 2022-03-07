package com.example.BirdsOfFeather.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.BirdsOfFeather.Course;

import java.util.List;


@Entity(tableName = "profiles")
public class ProfileEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int profileId;

    @ColumnInfo(name = "session_id")
    public int profileSessionId;

    @ColumnInfo(name = "name")
    public String profileName;

    @ColumnInfo(name = "URL")
    public String profileURL;

    @ColumnInfo(name = "profileCourses")
    public List<Course> profileCourses;


    public ProfileEntity(String profileName, String profileURL, int profileSessionId, List<Course> profileCourses){
        this.profileName = profileName;
        this.profileURL = profileURL;
        //this.profileId = profileId;
        this.profileSessionId = profileSessionId;
        this.profileCourses = profileCourses;
    }

}
