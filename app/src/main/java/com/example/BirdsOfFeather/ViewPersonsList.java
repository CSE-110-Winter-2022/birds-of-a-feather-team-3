package com.example.BirdsOfFeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPersonsList extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;
    boolean startButtonOn = false;
    //class name for log
    private static final String TAG = ViewPersonsList.class.getSimpleName();
    //message that this user broadcasts to others.
    private Message classesMessage;
    private MessageListener classesMessageListener;
    private List<Course> myCourses;
    private List<Person> fakedSubscribers;
    PersonSerializer personSerializer;
    AppDatabase db;
    List<Person> classmates;

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == 500) {
            byte[] result = intent.getByteArrayExtra("deserialized");
            System.out.println("RESULT: " + result);
            try {
                fakedSubscribers.add(personSerializer.convertFromByteArray(result));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onMockClicked(View v) {
        Intent intent = new Intent(this, MockInputPeople.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "RESUMED");
        System.out.println(myCourses.toString());
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "CREATED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
        personSerializer = new PersonSerializer();

        // create the sort(filter) spinner
        Spinner spinner = (Spinner) findViewById(R.id.filter_dropdown);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.SortSelection,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //empty arraylist to be used by PersonsViewAdapter for storing info
        classmates = new ArrayList<>();
        db = AppDatabase.singleton(this);
        myCourses = db.classesDao().getAll();
        setTitle("BoFs");
        personsRecyclerView = findViewById(R.id.persons_view);
        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);
        personsViewAdapter = new PersonsViewAdapter(classmates);
        personsRecyclerView.setAdapter(personsViewAdapter);

        //construct Person object for self
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selfName = preferences.getString("first_name", "First_Name");
        String selfPictureLink = preferences.getString("profile_picture_url", "");
        Log.i(TAG, selfName + " " + selfPictureLink);
        Person self = new Person(selfName, selfPictureLink, myCourses);
        self.setUniqueId("4b295157-ba31-4f9f-8401-5d85d9cf659a"); //for mocking example
        int i = 1;
        for(Course course: self.getClasses()){
            Log.i(TAG, "Course " + i + ":" + course.toString());
            i++;
        }
        byte[] empty = {};
        classesMessage = new Message(empty);
        try {
            classesMessage = new Message(personSerializer.convertToByteArray(self));
            Log.i(TAG, "Set classesMessage");
        } catch (Exception e) {
            e.printStackTrace();
        }

        MessageListener realMessageListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                Log.i(TAG, "Found a message");
                if (startButtonOn) {
                    byte[] messageByteArray = message.getContent();
                    String messageString = new String(messageByteArray, StandardCharsets.UTF_8);
                    System.out.println(messageString.substring(0, 5));
                    if (messageString.substring(0, 5).equals("WAVE:")  ) { // it was  a wave
                        int indexOfSeparator = messageString.lastIndexOf(":::");
                        String waveSender = messageString.substring(5, indexOfSeparator);
                        String waveRecipient = messageString.substring(indexOfSeparator + 3, messageString.length());
                        System.out.println(waveSender + ", " + waveRecipient);
                        if (waveRecipient.equals(self.getUniqueId())) { // they are waving at you
                            System.out.println("process wave");
                            processWave(waveSender);
                        }
                    }
                    else { // it was a person sharing their classes
                        Person deserializedPerson = null;
                        final Person unchangingDeserializedPerson;
                        final ProfileInfo personsProfileInfo;
                        try {
                            deserializedPerson = (Person) personSerializer.convertFromByteArray(messageByteArray);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        final String gotName;
                        if (deserializedPerson != null) {
                            unchangingDeserializedPerson = deserializedPerson;
                            personsProfileInfo = SearchClassmates
                                    .detectAndReturnSharedClasses(self, deserializedPerson);
                            gotName = deserializedPerson.getName();
                            Log.i(TAG, "Received message from " + gotName);
                        } else {
                            unchangingDeserializedPerson = null;
                            personsProfileInfo = null;
                        }

                        if (personsProfileInfo != null) {
                            //Was pausing scripts, but running on UI thread fixed
                            runOnUiThread(() -> {
                                personsViewAdapter.addPerson(unchangingDeserializedPerson, personsProfileInfo, false);
                            });
                        }
                    }
                }
            }


            @Override
            public void onLost(@NonNull Message message) {
                Log.i(TAG, "message lost");
                //Called when message no longer detectable nearby
                //should NOT delete user from list in this case
                if (startButtonOn) {
                    byte[] msgBody = message.getContent();
                    String messageString = new String(msgBody, StandardCharsets.UTF_8);
                    System.out.println(messageString.substring(0, 5));
                    if (!messageString.substring(0, 5).equals("WAVE:")  ) { // it was  a wave
                        String senderName = "";
                        try {
                            senderName = personSerializer.convertFromByteArray(msgBody).getName();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.i(TAG, "Stopped receiving messages from " + senderName);
                    }
                }
            }
        };

        //Instructor can add a Person to fakedSubscribers via the Mock Nearby Activity
        fakedSubscribers = new ArrayList<>();
        this.classesMessageListener = realMessageListener;//new FakedMessageListener(realMessageListener, 3, fakedSubscribers);
        Button shareButton = (Button)findViewById(R.id.shareButton);

        shareButton.setOnClickListener(v -> {
            if (shareButton.getText().equals("Start")) {
                startButtonOn = true;
                //start sharing and receiving data
                Log.i(TAG, "Starting share");
                shareButton.setText("Stop");
                subscribe();
                publish();
            } else if (shareButton.getText().equals("Stop")) {
                startButtonOn = false;
                //stop sharing and receiving data
                Log.i(TAG, "Stopping share");
                shareButton.setText("Start");
                unsubscribe();
                unpublish();
            }
        });
    }

    //subscribe to messages from nearby devices
    private void subscribe(){
        //msgAdapter.clear();
        SubscribeOptions options = new SubscribeOptions.Builder()
                .setCallback(new SubscribeCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer subscribing");
                    }
                }).build();
        Nearby.getMessagesClient(this).subscribe(classesMessageListener, options);
        Log.i(TAG, Nearby.getMessagesClient(this).toString());
        Log.i(TAG, "Subscribing");
    }

    //publish message to nearby devices
    private void publish(){
        PublishOptions options = new PublishOptions.Builder()
                .setCallback(new PublishCallback() {
                    @Override
                    public void onExpired() {
                        super.onExpired();
                        Log.i(TAG, "No longer publishing");
                        //swap shareButton to stop
                        //runOnUiThread(()->findViewById(R.id.shareButton).performClick());
                    }
                }).build();

        Nearby.getMessagesClient(this).publish(classesMessage, options);
        Log.i(TAG, "Publishing");
    }

    //stop getting messages from nearby devices
    private void unsubscribe(){
        Nearby.getMessagesClient(this).unsubscribe(classesMessageListener);
        Log.i(TAG,"Unsubscribing");
    }

    public void unpublish(){
        Nearby.getMessagesClient(this).unpublish(classesMessage);
        Log.i(TAG, "Unpublishing");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        personsViewAdapter.setSortType(i);
//        String text = Integer.toString(i);
//        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

        personsViewAdapter.setSortType(i);
        if (i == 0) {
            personsViewAdapter.sortByMatches();
        } else if (i == 1) {
            personsViewAdapter.sortByRecent();
        } else {
            personsViewAdapter.sortBySize();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        personsViewAdapter.setSortType(0);
    }

    public void favoritePerson(View view) {

    }

    public void processWave(String personWavingId) { //check current session for a person with this Id.
        Person foundClassmate = null;
        System.out.println(personWavingId);
        for (int i = 0; i < classmates.size(); i++) { //person was in their BoF list
            Person classmate = classmates.get(i);
            System.out.println(i + ": " + classmate.getUniqueId() + ", " + classmate.getUniqueId().equals(personWavingId));
            if (classmate.getUniqueId().equals(personWavingId)) {
                foundClassmate = classmate;
                break;
            }
        }

        if (foundClassmate != null) {
            foundClassmate.setWave(true);
            Log.i(TAG, "Received wave");
            runOnUiThread(() -> {
                        personsViewAdapter.resort();
            });
            Log.i(TAG, "done wave");

        }
    }
}