package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;
    final BluetoothAdapter btAdapter = BluetoothAdapter.
            getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        //If its not their first use, skip inputting name/link
        EditText textView = findViewById(R.id.enter_name_view);
        textView.setText(getNameFromGoogle());

 /*bluetooth reminder, TODO does not work unless forcefully stop app
        if (!btAdapter.isEnabled()) {
            Context context = getApplicationContext();
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            Toast toast = Toast.makeText(context, "Please Turn on Bluetooth, Toast.LENGTH_LONG);
            toast.show();
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        if (!btAdapter.isDiscovering()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(enableBtIntent, REQUEST_DISCOVERABLE_BT);
        }
*/


//        if (!sharedPreferences.getString("first_name", "").equals("")) {
//            Intent intent = new Intent(this, ViewPersonsList.class);
//            startActivity(intent);
//            finish();
//        }



          // InputClasses Activity
        //Intent intent = new Intent(this, ImageLinkEntry.class);
        //startActivity(intent);

        // ViewPersonsList Activity
        //Intent intent = new Intent(this, ViewPersonsList.class);
        //startActivity(intent);
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
        //Intent intent = new Intent(this, InputClasses.class);
    }

    public String getNameFromGoogle() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();
        if (accounts.length == 0) {
            Utilities.sendAlert(this,"Warning: No account detected","Warning");
            return "";
        }
        else {
            return accounts[0].name;
        }
    }
}