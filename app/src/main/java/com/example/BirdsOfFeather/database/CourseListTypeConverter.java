package com.example.BirdsOfFeather.database;

import androidx.room.TypeConverter;

import com.example.BirdsOfFeather.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class CourseListTypeConverter {

    @TypeConverter
    public static List<Course> stringToCourseList(String dataString) {
        if (dataString == null) {
            System.out.println("ERROR EMPTY DATA STRING OH NOOOOOOOOOOO :(");
            return Collections.emptyList();

        }

        Type listType = new TypeToken<List<Course>>() {}.getType();

        return new Gson().fromJson(dataString, listType);
    }

    @TypeConverter
    public static String courseListToString(List<Course> list) {
        return new Gson().toJson(list);
    }

}


