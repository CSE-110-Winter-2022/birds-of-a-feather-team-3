package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.*;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.example.BirdsOfFeather.database.ClassEntity;
import com.example.BirdsOfFeather.database.SessionEntity;

import java.util.*;

public class InputClasses extends AppCompatActivity {
    private List<Course> localCourses;
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
        Log.i("Mock Type", String.valueOf(usingMock));

        if (usingMock) {
            localCourses = new ArrayList<>();
        }
        else { //retrieve classes from local database
            db = AppDatabase.singleton(this);
            localCourses = db.classesDao().getAll();
            SessionEntity syntheticFavoriteSession = new SessionEntity("Favorites");
            db.sessionDao().insert(syntheticFavoriteSession);
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

        //size spinner
        Spinner sizeSpinner = (Spinner) findViewById(R.id.size_dropdown);
        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this, R.array.SizeSelection,
                android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(sizeAdapter);



        //enter subject textView
        //Restrict input to uppercase
        EditText subjectView = findViewById(R.id.subject_edittext);
        InputFilter[] currentFilters = subjectView.getFilters();
        InputFilter[] updatedFilters = new InputFilter[currentFilters.length + 1];
        for (int i = 0; i < currentFilters.length; i++) {
            updatedFilters[i] = currentFilters[i];
        }
        updatedFilters[currentFilters.length] = new InputFilter.AllCaps();
        subjectView.setFilters(updatedFilters);


        //enter course number textView
        //Restrict input to uppercase
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
        else{ //proceed
            Intent intent = new Intent(this, ViewPersonsList.class);
            startActivity(intent);
            finish();
        }
    }


    public void enterClassOnClick(View view) {
        Spinner quarterInput = (Spinner) findViewById(R.id.quarter_dropdown);
        Spinner yearInput = (Spinner) findViewById(R.id.year_dropdown);
        Spinner sizeInput = (Spinner) findViewById(R.id.size_dropdown);
        EditText subjectInput = (EditText) findViewById(R.id.subject_edittext);
        EditText classNumberInput = (EditText) findViewById(R.id.class_number_edittext);

        String quarter = quarterInput.getSelectedItem().toString() + "";
        String year = yearInput.getSelectedItem().toString() + "";
        String classSize = sizeInput.getSelectedItem().toString() + "";
        String subject = subjectInput.getText().toString() + "";
        String classNumber = classNumberInput.getText().toString() + "";
        // String size = classNumberInput.getText().toString()+"";
        //alert and do not continue if not all entries filled
        if (checkValuesEmpty(quarter, year, classSize, subject, classNumber)) {
            Utilities.sendAlert(this, "Fill in all the inputs", "Warning");
        }
        else {
            Course potentialCourse = new Course(quarter, year, classSize, subject, classNumber);
            //alert and do not continue if entry matches previous entry
            if (checkIsDuplicate(localCourses, potentialCourse)) {
                // Utilities.sendAlert(this, "Duplicate Class Exists", "Warning");
            }
            else {
                //save class to local database
                if (!usingMock) {
                    ClassEntity classEntity = new ClassEntity(quarter, year, classSize, subject, classNumber);
                    long val = db.classesDao().insert(classEntity);
                    System.out.println("insert returned dis: " + val);

                }
                localCourses.add(potentialCourse);
                //alert user of successful course entry
                String courseConfirmed = potentialCourse.toString() + " Added";
                Toast.makeText(this, courseConfirmed, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean checkValuesEmpty(String quarter, String year, String size, String subject, String classNumber) {
        return quarter.equals("") || year.equals("") || size.equals("") || subject.equals("") || classNumber.equals("");
    }

    public boolean checkIsDuplicate(List<Course> compareList, Course potentialCourse) {
        for (int i = 0; i < compareList.size(); i++) {
            if (potentialCourse.toString().equals(compareList.get(i).toString())) {
                return true;
            }
        }
        return false;
    }
}