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
    @ColumnInfo(name = "subject")
    public String subject;
    @ColumnInfo(name = "classNumber")
    public String classNumber;

    public ClassEntity(String quarter, String year, String subject, String classNumber) {
        this.quarter = quarter;
        this.year = year;
        this.subject = subject;
        this.classNumber = classNumber;
    }
}
