package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageLinkEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_link_entry);
    }

    public void onSubmitLinkClick(View v) {

        //editor for writing to sharePreferences to store URL
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText linkView = findViewById(R.id.image_link_edit_text);
        boolean validated = true;
        String inputtedLink = linkView.getText().toString();

        //if no URL input, then set profile to default URL
        if (inputtedLink.equals("")) {
            Intent intent = new Intent(this, InputClasses.class);
            intent.putExtra("database_type", "actual");
            startActivity(intent);
            finish();
        }
        else {
            ImageView profilePicture = (ImageView) findViewById(R.id.image_view_in_link_entry);
            //download profile picture
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
                finish();
            } else { //invalid link sends alert
                Utilities.sendAlert(this, "Invalid link", "Warning");
            }
        }
    }


}

//Allows the image to be downloaded in the background,
//then displayed after it is done without pausing everything
class URLDownload extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    Bitmap bitmap;

    public URLDownload(ImageView imageView) {
        this.imageView = imageView;
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
        }
        return decodedBitmap;
    }

    protected void onPostExecute(Bitmap bitmap) {
        this.bitmap = bitmap;
        imageView.setImageBitmap(bitmap);
    }
}