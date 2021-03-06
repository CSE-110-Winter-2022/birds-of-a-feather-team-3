package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.example.BirdsOfFeather.database.SessionEntity;
import com.google.android.gms.common.AccountPicker;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    EditText textView;

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
        else { //first use
            textView = findViewById(R.id.enter_name_view);
            String autoFilledName = this.getNameFromGoogle();
            if (!autoFilledName.equals("")) {
                textView.setText(autoFilledName);
            } else {
                Utilities.sendAlert(this, "No google account detected", "Warning");
                Log.i(TAG, "could not find google account");
            }
        }
    }

    public void onClickSave(View view) {
        EditText textView = findViewById(R.id.enter_name_view);
        String name = textView.getText().toString();
        //save name to sharedPreferences if not empty input
        if (!name.equals("")) {
            Log.i(TAG, "saving non-empty name");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String generatedUUID = UUID.randomUUID().toString();
//            Log.i(TAG, "App user's unique ID:" + generatedUUID);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("first_name", name);
            editor.putString("unique_identifier", generatedUUID);
            editor.apply();
            Intent intent = new Intent(this, ImageLinkEntry.class);
            startActivity(intent);
            finish();
        }
        else {
            Utilities.sendAlert(this, "Name can't be blank", "Warning");
            Log.i(TAG, "empty name");
        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        textView.setText(intent.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
    }*/


    public String getNameFromGoogle() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();

        //Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"},
         //       false, null, null, null, null);
        //startActivityForResult(intent, 10);
        if (accounts.length == 0) {
            return "";
        }
        else {
            return accounts[0].name;
        }
    }
}
