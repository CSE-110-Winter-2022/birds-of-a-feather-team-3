package com.example.BirdsOfFeather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.BirdsOfFeather.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewFavoritesList extends AppCompatActivity {
    protected RecyclerView favoritesRecyclerView;
    protected RecyclerView.LayoutManager favoritesLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

    //class name for log
    private static final String TAG = ViewFavoritesList.class.getSimpleName();

    AppDatabase db;
    List<Person> favorites;
    List<ProfileInfo> favoritesProfileInfos;
    List<Course> myCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "CREATED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorites_list);

        // create the sort(filter) spinner
        Spinner spinner = (Spinner) findViewById(R.id.filter_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SortSelection,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        //empty arraylist to be used by PersonsViewAdapter for storing info
        favorites = new ArrayList<>();
        favoritesProfileInfos = new ArrayList<>();
        db = AppDatabase.singleton(this);
        myCourses = db.classesDao().getAll();


        setTitle("BoFs");
        favoritesRecyclerView = findViewById(R.id.favorites_view);
        favoritesLayoutManager = new LinearLayoutManager(this);
        favoritesRecyclerView.setLayoutManager(favoritesLayoutManager);
        personsViewAdapter = new PersonsViewAdapter(favoritesProfileInfos, true);
        favoritesRecyclerView.setAdapter(personsViewAdapter);

        List<ProfileInfo> retrievedFavorites = db.sessionWithProfilesDao().get(1).getProfiles();
        Log.i(TAG, "Favorite count: " + retrievedFavorites.size());

        for (ProfileInfo profileInfo : db.sessionWithProfilesDao().get(1).getProfiles()) {
            Log.i(TAG, profileInfo.getName() + ":  " + profileInfo.getUniqueId() + " w: " + profileInfo.getIsWaving());
            personsViewAdapter.addPerson(profileInfo, false);
        }


        //String url = "https://i.imgur.com/HzSuJJR.jpeg";

        //create fake favorites list
        /*
        ProfileInfo profile1 = new ProfileInfo("Mark", url, myCourses, "1");
        ProfileInfo profile2 = new ProfileInfo("Also Mark", url, myCourses, "2");
        ProfileInfo profile3 = new ProfileInfo("Definitely not Mark", url, myCourses, "3");
        ProfileInfo profile4 = new ProfileInfo("Mork", url, myCourses, "4");
        favoritesProfileInfos.add(profile1);
        favoritesProfileInfos.add(profile2);
        favoritesProfileInfos.add(profile3);
        favoritesProfileInfos.add(profile4);
        */
        //personsViewAdapter.addPerson(favoriteProfileInfo, false);
    }

    public void backButtonClicked(View view) {
        finish();
    }
}
