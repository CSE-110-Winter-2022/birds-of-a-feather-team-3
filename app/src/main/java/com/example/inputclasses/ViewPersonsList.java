package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.inputclasses.database.AppDatabase;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.material.tabs.TabLayout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPersonsList extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

    //class name for log
    private static final String TAG = ViewPersonsList.class.getSimpleName();

    //message that will be broadcasted
    private Message classesMessage;

    //message listener for processing received messages
    private MessageListener classesMessageListener;

    //list of user's inputted courses
    private List<Course> myCourses;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        List<Person> nearbyPeople = new ArrayList<Person>();

        db = AppDatabase.singleton(this);
        myCourses = db.classesDao().getAll();

        //fakedata
        /*
        Person Rodney = new Person("Rodney", new String[]{"CSE21", "MATH18"});


        Person Lucas = new Person("Lucas", new String[]{"ECE45", "ECE35", "CSE21"});
        Person Grace = new Person("Grace", new String[]{"ECE45", "ECE35", "MATH18"});
        Person Mark = new Person("Mark", new String[]{"CSE21", "MATH18", "WCWP10A"});
        Person Vicki = new Person("Vicki", new String[]{"WCWP10B", "ECON109", "WCWP10A"});

        List<Person> fakedata = new ArrayList<>();
        fakedata.add(Lucas);
        fakedata.add(Grace);
        fakedata.add(Mark);
        fakedata.add(Vicki);
        */


        //construct Person object for self-data
        //convert object to byte array using serialization
        Person self = new Person("Name", myCourses);


        // set message to be outgoing data
        try {
            classesMessage = new Message(convertToByteArray(self));
        } catch (Exception e) {
            e.printStackTrace();
            Utilities.sendAlert(this, "Error serializing course data", "Error");
        }

        classesMessageListener = new MessageListener() {
            @Override
            public void onFound(final Message message) {
                //Called when new message found
                //create person from message
                try {
                    Person msgBody = convertFromByteArray(message.getContent());
                    nearbyPeople.add(msgBody);
                } catch (Exception e) {
                    e.printStackTrace();
                    //throw error
                }
            }

            @Override
            public void onLost(final Message message) {
                //Called when message no longer detectable nearby
                //should NOT delete message in this case
                //String msgBody = new String(message.getContent());
            }
        };



        //persons  = SearchClassmates.search(fakedata,Rodney);
        List<String> classmates = SearchClassmates.search(nearbyPeople, self);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
        setTitle("People with Shared Classes");

        personsRecyclerView = findViewById(R.id.persons_view);

        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);

        personsViewAdapter = new PersonsViewAdapter(classmates);
        personsRecyclerView.setAdapter(personsViewAdapter);

    }




        //subscribe to messages from nearby devices
        //update recyclerview
        private void subscribe(){
            Log.i(TAG, "Subscribing");
            //msgAdapter.clear();
            SubscribeOptions options = new SubscribeOptions.Builder()
                    .setCallback(new SubscribeCallback() {
                        @Override
                        public void onExpired() {
                            super.onExpired();
                            Log.i(TAG, "No longer subscribing");
                            //swap shareButton to stop
                            runOnUiThread(()->findViewById(R.id.shareButton).performClick());
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
                            runOnUiThread(()->findViewById(R.id.shareButton).performClick());

                        }
                    }).build();

            Nearby.getMessagesClient(this).publish(classesMessage, options);
            // onFailureListener and throw error
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

    public byte[] convertToByteArray(Person self) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(self);
        oos.flush();
        byte [] data = bos.toByteArray();
        //bos.close();
        //oos.close();
        return data;
    }

    public Person convertFromByteArray(byte[] data) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Person person = (Person) ois.readObject();
        //bis.close();
        //ois.close();
        return person;
    }


    public void toggleShareButtonOnClick(View view) {
        Button shareButton = (Button) findViewById(R.id.shareButton);
        if (shareButton.getText().equals("Start")) {
            //start sharing and receiving data
            //Utilities.sendAlert(this, "Starting share", "Test");
            System.out.println("Starting share");
            shareButton.setText("Stop");
            subscribe();
            publish();
        } else if (shareButton.getText().equals("Stop")) {
            //stop sharing and receiving data
            //Utilities.sendAlert(this, "Stopping share", "Test");
            System.out.println("Stopping share");
            shareButton.setText("Start");
            unsubscribe();
            unpublish();
        }
    }
}