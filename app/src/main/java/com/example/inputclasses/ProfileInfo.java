package com.example.inputclasses;

import java.util.List;

public class ProfileInfo {
    List<Course> commonCourses;
    String name;
    String URL;
    public ProfileInfo(String name, String URL, List<Course> commonCourses) {
        this.name = name;
        this.URL = URL;
        this.commonCourses = commonCourses;
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
}
