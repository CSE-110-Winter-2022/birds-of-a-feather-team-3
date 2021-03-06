package com.example.BirdsOfFeather.database;

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

    @ColumnInfo(name = "scoreRecent")
    public int scoreRecent;

    @ColumnInfo(name = "scoreClassSize")
    public float scoreClassSize;

    @ColumnInfo(name = "isWaving")
    public boolean isWaving;

    @ColumnInfo(name = "isFavorited")
    public boolean isFavorited;

    public ProfileEntity(String profileName, String profileURL, long profileSessionId,
                         List<Course> profileCourses, String uniqueId, boolean isWaving){
        this.profileName = profileName;
        this.profileURL = profileURL;
        this.profileSessionId = profileSessionId;
        this.profileCourses = profileCourses;
        this.uniqueId = uniqueId;
        this.isWaving = isWaving;
        this.isFavorited = false;
    }

}
