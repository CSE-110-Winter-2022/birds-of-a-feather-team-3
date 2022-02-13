package com.example.BirdsOfFeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;
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

    //message that will be broadcasted
    private Message classesMessage;

    //message listener for processing received messages
    private MessageListener classesMessageListener;

    //list of user's inputted courses
    private List<Course> myCourses;


    //Service stuff
    private NearbyService nearbyService;
    private boolean isBound;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            NearbyService.NearbyBinder nearbyBinder = (NearbyService.NearbyBinder)iBinder;
            nearbyService = nearbyBinder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;

        }
    };
    //end Service stuff

    AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //list of names received message from
        List<Person> classmates = new ArrayList<>();

        db = AppDatabase.singleton(this);
        myCourses = db.classesDao().getAll();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
        setTitle("People with Shared Classes");

        personsRecyclerView = findViewById(R.id.persons_view);
        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);
        personsViewAdapter = new PersonsViewAdapter(classmates);
        personsRecyclerView.setAdapter(personsViewAdapter);

        //fakedata
        Course course1 = new Course("Spring", "2020", "CSE", "110");
        Course course2 = new Course("Fall", "2020", "CSE", "100");
        Course course3 = new Course("Winter", "2020", "CSE", "101");
        Course course4 = new Course("Fall", "2020", "WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));
        List<Course> DupVickiClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));

        /***commonalities
         * Course1: Rodney, Grace, Vicki, DupVicki
         * Course2: Rodney, Grace, Mark, DupVicki
         * Course3: Mark
         * Course4: Vicki, Lucas
         */
        String img1 = "";

        String birds[] = {
                "https://natureconservancy-h.assetsadobe.com/is/image/content/dam/tnc/nature/en/photos/AmericanGoldfinch_MattWilliams_4000x2200.jpg?crop=0,0,4000,2200&wid=2000&hei=1100&scl=2.0",
                "https://www.kaytee.com/-/media/Images/Kaytee-NA/US/learn-care/ask-the-pet-bird-experts/ways-to-show-parrot-love/PARROT%20png.png",
                "https://images.unsplash.com/photo-1618281377501-88c2328cbb9a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8Z3JlZW4lMjBwYXJyb3R8ZW58MHx8MHx8&w=1000&q=80",
                "https://news.stonybrook.edu/wp-content/uploads/2018/07/Dodo.jpg",
                "https://cdn.download.ams.birds.cornell.edu/api/v1/asset/303800251/1800",
                "https://static.wikia.nocookie.net/dbxfanon/images/c/cc/The_Impostor.png/revision/latest?cb=20201223005217"
        };
        Person Rodney = new Person("Rodney", birds[0], RodneyClasses);
        Person Lucas = new Person("Lucas", birds[1], LucasClasses);
        Person Grace = new Person("Grace", birds[2], GraceClasses);
        Person Mark = new Person("Mark", birds[3], MarkClasses);
        Person Vicki = new Person("Vicki", "", VickiClasses);
        Person DupVicki = new Person("Vicki", birds[5], DupVickiClasses);

        List<Person> fakedata = new ArrayList<>(Arrays.asList(Rodney, Lucas, Grace, Mark, Vicki, DupVicki));


        //construct Person object for self-data
        //convert object to byte array using serialization
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selfName = preferences.getString("first_name", "First_Name");
        String selfPictureLink = preferences.getString("profile_picture_url", "");

        System.out.println(selfName + " " + selfPictureLink);
        Person self = new Person(selfName, selfPictureLink, myCourses);
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
                //System.out.println("Received message!");
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

                    final String gotName;
                    if (deserializedPerson != null) {
                        unchangingDeserializedPerson = deserializedPerson;
                        personsProfileInfo = SearchClassmates
                                .detectAndReturnSharedClasses(self, deserializedPerson);
                        /*personsProfileInfo = new ProfileInfo(deserializedPerson.getName(),
                                "", deserializedPerson.getClasses());*/
                        gotName = deserializedPerson.getName();
                        //System.out.println("Received message from " + gotName);
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
                    byte[] msgBody = message.getContent();
                    String senderName = null;
                    try {
                        senderName = convertFromByteArray(msgBody).getName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //System.out.println("Stopped receiving messages from " + senderName);
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
                Log.i(TAG, "Starting Share");

                Intent intent = new Intent(this, NearbyService.class);
                //startService(intent);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

                shareButton.setText("Stop");
                subscribe();
                publish();
            } else if (shareButton.getText().equals("Stop")) {
                startButtonOn = false;
                //stop sharing and receiving data
                //Utilities.sendAlert(this, "Stopping share", "Test");
                Log.i(TAG, "Stopping share");

                //Intent intent = new Intent(this, NearbyService.class);
                //stopService(intent);
                if(isBound){
                    unbindService(serviceConnection);
                    isBound = false;
                }

                shareButton.setText("Start");
                unsubscribe();
                unpublish();
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
        bis.close();
        ois.close();
        return person;
    }
}