package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickSave(View view) {
        // TODO!!! name not saved yet. Not sure how to handle at this point!
        TextView textView = findViewById(R.id.name);
        String name = textView.getText().toString();
//        Intent intent = new Intent(this, InputClasses.class);
//        intent.putExtra("database_type", "actual");


        // go to InputClasses Activity
        Intent intent = new Intent(this, InputClasses.class);
        startActivity(intent);
    }
}