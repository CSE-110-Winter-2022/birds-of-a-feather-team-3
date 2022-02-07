package com.example.inputclasses;

import java.io.Serializable;

public class Course implements Serializable {
    public String quarter;
    public String year;
    public String subject;
    public String classNumber;
    public Course (String quarter, String year, String subject, String classNumber) {
        this.quarter = quarter;
        this.year = year;
        this.subject = subject;
        this.classNumber = classNumber;
    }
    public String getQuarter() {
        return this.quarter;
    }
    public String getYear() {
        return this.year;
    }
    public String getSubject() {
        return this.subject;
    }
    public String getClassNumber() {
        return this.classNumber;
    }
    public boolean equals(Course otherCourse) {
        return this.quarter.equals(otherCourse.getQuarter()) &&
                this.year.equals(otherCourse.getYear()) &&
                this.subject.equals(otherCourse.getSubject()) &&
                this.classNumber.equals(otherCourse.getClassNumber());
    }
}
