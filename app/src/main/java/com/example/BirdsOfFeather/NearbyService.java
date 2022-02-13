package com.example.BirdsOfFeather;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NearbyService extends Service {

    ExecutorService executor = Executors.newSingleThreadExecutor();
    private final NearbyBinder binder = new NearbyBinder();
    private List<Person> nearbyClassmates = new ArrayList<Person>();

    //list of user's inputted courses
    private List<Course> myCourses;

    //message that will be broadcasted
    private Message classesMessage;

    //message listener for processing received messages
    private MessageListener classesMessageListener;

    AppDatabase db;

    public NearbyService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //start sharing / receiving
        Toast.makeText(NearbyService.this, "Sharing and receiving started", Toast.LENGTH_SHORT).show();

        db = AppDatabase.singleton(this);
        myCourses = db.classesDao().getAll();


        //construct self Person
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selfName = preferences.getString("first_name", "First_Name");
        String selfPictureLink = preferences.getString("profile_picture_url", "");
        Person self = new Person(selfName, selfPictureLink, myCourses);



        //set outgoing message

        //TO DO: update to be self-data person
        String helloMessage = "Hello!";
        classesMessage = new Message(helloMessage.getBytes(StandardCharsets.UTF_8));

        //Message listener

        MessageListener realMessageListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {

                //Called when new message found


                byte[] serializedPerson = message.getContent();
                Person deserializedPerson = null;
                final Person unchangingDeserializedPerson;
                final ProfileInfo personsProfileInfo;
                    try {
                        deserializedPerson = (Person) convertFromByteArray(serializedPerson);
                    } catch (Exception e) {
                        e.printStackTrace();


                    final String gotName;
                    if (deserializedPerson != null) {
                        unchangingDeserializedPerson = deserializedPerson;
                        personsProfileInfo = SearchClassmates
                                .detectAndReturnSharedClasses(self, deserializedPerson);
                        /*personsProfileInfo = new ProfileInfo(deserializedPerson.getName(),
                                "", deserializedPerson.getClasses());*/
                        gotName = deserializedPerson.getName();
                    } else {
                        unchangingDeserializedPerson = null;
                        personsProfileInfo = null;
                    }

                    if (personsProfileInfo != null) {
                        nearbyClassmates.add(unchangingDeserializedPerson);
                    }
                }
            }

            @Override
            public void onLost(@NonNull Message message) {
                //Called when message no longer detectable nearby
                //should NOT delete message in this case

                    byte[] msgBody = message.getContent();
                    String senderName = null;
                    try {
                        senderName = convertFromByteArray(msgBody).getName();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


            }
        };

        //getting data from mock nearby input

        //fake receiving message
        this.classesMessageListener = new FakedMessageListener(realMessageListener, 3, fakedata);





        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // stop sharing/receiving
        Toast.makeText(NearbyService.this, "Sharing and receiving stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }



    //any methods we will call from activity go here
    public List<Person> getNearbyClassmates(){return nearbyClassmates;}






    class NearbyBinder extends Binder implements IBinder{
        public NearbyService getService(){
            return NearbyService.this;
        }

    }


    public Person convertFromByteArray(byte[] data) throws Exception{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bis);

        Person person = (Person) ois.readObject();
        bis.close();
        ois.close();
        return person;
    }

    public byte[] convertToByteArray(Person person) throws Exception{

    }







}

