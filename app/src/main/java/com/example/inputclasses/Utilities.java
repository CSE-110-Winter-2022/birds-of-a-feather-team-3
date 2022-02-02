package com.example.inputclasses;

import android.app.Activity;
import android.app.AlertDialog;

public class Utilities {
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
    }
}
