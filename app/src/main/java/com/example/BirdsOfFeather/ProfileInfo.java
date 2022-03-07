package com.example.BirdsOfFeather;

import java.util.List;

//Stores information of another user in relation to the app user
public class ProfileInfo {
    List<Course> profileCourses;
    String name;
    String URL;

    public ProfileInfo(String name, String URL, List<Course> profileCourses) {
        this.name = name;
        this.URL = URL;
        this.profileCourses = profileCourses;
    }

    public List<Course> getCommonCourses() {
        return this.profileCourses;
    }
    public String getName() {
        return this.name;
    }
    public String getURL() {
        return this.URL;
    }
}
