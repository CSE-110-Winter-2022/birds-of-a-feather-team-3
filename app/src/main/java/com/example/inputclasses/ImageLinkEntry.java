package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageLinkEntry extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String link = "https://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0";
        String badlink = "lol";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_link_entry);
        setTitle("Image Link");
        ImageView profilePicture = (ImageView) findViewById(R.id.imageView2);
        URLDownload breh = new URLDownload(profilePicture);
        breh.execute(link);
        profilePicture.requestLayout();
        if (breh.getResult() == null) {
            setTitle("lmao error");
        }
        else {
            setTitle("gg");
        }
        profilePicture.getLayoutParams().height = 200;
        profilePicture.getLayoutParams().width = 200;
    }
    //public void onClick(View v) {
        //startActivity(new Intent(this, ImageLinkEntry.class));
    //}
}

class URLDownload extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    Bitmap result;

    public URLDownload(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    public Bitmap getResult() {
        return this.result;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        this.result = result;
        bmImage.setImageBitmap(result);
    }
}