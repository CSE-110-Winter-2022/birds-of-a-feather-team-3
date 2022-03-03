package com.example.BirdsOfFeather;

import java.io.Serializable;

public class Course implements Serializable {
    public String quarter;
    public String year;
    public String subject;
    public String classNumber;
    public String classSize;

    public Course (String quarter, String year, String classSize, String subject, String classNumber) {
        this.quarter = quarter;
        this.year = year;
        this.subject = subject;
        this.classNumber = classNumber;
        this.classSize = classSize;
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

    public String getClassSize() {return this.classSize;}

    public String toString(){
        String toReturn = this.quarter + " " + this.year + " " + this.classSize + " " + this.subject + " " + this.classNumber;
        return toReturn;
    }

}
