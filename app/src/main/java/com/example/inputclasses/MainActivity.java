package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //If its not their first use, skip inputting name/link TODO temperarily comment the code out for developing
//        if (!sharedPreferences.getString("first_name", "").equals("")) {
//            Intent intent = new Intent(this, ViewPersonsList.class);
//            startActivity(intent);
//        }

        TextView textview = findViewById(R.id.name);
        textview.setText(getNameFromGoogle());
    }

    public String getNameFromGoogle() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();
        if (accounts.length == 0) {
            return "No account detected";
        }
        else {
            return accounts[0].name;
        }
    }

    public void onClickSave(View view) {
        TextView textView = findViewById(R.id.name);
        String name = textView.getText().toString();
        if (!name.equals("")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_name", name);
            editor.apply();
            // go to InputClasses Activity
            Intent intent = new Intent(this, ImageLinkEntry.class);
            startActivity(intent);
        }
        else {
            Utilities.sendAlert(this, "Name can't be blank", "Warning");
        }
    }
}