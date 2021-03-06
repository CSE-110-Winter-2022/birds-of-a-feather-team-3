package com.example.BirdsOfFeather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.example.BirdsOfFeather.database.ProfileEntity;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;

import java.nio.charset.StandardCharsets;
import java.util.List;

//Displays the image, name, and classes.
public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = ProfileActivity.class.getSimpleName();
    private Message waveMessage;
    private String name;
    private String courses;
    private String URL;
    private String uniqueId;
    private String selfId;
    private long profileId;
    private boolean isFavorited;
    private boolean isFavoritedSession;
    private boolean wavedTo;
    ImageButton button;
    AppDatabase db;
    Session favoriteSession;
    List<ProfileInfo> currentFavoritedProfiles;
    ProfileEntity profileEntity;
    ImageView waveIcon;
    ImageView profileImageView;
    private MessagesClientLogger LoggedNearbyClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        button = findViewById(R.id.favorite_button_profile);
        name = intent.getStringExtra("name");
        courses = intent.getStringExtra("courses");
        URL = intent.getStringExtra("URL");
        uniqueId = intent.getStringExtra("uniqueId");
        selfId = intent.getStringExtra("selfId");
        isFavorited = intent.getBooleanExtra("favorited", false);
        profileId = intent.getLongExtra("profileId", 0);
        isFavoritedSession = intent.getBooleanExtra("isFavoritedSession", false);
        wavedTo = false;
        if (!intent.getBooleanExtra("testingMode", true)) {
            db = AppDatabase.singleton(this);
            favoriteSession = db.sessionDao().getSession(1);
            LoggedNearbyClient = new MessagesClientLogger(Nearby.getMessagesClient(this));
            favoriteSession.id = 1;
            currentFavoritedProfiles = db.sessionWithProfilesDao().get(favoriteSession.id).getProfiles();

            //public ProfileEntity(String profileName, String profileURL, long profileSessionId,
            //                         List<Course> profileCourses, String uniqueId, boolean isWaving){
            //profileEntity = new ProfileEntity(name, URL, 1, courses);
            ProfileInfo infoToCopy = db.profilesDao().get(profileId);

            profileEntity = new ProfileEntity(infoToCopy.getName(), infoToCopy.getURL(), 1,
                    infoToCopy.getCommonCourses(), infoToCopy.getUniqueId(), infoToCopy.getIsWaving());

            if (isFavorited) {
                button.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                button.setImageResource(android.R.drawable.btn_star_big_off);
            }
            String waveString = "WAVE:" + selfId + ":::" + uniqueId;
            waveMessage = new Message(waveString.getBytes(StandardCharsets.UTF_8));
            TextView nameTextView;
            TextView commonCoursesTextView;
            profileImageView = findViewById(R.id.profile_picture_view);
            nameTextView = findViewById(R.id.profile_name_view);
            commonCoursesTextView = findViewById(R.id.profile_common_classes);
            nameTextView.setText(name);
            commonCoursesTextView.setText(courses);

            if (!URL.equals("")) {
                URLDownload downloadClass = new URLDownload(profileImageView);
                Log.i(TAG, "Downloading image");
                downloadClass.execute(URL);
            }
            //Set the title with the person
            setTitle("Profile");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (favoriteSession != null) {
            unpublish();
        }
    }

    public void favoritePerson(View view) {
        isFavorited = !isFavorited;
        if (isFavorited) {
            button.setImageResource(android.R.drawable.btn_star_big_on);
            db.profilesDao().insert(profileEntity);
        }
        else {
            button.setImageResource(android.R.drawable.btn_star_big_off);
            //ProfileEntity entityToDelete = db.profilesDao().getEntity(profileId);
            ProfileEntity entityToDelete = db.profilesDao().searchFavorite(uniqueId, 1);
            db.profilesDao().delete(entityToDelete);
        }
    }

    //publish message to nearby devices
    private void publish() {
        PublishOptions options = new PublishOptions.Builder()
                .setCallback(new PublishCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        //swap shareButton to stop
                        //runOnUiThread(()->findViewById(R.id.shareButton).performClick());
                    }
                }).build();

        LoggedNearbyClient.publish(waveMessage, options);
    }

    public void unpublish(){
        LoggedNearbyClient.unpublish(waveMessage);
    }

    public void sendWave(View view) {

        //publish();
        if (favoriteSession != null) { //for testing, if its null, means its a test
            if (isFavoritedSession) {
                return;
            }
            if (wavedTo) {
                return;
            }

            //fill wave icon
            waveIcon = (ImageView) findViewById(R.id.wave_imageView);
            waveIcon.setImageResource(R.mipmap.filled_wave);
            publish();
            wavedTo = true;
        }
        Toast waveToast = Toast.makeText(this, "Wave sent", Toast.LENGTH_SHORT);
        waveToast.show();
    }
}