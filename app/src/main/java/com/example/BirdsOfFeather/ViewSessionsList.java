package com.example.BirdsOfFeather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewSessionsList extends AppCompatActivity {

    protected RecyclerView sessionsRecyclerView;
    protected RecyclerView.LayoutManager sessionsLayoutManager;
    protected PersonsViewAdapter sessionsViewAdapter;

    private static final String TAG = ViewSessionsList.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sessions_list);

        setTitle("Previous Sessions");

        //empty arraylist to be used by PersonsViewAdapter for storing info
        List<Person> sessions = new ArrayList<>();

        sessionsRecyclerView = findViewById(R.id.persons_view);
        sessionsLayoutManager = new LinearLayoutManager(this);
        sessionsRecyclerView.setLayoutManager(sessionsLayoutManager);
        //sessionsViewAdapter = new PersonsViewAdapter(sessions);
        //sessionsRecyclerView.setAdapter(sessionsViewAdapter);



    }


}
