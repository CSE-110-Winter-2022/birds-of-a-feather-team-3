package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //If its not their first use, skip inputting name/link


        if (!sharedPreferences.getString("first_name", "").equals("")) {
            Intent intent = new Intent(this, ViewPersonsList.class);
            startActivity(intent);
            finish();
        }
        TextView textview = findViewById(R.id.enter_name_view);
        textview.setText(getNameFromGoogle());
    }



    public String getNameFromGoogle() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();
        if (accounts.length == 0) {
            Utilities.sendAlert(this,"Wanring: No account detected","Warning");
            return "";




        }
        else {
            return accounts[0].name;
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