package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.*;

import com.example.inputclasses.database.AppDatabase;
import com.example.inputclasses.database.ClassEntity;

import java.util.*;

public class InputClasses extends AppCompatActivity {
    private List<Course> localCourses;
    //private Course[] classes;
    AppDatabase db;
    boolean usingMock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent extras = getIntent();

        usingMock = true; //For testing purposes
        if (extras != null) { //Use the real database if called by MainActivity
            String databaseType = extras.getStringExtra("database_type");
            if (databaseType != null) {
                usingMock = !databaseType.equals("actual");
            }
            else {
                usingMock = true;
            }
        }
        Log.d("Mock Type", String.valueOf(usingMock));

        if (usingMock) {
            localCourses = new ArrayList<>();
        }
        else {
            db = AppDatabase.singleton(this);
            localCourses = db.classesDao().getAll();
        }

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

        EditText subjectView = findViewById(R.id.subject_edittext);
        InputFilter[] currentFilters = subjectView.getFilters();
        InputFilter[] updatedFilters = new InputFilter[currentFilters.length + 1];
        for (int i = 0; i < currentFilters.length; i++) {
            updatedFilters[i] = currentFilters[i];
        }
        updatedFilters[currentFilters.length] = new InputFilter.AllCaps();
        subjectView.setFilters(updatedFilters);

        EditText numberView = findViewById(R.id.class_number_edittext);
        InputFilter[] currentFiltersNumView = numberView.getFilters();
        InputFilter[] updatedFiltersNumView = new InputFilter[currentFiltersNumView.length + 1];
        for (int i = 0; i < currentFiltersNumView.length; i++) {
            updatedFiltersNumView[i] = currentFiltersNumView[i];
        }
        updatedFiltersNumView[currentFiltersNumView.length] = new InputFilter.AllCaps();
        numberView.setFilters(updatedFiltersNumView);

    }
    public void doneInputOnClick(View view){
        if(localCourses.isEmpty()){
            //do not proceed and send warning if no classes entered
            Utilities.sendAlert(this,"Please enter at least one class", "Warning");
        }
        else{
            Intent intent = new Intent(this, ViewPersonsList.class);
            //intent.putExtra("COURSES_ARRAY", classes);
            startActivity(intent);
        }
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
            if (checkIsDuplicate(localCourses, potentialCourse)) {
                Utilities.sendAlert(this, "Duplicate Class Exists", "Warning");
            }
            else {
                if (!usingMock) {
                    ClassEntity classEntity = new ClassEntity(quarter, year, subject, classNumber);
                    db.classesDao().insert(classEntity);
                }
                localCourses.add(potentialCourse);
                //System.out.println(localCourses);
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
    public boolean checkIsDuplicate(List<Course> compareList, Course potentialCourse) {
        for (int i = 0; i < compareList.size(); i++) {
            if (potentialCourse.equals(compareList.get(i))) {
                return true;
            }
        }
        return false;
    }
}