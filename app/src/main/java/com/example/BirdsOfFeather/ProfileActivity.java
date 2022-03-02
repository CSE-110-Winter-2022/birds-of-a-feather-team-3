package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Displays the image, name, and classes.
public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();

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
            Log.i(TAG, "Downloading image");
            downloadClass.execute(URL);
        }
        //Set the title with the person
        setTitle("Profile");
    }

    public void onWaveClicked(View view) {
        //Button wave = this.findViewById(R.id.wave_button);
        Context context = getApplicationContext();
        Toast waveToast = Toast.makeText(context, "wave sent", Toast.LENGTH_LONG);
        waveToast.show();

    }
}