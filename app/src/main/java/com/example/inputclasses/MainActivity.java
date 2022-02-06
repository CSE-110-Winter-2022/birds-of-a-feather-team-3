package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.inputclasses.database.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickInputClasses(View view) {
        // InputClasses Activity
        Intent intent = new Intent(this, InputClasses.class);
        startActivity(intent);
    }

    public void onClickViewList(View view) {
        // ViewPersonsList Activity
        Intent intent = new Intent(this, ViewPersonsList.class);
        startActivity(intent);
    }
}