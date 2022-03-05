package com.example.BirdsOfFeather;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

//Allows the sending of notifications, typically warnings, to users.
public class Utilities {
    private static final String TAG = Utilities.class.getSimpleName();

    public static void sendAlert(Activity activity, String message, String title) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Understood", (dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);
        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
        Log.i(TAG, "Sent alert");
    }
}
