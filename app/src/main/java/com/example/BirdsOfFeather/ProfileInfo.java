package com.example.BirdsOfFeather;

import java.util.List;

//Stores information of another user in relation to the app user
public class ProfileInfo {
    List<Course> profileCourses;
    String name;
    String URL;
    String uniqueId;
    public long id;
    private int scoreRecent;
    private float scoreClassSize;
    private boolean isWaving;
    private boolean isFavorited;
    public ProfileInfo(String name, String URL, List<Course> profileCourses, String uniqueId) {
        this.name = name;
        this.URL = URL;
        this.profileCourses = profileCourses;
        this.uniqueId = uniqueId;
        this.isWaving = false;
        this.isFavorited = false;
        this.scoreRecent = 0;
        this.scoreClassSize = 0;
        this.id = 0;
    }

    public long getProfileId() {
        return this.id;
    }
    public void setProfileId(long profileId) {this.id = profileId;}

    public List<Course> getCommonCourses() {
        return this.profileCourses;
    }
    public String getName() {
        return this.name;
    }
    public String getURL() {
        return this.URL;
    }
    public String getUniqueId() {
        return this.uniqueId;
    }
    public String toString() {
        return this.uniqueId;
    }
    public void setScoreRecent(int scoreRecent) {this.scoreRecent = scoreRecent;}
    public void setScoreClassSize(float scoreClassSize) {this.scoreClassSize = scoreClassSize;}
    public int getScoreRecent() {return scoreRecent;}
    public float getScoreClassSize() {return scoreClassSize;}
    public boolean getIsWaving() {
        return this.isWaving;
    }
    public void setWaving(boolean isWaving) {
        this.isWaving = isWaving;
    }
    public boolean getIsFavorited() {
        return this.isFavorited;
    }
    public void setFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }
}
