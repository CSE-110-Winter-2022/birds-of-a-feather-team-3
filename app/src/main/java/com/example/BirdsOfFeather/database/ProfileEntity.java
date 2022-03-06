package com.example.BirdsOfFeather.database;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.BirdsOfFeather.Course;

import java.util.List;

//@Entity(foreignKeys = @ForeignKey(entity = SessionEntity.class, parentColumns = "sessionId", childColumns = "profileSessionId", onUpdate = CASCADE), tableName = "profiles")
@Entity(tableName = "profiles")
public class ProfileEntity {

    @PrimaryKey//(autoGenerate = true)
    public int profileId;

    public int profileSessionId;

    @ColumnInfo(name = "name")
    public String profileName;

    @ColumnInfo(name = "URL")
    public String profileURL;

    @ColumnInfo(name = "commonCourses")
    public List<Course> profileCommonCourses;

//    public int getProfileId(){
//        return profileId;
//    }
//
//    public void setProfileId(int profileId){
//        this.profileId = profileId;
//    }
//
//    public String getProfileName(){
//        return profileName;
//    }
//
//    public void setProfileName(String profileName){
//        this.profileName = profileName;
//    }
//
//    public String getProfileURL(){
//        return profileURL;
//    }
//
//    public void setProfileURL(String profileURL){
//        this.profileURL = profileURL;
//    }
//
//    public String getProfileCommonCourses(){
//        return profileCommonCourses;
//    }
//
//    public void setProfileCommonCourses(String profileCommonCourses){
//        this.profileCommonCourses = profileCommonCourses;
//    }

//    public int getProfileSessionId(){
//        return profileSessionId;
//    }

//    public void setProfileSessionId(int profileSessionId){
//        this.profileSessionId = profileSessionId;
//    }

    public ProfileEntity(String profileName, String profileURL, List<Course> profileCommonCourses){
        this.profileName = profileName;
        this.profileURL = profileURL;
        this.profileCommonCourses = profileCommonCourses;
    }

}
