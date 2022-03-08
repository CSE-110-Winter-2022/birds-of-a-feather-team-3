package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockInputPeople extends AppCompatActivity {

    public String name;
    public String profileURL;
    public String uniqueId;
    public List<Course> classes;
    public Person newStudent;
    public HashMap<String, String> sizeMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mock_nearby_message);
    }

    /*
        expected mock input:
        Name,,,
        url,,,
        year,quarter abbrev,subject,number,size
     */


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
            uniqueId = inputDataSplit[0].split(",")[0];
            name = inputDataSplit[1].split(",")[0];
            profileURL = inputDataSplit[2].split(",")[0];
            //each line split at comma
            //take first input only from first and second lines

            // for all remaining entries, convert to courses
            List<String> wavePersonIds = new ArrayList<>();
            for (int i = 3; i < inputDataSplit.length; i++) {
                if (inputDataSplit[i].contains(",,,")) {//this is a wave
                    //4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,
                    wavePersonIds.add(parseWave(inputDataSplit[i]));
                }
                else { // this is a course
                    classes.add(parseCourse(inputDataSplit[i]));
                }
            }

            newStudent = new Person(name, profileURL, classes);
            newStudent.setUniqueId(uniqueId);
            for (String waveId : wavePersonIds) {
                newStudent.addWaveMocks(waveId);
            }
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
    
    public String sizeToClassSize(String size) {
        if (size.equals("Tiny")) {return "Tiny (<40)";}
        else if (size.equals("Small")) {return "Small (40-75)";}
        else if (size.equals("Medium")) {return "Medium (75-150)";}
        else if (size.equals("Large")) {return "Large (150-250)";}
        else if (size.equals("Huge")) {return "Huge (250-400)";}
        else if (size.equals("Gigantic")) {return "Gigantic (400+)";}

    }

    //convert from string to course object, splitting at commas
    public Course parseCourse(String input){
        String[] splitCourse = input.split(",");
        String quarter = quarterCodeToQuarter(splitCourse[1]);
        String year = splitCourse[0];
        String subject = splitCourse[2];
        String number = splitCourse[3];
        String classSize = sizeToClassSize(splitCourse[4]);
//        String size = "temp";
//        return new Course(quarter, year, size, subject, number);
        return new Course(quarter, year, classSize, subject, number);
    }

    public String parseWave(String input) {
        //4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,
        return input.split(",")[0];
    }
}
