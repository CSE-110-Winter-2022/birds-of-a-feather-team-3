package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MockInputPeople extends AppCompatActivity {

    public String name;
    public String profileURL;
    public List<Course> classes;
    public Person newStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mock_nearby_message);
    }


    public void onMockEnterClicked(View v) throws Exception {
        classes = new ArrayList<Course>();

        TextView inputTextView = (TextView)findViewById(R.id.mockDataTextView);
        String inputData = inputTextView.getText().toString();
        if(inputData == null || inputData == ""){
            //no data inputted
            Utilities.sendAlert(this, "No Nearby Students", "No Data Entered");
        }
        else{
            //split text in textbox at newline character into string array
            String[] inputDataSplit = inputData.split(System.lineSeparator());
            name = inputDataSplit[0].split(",")[0];
            profileURL = inputDataSplit[1].split(",")[0];
            //each line split at comma
            //take first input only from first and second lines
            name = inputDataSplit[0].split(",")[0];
            profileURL = inputDataSplit[1].split(",")[0];

            // for all remaining entries, convert to courses
            for (int i = 2; i < inputDataSplit.length; i++) {
                classes.add(parseCourse(inputDataSplit[i]));
            }

            newStudent = new Person(name, profileURL, classes);
            Log.i("New Mock Student", newStudent.toString());

            //send new person object with intent to ViewPersonList
            Intent intent = new Intent();
            PersonSerializer personSerializer = new PersonSerializer();
            intent.putExtra("deserialized", personSerializer.convertToByteArray(newStudent));
            setResult(500, intent);
        }
        finish();

    }

    //convert from quarter code string to un-abbreviated  quarter
    public String quarterCodeToQuarter(String code){
        if(code.equals("FA")){return "Fall";}
        else if(code.equals("WI")){return "Winter";}
        else if (code.equals("SP")){return "Spring";}
        else if (code.equals("SS1")){return "Summer Session I";}
        else if(code.equals("SS2")){return "Summer Session II";}
        else if(code.equals("SSS")){return "Special Summer Session";}

        //return empty string if not match defined quarters
        return "";
    }

    //convert from string to course object, splitting at commas
    public Course parseCourse(String input){
        String[] splitCourse = input.split(",");
        String quarter = quarterCodeToQuarter(splitCourse[1]);
        String year = splitCourse[0];
        String classSize = splitCourse[4];
        String subject = splitCourse[2];
        String number = splitCourse[3];

        return new Course(quarter, year, classSize, subject, number);
    }
}