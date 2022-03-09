package com.example.BirdsOfFeather.database;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName = "classes")
public class ClassEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int classIndex;

    @ColumnInfo(name = "quarter")
    public String quarter;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "classSize")
    public String classSize;

    @ColumnInfo(name = "subject")
    public String subject;

    @ColumnInfo(name = "classNumber")
    public String classNumber;

    public ClassEntity(String quarter, String year, String classSize, String subject, String classNumber) {
        System.out.println("it id is class btw : " + classIndex);
        this.quarter = quarter;
        this.year = year;
        this.classSize = classSize;
        this.subject = subject;
        this.classNumber = classNumber;
    }
}
