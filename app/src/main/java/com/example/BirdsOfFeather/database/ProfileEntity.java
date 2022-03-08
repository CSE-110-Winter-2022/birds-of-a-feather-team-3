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
    public long profileSessionId;

    @ColumnInfo(name = "name")
    public String profileName;

    @ColumnInfo(name = "URL")
    public String profileURL;

    @ColumnInfo(name = "profileCourses")
    public List<Course> profileCourses;

    @ColumnInfo(name = "uniqueId")
    public String uniqueId;


    public ProfileEntity(String profileName, String profileURL, long profileSessionId, List<Course> profileCourses, String uniqueId){
        this.profileName = profileName;
        this.profileURL = profileURL;
        this.uniqueId = uniqueId;
        this.profileSessionId = profileSessionId;
        this.profileCourses = profileCourses;
    }

}
