package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //If its not their first use, skip inputting name/link
        /*
        if (!sharedPreferences.getString("first_name", "").equals("")) {
            Intent intent = new Intent(this, ViewPersonsList.class);
            startActivity(intent);
        }

         */

          // InputClasses Activity
        //Intent intent = new Intent(this, ImageLinkEntry.class);
        //startActivity(intent);

        // ViewPersonsList Activity
        //Intent intent = new Intent(this, ViewPersonsList.class);
        //startActivity(intent);
    }

    public void onClickSave(View view) {
        TextView textView = findViewById(R.id.name);
        String name = textView.getText().toString();
        if (!name.equals("")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_name", name);
            editor.apply();
//        Intent intent = new Intent(this, InputClasses.class);
//        intent.putExtra("database_type", "actual");
            // go to InputClasses Activity
            Intent intent = new Intent(this, ImageLinkEntry.class);
            startActivity(intent);
        }
        else {
            Utilities.sendAlert(this, "Name can't be blank", "Warning");
        }
        //Intent intent = new Intent(this, InputClasses.class);
    }
}