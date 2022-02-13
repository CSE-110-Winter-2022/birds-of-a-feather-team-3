package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String courses = intent.getStringExtra("courses");
        String URL = intent.getStringExtra("URL");

        ImageView profileImageView;
        TextView nameTextView;
        TextView commonCoursesTextView;

        profileImageView = findViewById(R.id.profile_picture_view);
        nameTextView = findViewById(R.id.profile_name_view);
        commonCoursesTextView = findViewById(R.id.profile_common_classes);

        nameTextView.setText(name);
        commonCoursesTextView.setText(courses);
        if (!URL.equals("")) {
            URLDownload downloadClass = new URLDownload(profileImageView);
            //System.out.println(URL);
            downloadClass.execute(URL);
        }
        //Set the title with the person
        setTitle("Profile");
    }
}