package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //If its not their first use, skip inputting name/link and courses
        if (!sharedPreferences.getString("first_name", "").equals("")) {
            Intent intent = new Intent(this, ViewPersonsList.class);
            startActivity(intent);
            finish();
        }
        EditText textView = findViewById(R.id.enter_name_view);
        Autofill autofill = new Autofill();
        String autoFilledName = autofill.getNameFromGoogle();
        if (autoFilledName.equals("")) {
            textView.setText(autoFilledName);
        }
        else {
            Utilities.sendAlert(this,"No google account detected","Warning");
        }
    }

    public void onClickSave(View view) {
        EditText textView = findViewById(R.id.enter_name_view);
        String name = textView.getText().toString();
        if (!name.equals("")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_name", name);
            editor.apply();
            Intent intent = new Intent(this, ImageLinkEntry.class);
            startActivity(intent);
            finish();
        }
        else {
            Utilities.sendAlert(this, "Name can't be blank", "Warning");
        }
    }
}