package com.example.BirdsOfFeather;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

public class Autofill extends AppCompatActivity {
    public String getNameFromGoogle() {
        AccountManager manager = AccountManager.get(this);
        Account[] accounts = manager.getAccounts();
        if (accounts.length == 0) {
            return "";
        }
        else {
            return accounts[0].name;
        }
    }
}
