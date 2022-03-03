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
import android.widget.Button;

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

public class ViewPersonsList extends AppCompatActivity {
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

        //empty arraylist to be used by PersonsViewAdapter for storing info
        List<Person> classmates = new ArrayList<>();

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
        int i = 1;
        for(Course course: self.getClasses()){
            Log.i(TAG, "Course " + i + ":" + course.toString());
            i++;
        }
        byte[] empty = {};
        classesMessage = new Message(empty);
        try {
            classesMessage = new Message(personSerializer.convertToByteArray(self));
        } catch (Exception e) {
            e.printStackTrace();
        }

        MessageListener realMessageListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                if (startButtonOn) {
                    byte[] serializedPerson = message.getContent();
                    Person deserializedPerson = null;
                    final Person unchangingDeserializedPerson;
                    final ProfileInfo personsProfileInfo;
                    try {
                        deserializedPerson = (Person) personSerializer.convertFromByteArray(serializedPerson);
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
                        //Was pausing scripts, but running on main thread fixed
                        runOnUiThread(() -> {
                            personsViewAdapter.addPerson(unchangingDeserializedPerson, personsProfileInfo, false);
                        });
                    }
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                //Called when message no longer detectable nearby
                //should NOT delete user from list in this case
                if (startButtonOn) {
                    byte[] msgBody = message.getContent();
                    String senderName = "";
                    try {
                        senderName = personSerializer.convertFromByteArray(msgBody).getName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, "Stopped receiving messages from " + senderName);
                }
            }
        };

        //Instructor can add a Person to fakedSubscribers via the Mock Nearby Activity
        fakedSubscribers = new ArrayList<>();
        this.classesMessageListener = new FakedMessageListener(realMessageListener, 3, fakedSubscribers);
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

                //prompt to save session
                Utilities.sendAlert(this,"Do you want to save your session?", "Save Session");
            }
        });
    }

    //subscribe to messages from nearby devices
    private void subscribe(){
        Log.i(TAG, "Subscribing");
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
    }

    //publish message to nearby devices
    private void publish(){
        Log.i(TAG, "Publishing");
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
    }

    //stop getting messages from nearby devices
    private void unsubscribe(){
        Log.i(TAG,"Unsubscribing");
        Nearby.getMessagesClient(this).unsubscribe(classesMessageListener);
    }

    public void unpublish(){
        Log.i(TAG, "Unpublishing");
        Nearby.getMessagesClient(this).unpublish(classesMessage);
    }
}