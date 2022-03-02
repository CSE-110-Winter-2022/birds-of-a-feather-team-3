package com.example.BirdsOfFeather;

import java.io.Serializable;

public class Course implements Serializable {
    public String quarter;
    public String year;
    public String subject;
    public String classNumber;
    public String size;
    public Course (String quarter, String year, String subject, String classNumber, String size) {
        this.quarter = quarter;
        this.year = year;
        this.subject = subject;
        this.classNumber = classNumber;
        this.size = size;
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
    public String getSize(){return this.size;}
    public String toString(){
        String toReturn = this.quarter + " " + this.year + " " + this.subject + " " + this.classNumber;
        return toReturn;
    }

}
