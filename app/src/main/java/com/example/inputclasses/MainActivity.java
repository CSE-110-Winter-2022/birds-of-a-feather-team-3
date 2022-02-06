package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Intent intent = new Intent(this, InputClasses.class);
//        intent.putExtra("database_type", "actual");

          // InputClasses Activity
//        Intent intent = new Intent(this, InputClasses.class);
//        startActivity(intent);

        // ViewPersonsList Activity
        Intent intent = new Intent(this, ViewPersonsList.class);
        startActivity(intent);
    }
}