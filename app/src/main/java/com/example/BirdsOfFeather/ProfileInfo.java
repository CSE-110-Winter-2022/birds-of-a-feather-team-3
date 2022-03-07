package com.example.BirdsOfFeather;

import java.util.List;

//Stores information of another user in relation to the app user
public class ProfileInfo {
    List<Course> commonCourses;
    String name;
    String URL;
    String uniqueId;
    public ProfileInfo(String name, String URL, List<Course> commonCourses, String uniqueId) {
        this.name = name;
        this.URL = URL;
        this.commonCourses = commonCourses;
        this.uniqueId = uniqueId;
    }

    public List<Course> getCommonCourses() {
        return this.commonCourses;
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
}
