package com.example.inputclasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

    boolean startButtonOn = false;

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
        List<String> names;
//        // fake data of my classes
//        Person Rodney = new Person("Rodney", new String[]{"CSE21","MATH18"});
//
//        // fake data of people around
//        Person Lucas = new Person("Lucas", new String[]{"ECE45","ECE35","CSE21"});
//        Person Grace = new Person("Grace", new String[]{"ECE45","ECE35","MATH18"});
//        Person Mark = new Person("Mark", new String[]{"CSE21","MATH18","WCWP10A"});
//        Person Vicky = new Person("Vicky", new String[]{"WCWP10B","ECON109","WCWP10A"});
//
//        List<Person> fakeData = new ArrayList<>();
//        fakeData.add(Lucas);
//        fakeData.add(Grace);
//        fakeData.add(Mark);
//        fakeData.add(Vicky);

//        names  = SearchClassmates.search(fakeData,Rodney);

        //list of people received message from
        List<Person> nearbyPeople = new ArrayList<Person>();
        //list of names received message from (for testing
        List<Person> classmates = new ArrayList<>();

        db = AppDatabase.singleton(this);
        myCourses = db.classesDao().getAll();
        //names = new ArrayList<>();
        //names.add("Dummy1");
        //names.add("Dummy2");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
        setTitle("People with Shared Classes");

        personsRecyclerView = findViewById(R.id.persons_view);

        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);

        personsViewAdapter = new PersonsViewAdapter(classmates);
        //personsViewAdapter = new PersonsViewAdapter(names);
        personsRecyclerView.setAdapter(personsViewAdapter);

        //fakedata
        Course course1 = new Course("SP", "2020", "CSE", "110");
        Course course2 = new Course("FA", "2020", "CSE", "100");
        Course course3 = new Course("WI", "2020", "CSE", "101");
        Course course4 = new Course("FA", "2020", "WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));
        List<Course> DupVickiClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));

        /***commonalities
         * Course1: Rodney, Grace, Vicki
         * Course2: Rodney, Grace, Mark
         * Course3: Mark
         * Course4: Vicki, Lucas
         */

        Person Rodney = new Person("Rodney", RodneyClasses);
        Person Lucas = new Person("Lucas", LucasClasses);
        Person Grace = new Person("Grace", GraceClasses);
        Person Mark = new Person("Mark", MarkClasses);
        Person Vicki = new Person("Vicki", VickiClasses);
        Person DupVicki = new Person("Vicki", DupVickiClasses);

        List<Person> fakedata = new ArrayList<>(Arrays.asList(Rodney, Lucas, Grace, Mark, Vicki, DupVicki));
        //construct Person object for self-data
        //convert object to byte array using serialization
        Person self = new Person("Homer", myCourses);

        System.out.println("User input data:");
        System.out.println(self.getName());
        int i = 1;
        for(Course course: self.getClasses()){
            System.out.print("Course " + i + ":");
            i++;
            System.out.println(course.toString());
        }

/*

        // set message to be outgoing data
        try {
            classesMessage = new Message(convertToByteArray(self));
        } catch (Exception e) {
            e.printStackTrace();
            Utilities.sendAlert(this, "Error serializing course data", "Error");
        }

 */
        String helloMessage = "Hello!";
        classesMessage = new Message(helloMessage.getBytes(StandardCharsets.UTF_8));
        MessageListener realMessageListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                System.out.println("Received message!");
                //Called when new message found
                //create person from message
                /*
                try {
                    Person msgBody = convertFromByteArray(message.getContent());
                    nearbyPeople.add(msgBody);
                } catch (Exception e) {
                    e.printStackTrace();
                    //throw error
                }
                 */
                //Log.d("Start button status: ", Boolean.toString(startButtonOn));
                if (startButtonOn) {
                    byte[] serializedPerson = message.getContent();
                    Person deserializedPerson = null;
                    final Person unchangingDeserializedPerson;
                    final ProfileInfo personsProfileInfo;
                    try {
                        deserializedPerson = (Person) convertFromByteArray(serializedPerson);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //String msgBody = new String(message.getContent());
                    final String gotName;
                    if (deserializedPerson != null) {
                        unchangingDeserializedPerson = deserializedPerson;
                        personsProfileInfo = SearchClassmates
                                .detectAndReturnSharedClasses(self, deserializedPerson);
                        /*personsProfileInfo = new ProfileInfo(deserializedPerson.getName(),
                                "", deserializedPerson.getClasses());*/
                        gotName = deserializedPerson.getName();
                        System.out.println("Received message from " + gotName);
                    } else {
                        unchangingDeserializedPerson = null;
                        personsProfileInfo = null;
                    }
                    //classmates.add(msgBody);
                    //int i = 0;
                    //for(String name: classmates){
                       // Log.d("Classmate : ", name);
                        //System.out.println("Classmate name: " + name);
                       // System.out.println(classmates.size());
                    //}
                    if (personsProfileInfo != null) {

                        runOnUiThread(() -> {
                            personsViewAdapter.addPerson(unchangingDeserializedPerson, personsProfileInfo);
                        });
                    }
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                //Called when message no longer detectable nearby
                //should NOT delete message in this case
                if (startButtonOn) {
                    String msgBody = new String(message.getContent());
                    System.out.println("Stopped receiving messages from" + msgBody);
                }
            }
        };

        //fake receiving message
        this.classesMessageListener = new FakedMessageListener(realMessageListener, 3, fakedata);
        Button shareButton = (Button)findViewById(R.id.shareButton);

        shareButton.setOnClickListener(v -> {
            if (shareButton.getText().equals("Start")) {
                startButtonOn = true;
                //start sharing and receiving data
                //Utilities.sendAlert((Activity) getApplicationContext(), "Starting share", "Test");
                System.out.println("Starting share");
                shareButton.setText("Stop");
               // subscribe();
               // publish();
            } else if (shareButton.getText().equals("Stop")) {
                startButtonOn = false;
                //stop sharing and receiving data
                //Utilities.sendAlert(this, "Stopping share", "Test");
                System.out.println("Stopping share");
                shareButton.setText("Start");
               // unsubscribe();
               // unpublish();
            }
        });


        /*
        List<Course> dummyClasses= new ArrayList<>();
        dummyClasses.add(new Course("Fall", "2022","cats", "8008"));

        Person dummyPerson = new Person("Test", dummyClasses);

         */


        //persons  = SearchClassmates.search(fakedata,Rodney);
        //List<String> classmates = SearchClassmates.search(nearbyPeople, self);


        /*
        List<String> classmates = new ArrayList<>();
        for(Person classmate: nearbyPeople){
            classmates.add(classmate.getName());
        }

         */
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
                            //runOnUiThread(()->findViewById(R.id.shareButton).performClick());
                        }
                    }).build();

            //what do you do message client? tell me your secrets
            //secrets: which messagelistener to get the messages from
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

            //had options before as arg but not anymore since i deleted them
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


    public Person convertFromByteArray(byte[] data) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Person person = (Person) ois.readObject();
        //bis.close();
        //ois.close();
        return person;
    }
}