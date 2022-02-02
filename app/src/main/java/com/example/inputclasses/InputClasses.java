package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.*;

import com.example.inputclasses.database.AppDatabase;
import com.example.inputclasses.database.ClassEntity;

import java.util.*;

public class InputClasses extends AppCompatActivity {
    private List<Course> localCourses;
    //AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //db = AppDatabase.singleton(this);
        localCourses = new ArrayList<>();//db.classesDao().getAll();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_classes);
        //quarter spinner
        Spinner quarterSpinner = (Spinner) findViewById(R.id.quarter_dropdown);
        ArrayAdapter<CharSequence> quarterAdapter = ArrayAdapter.createFromResource(this, R.array.QuarterSelection,
                android.R.layout.simple_spinner_item);
        quarterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterSpinner.setAdapter(quarterAdapter);
        //year spinner
        Spinner yearSpinner = (Spinner) findViewById(R.id.year_dropdown);
        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this, R.array.YearSelection,
                android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);
    }
    public void enterClassOnClick(View view) {
        Spinner quarterInput = (Spinner) findViewById(R.id.quarter_dropdown);
        Spinner yearInput = (Spinner) findViewById(R.id.year_dropdown);
        EditText subjectInput = (EditText) findViewById(R.id.subject_edittext);
        EditText classNumberInput = (EditText) findViewById(R.id.class_number_edittext);
        String quarter = quarterInput.getSelectedItem().toString() + "";
        String year = yearInput.getSelectedItem().toString() + "";
        String subject = subjectInput.getText().toString() + "";
        String classNumber = classNumberInput.getText().toString() + "";

        if (checkValuesEmpty(quarter, year, subject, classNumber)) {
            Utilities.sendAlert(this, "Fill in all the inputs", "Warning");
        }
        else {
            Course potentialCourse = new Course(quarter, year, subject, classNumber);
            if (checkIsDuplicate(potentialCourse)) {
                Utilities.sendAlert(this, "Duplicate Class Exists", "Warning");
            }
            else {
                localCourses.add(potentialCourse);
                //ClassEntity classEntity = new ClassEntity(quarter, year, subject, classNumber);
                //db.classesDao().insert(classEntity);
                //localCourses.add(potentialCourse);
                System.out.println(localCourses);
            }
        }
    }
    public boolean checkValuesEmpty(String quarter, String year, String subject, String classNumber) {
        /*Log.d("quarter", quarter);
        Log.d("year: ", year);
        Log.d("subject: ", subject);
        Log.d("classnum: ", classNumber);
        */
        return quarter.equals("") || year.equals("") || subject.equals("") || classNumber.equals("");
    }
    public boolean checkIsDuplicate(Course potentialCourse) {
        for (int i = 0; i < localCourses.size(); i++) {
            if (potentialCourse.equals(localCourses.get(i))) {
                return true;
            }
        }
        return false;
    }
}