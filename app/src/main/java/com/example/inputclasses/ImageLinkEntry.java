package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageLinkEntry extends AppCompatActivity {

    public void onSubmitLinkClick(View v) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //validate();
        EditText linkView = findViewById(R.id.image_link_edit_text);
        boolean validated = true;
        String inputtedLink = linkView.getText().toString();
        if (inputtedLink.equals("")) { //chose to go with default
            Intent intent = new Intent(this, InputClasses.class);
            intent.putExtra("database_type", "actual");
            startActivity(intent);
        }
        else {
            ImageView profilePicture = (ImageView) findViewById(R.id.image_view_in_link_entry);
            URLDownload downloadClass = new URLDownload(profilePicture);
            Bitmap bitmap = null;
            try {
                bitmap = downloadClass.execute(inputtedLink).get();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) { //valid image link
                editor.putString("profile_picture_url", inputtedLink);
                editor.apply();
                Intent intent = new Intent(this, InputClasses.class);
                startActivity(intent);
            } else { //invalid link
                Utilities.sendAlert(this, "Invalid link", "Warning");
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_link_entry);
    }
    //public void onClick(View v) {
    //startActivity(new Intent(this, ImageLinkEntry.class));
    //}
}

//Allows the image to be downloaded in the background,
//then displayed after it is done without pausing everything
class URLDownload extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    Bitmap bitmap;
    boolean error;

    public URLDownload(ImageView imageView) {
        error = false;
        this.imageView = imageView;
    }

    public boolean getError() {
        return error;
    }
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    protected Bitmap doInBackground(String... urls) {
        String firstUrl = urls[0];
        Bitmap decodedBitmap = null;
        try {
            InputStream inputStream = new java.net.URL(firstUrl).openStream();
            decodedBitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            error = true;
        }
        return decodedBitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
        this.bitmap = bitmap;
        imageView.setImageBitmap(bitmap);
    }
}