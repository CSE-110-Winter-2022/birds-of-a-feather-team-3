package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MockInputPeople extends AppCompatActivity {

    public String name;
    public String profileURL;
    public List<Course> classes;
    public Course newCourse;
    public Person newStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_mock_nearby_message);
    }

    public void onEnterClicked(View v) throws IOException {

        classes = new ArrayList<Course>();

        TextView inputTextView = (TextView)findViewById(R.id.mockDataTextView);
        String inputData = inputTextView.getText().toString();

        if(inputData == null || inputData.equals("")){
            Utilities.sendAlert(this, "No mock data entered", "No Data Entered");
        }
        else{
            //split text in textbox at newline character into string array

            //String[] inputDataSplit = inputData.split("\n\n");
            String[] inputDataSplit = inputData.split(System.lineSeparator());

            //each line split at comma

            //take first input only from first and second lines
            name = inputDataSplit[0].split(",")[0];
            profileURL = inputDataSplit[1].split(",")[0];

            // for all remaining entries, convert to courses
            for (int i = 2; i < inputDataSplit.length; i++) {
                String courseToSplit = inputDataSplit[i];
                String[] splitCourse = courseToSplit.split(",");

                String quarter = quarterCodeToQuarter(splitCourse[1]);
                String year = splitCourse[0];
                String subject = splitCourse[2];
                String number = splitCourse[3];

                newCourse = new Course(quarter, year, subject, number);

                classes.add(newCourse);

            }
            newStudent = new Person(name,profileURL,classes);
            Intent intent = new Intent(this, ViewPersonsList.class);
            //intent.putExtra("studenName", name);
            //intent.putExtra("studentURL", profileURL);

            byte[] serializedPerson = null;
            try {
                serializedPerson = convertToByteArray(newStudent);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(serializedPerson != null){
                intent.putExtra("person", serializedPerson);
            }

            startActivity(intent);

        }


    }


    public String quarterCodeToQuarter(String code){
        String[] quarters = this.getResources().getStringArray(R.array.QuarterSelection);

        if(code.equals("FA")){return quarters[0];}
        else if(code.equals("WI")){return quarters[1];}
        else if (code.equals("SP")){return quarters[2];}
        else if (code.equals("SS1")){return quarters[3];}
        else if(code.equals("SS2")){return quarters[4];}
        else if(code.equals("SSS")){return quarters[5];}

        return "";
    }

    public byte[] convertToByteArray(Person self) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(self);
        oos.flush();
        byte [] data = bos.toByteArray();
        bos.close();
        oos.close();
        return data;
    }


}