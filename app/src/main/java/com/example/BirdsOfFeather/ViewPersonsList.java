package com.example.BirdsOfFeather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.example.BirdsOfFeather.database.AppDatabase;
import com.example.BirdsOfFeather.database.ProfileEntity;
import com.example.BirdsOfFeather.database.SessionEntity;
import com.example.BirdsOfFeather.database.SessionWithProfiles;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.PublishCallback;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.SubscribeCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    List<ProfileInfo> classmateProfileInfos;

    //session data
    Session currentSession;
    Session favoriteSession;
    long currSessionId;
    //ProfileInfo newProfile;

    //save popup stuff
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText customName_editText;
    private Button saveSession_button;
    private Spinner classes_spinner;
    String sessionName;

    //start popup stuff
    private Spinner sessionsSpinner;
    private Button continueButton;
    private Button newButton;
    private Button renameButton;

    //rename popup stuff
    private AlertDialog.Builder renameDialogBuilder;
    private AlertDialog renameDialog;
    private Button cancelButton;
    private Button saveButton;
    private Spinner classesSpinner;
    private EditText newNameEditText;


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
        reassignFavorites();
        super.onResume();
    }

    public void reassignFavorites() {
        List<ProfileInfo> retrievedFavorites = db.sessionWithProfilesDao().get(1).getProfiles();
        for (ProfileInfo classmateProfileInfo : classmateProfileInfos) {
            classmateProfileInfo.setFavorited(false);
        }
        for (ProfileInfo classmateProfileInfo : classmateProfileInfos) {
            for (ProfileInfo retrievedFavorite : retrievedFavorites) {
                if (classmateProfileInfo.getUniqueId().equals(retrievedFavorite.getUniqueId())) {
                    classmateProfileInfo.setFavorited(true);
                }
            }
        }

        personsViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "CREATED");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
        db = AppDatabase.singleton(this);

        //setting up favorite synthetic session for future use
        favoriteSession = db.sessionDao().getSession(1);
        favoriteSession.id = 1;

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
        classmateProfileInfos = new ArrayList<>();
        myCourses = db.classesDao().getAll();
        setTitle("BoFs");
        personsRecyclerView = findViewById(R.id.favorites_view);
        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);
        personsViewAdapter = new PersonsViewAdapter(classmateProfileInfos);
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

                            //TODO add personsProfileInfo to database

                            runOnUiThread(() -> {
                                boolean result = personsViewAdapter.addPerson(personsProfileInfo, false);//unchangingDeserializedPerson, personsProfileInfo, false);
                                if (result) {//was added properly
                                    ProfileEntity newProfile = new ProfileEntity(personsProfileInfo.getName(), personsProfileInfo.getURL(), currentSession.id, myCourses, personsProfileInfo.getUniqueId(), personsProfileInfo.getIsWaving());
                                    long autogeneratedProfileId = db.profilesDao().insert(newProfile);
                                    personsProfileInfo.setProfileId(autogeneratedProfileId);
                                }
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
        this.classesMessageListener = new FakedMessageListener(realMessageListener, 3, fakedSubscribers);
        Button shareButton = (Button)findViewById(R.id.shareButton);

        shareButton.setOnClickListener(v -> {
            if (shareButton.getText().equals("Start")) {

                System.out.println("Starting session dialog popup");

                if(db.sessionWithProfilesDao().count()==0){
                    //start new session
                    startNewSession();
                }
                else{
                    startSessionDialog();
                }


                shareButton.setText("Stop");

            } else if (shareButton.getText().equals("Stop")) {
                startButtonOn = false;
                //stop sharing and receiving data
                Log.i(TAG, "Stopping share");
                shareButton.setText("Start");
                unsubscribe();
                unpublish();

                //prompt to save session
                saveSessionDialog();

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


    public void saveSessionDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View saveSessionPopupView = getLayoutInflater().inflate(R.layout.popup_save_session_prompt, null);
        customName_editText = (EditText) saveSessionPopupView.findViewById(R.id.new_name_edittext);
        saveSession_button = (Button) saveSessionPopupView.findViewById(R.id.save_button);
        classes_spinner = (Spinner) saveSessionPopupView.findViewById(R.id.classes_spinner);

        loadSpinnerData(classes_spinner);

        dialogBuilder.setView(saveSessionPopupView);
        dialog = dialogBuilder.create();
        dialog.show();

        saveSession_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define click behaviour

                //no need to check for empty input as cannot reach this screen unless has at least one class -> default session name

                //if there is any text in custom name box
                if(customName_editText.getText().toString() != null && !customName_editText.getText().toString().equals("")){
                    sessionName = customName_editText.getText().toString();
                }
                else{
                    sessionName = classes_spinner.getSelectedItem().toString();
                }

                //update name of autosaved session to user input session name
                db.sessionDao().update(sessionName, currentSession.id);


                //test print all sessions
                List<Session> mySessions = db.sessionDao().getAll();
                System.out.println("Sessions: ");
                for(Session s: mySessions){
                    System.out.println(s.sessionName);
                }

                System.out.println("Testing linked database now:");

                List<SessionWithProfiles> sessionProfiles = db.sessionWithProfilesDao().getAll();

                for(SessionWithProfiles swp : sessionProfiles){
                    System.out.println("Session name: " + swp.getName() + " with id=" + swp.getId());
                    List<ProfileInfo> profiles = swp.getProfiles();
                    System.out.println("Number of profiles in "+swp.getName()+":"+profiles.size());
                    for(ProfileInfo p: profiles){
                        System.out.println(p.name + " " + p.URL);
                        for(Course c: p.getCommonCourses()){
                            System.out.println(c.toString());
                        }
                    }
                }

                dialog.dismiss();
            }
        });

    }

    public void loadSpinnerData(Spinner spinner){
        List<String> courseNameList = new ArrayList<String>();
        for(Course c: myCourses){
            courseNameList.add(c.getSubject() + " " + c.getClassNumber());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, courseNameList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    public void loadSessionData(Spinner spinner, AppDatabase db){
        List<String> sessionNameList = new ArrayList<String>();
        List<Session> sessionList = db.sessionDao().getAll();

        for(Session s: sessionList){
            sessionNameList.add(s.sessionName);
        }
        sessionNameList.remove(0);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, sessionNameList);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    public void startSessionDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View startSessionPopupView = getLayoutInflater().inflate(R.layout.popup_start_session_prompt, null);

        sessionsSpinner = (Spinner) startSessionPopupView.findViewById(R.id.classes_spinner);
        continueButton = (Button) startSessionPopupView.findViewById(R.id.save_button);
        newButton = (Button) startSessionPopupView.findViewById(R.id.new_session_button);
        renameButton = (Button) startSessionPopupView.findViewById(R.id.rename_button);


        loadSessionData(sessionsSpinner, db);

        dialogBuilder.setView(startSessionPopupView);
        dialog = dialogBuilder.create();
        dialog.show();


        renameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define click behaviour
                //assuming that Dao getAll() method preserves order of inserted sessions
                //index of spinner starts at 0 while index of database starts at 1, thus add 1
                renameSessionDialog(sessionsSpinner.getSelectedItemPosition() + 2, sessionsSpinner);
            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define click behaviour

                currentSession = db.sessionDao().getSession(sessionsSpinner.getSelectedItemPosition() + 2);
                personsViewAdapter.clearAdapter();
                List<ProfileInfo> loadedProfiles = db.sessionWithProfilesDao().get(currentSession.id).getProfiles();
                //personsProfileInfo = SearchClassmates
                //        .detectAndReturnSharedClasses(self, deserializedPerson);
                for (ProfileInfo newProfile : loadedProfiles) {
                    Log.i(TAG, newProfile.getName() + ", " + newProfile.getURL() + " wave: " + newProfile.getIsWaving());
                    personsViewAdapter.addPerson(newProfile, false);
                }
                System.out.println("Finishing session dialog popup");
                //start sharing and receiving data
                Log.i(TAG, "Starting share");
                startButtonOn = true;
                subscribe();
                publish();
                dialog.dismiss();
            }
        });

        newButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //define click behaviour

                startNewSession();
                dialog.dismiss();
            }
        });

    }

    public void startNewSession(){
        SessionEntity newSessionEntity = new SessionEntity(getCurrDayTime());
        long generatedId =  db.sessionDao().insert(newSessionEntity);
        currentSession = db.sessionDao().getSession(generatedId);
        currentSession.id = generatedId;
        currSessionId = currentSession.id;//db.sessionWithProfilesDao().count() + 1;
        //start sharing and receiving data
        Log.i(TAG, "Starting share");
        startButtonOn = true;
        subscribe();
        publish();

    }

    public String getCurrDayTime(){
        //format is "1/16/22 5:10PM"
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy hh:mm a");
        String currentDateandTime = sdf.format(new Date());

        System.out.println("Default Session name is: " + currentDateandTime);

        return currentDateandTime;
    }

    public void renameSessionDialog(int selectedSessionId, Spinner spinner){
        //set up popup dialog
        renameDialogBuilder = new AlertDialog.Builder(this);
        final View renameSessionPopupView = getLayoutInflater().inflate(R.layout.popup_rename_session_prompt, null);
        renameDialogBuilder.setView(renameSessionPopupView);
        renameDialog = renameDialogBuilder.create();
        renameDialog.show();

        //views
        classesSpinner = renameSessionPopupView.findViewById(R.id.classes_spinner);
        newNameEditText = renameSessionPopupView.findViewById(R.id.new_name_edittext);
        cancelButton = renameSessionPopupView.findViewById(R.id.cancel_button);
        saveButton = renameSessionPopupView.findViewById(R.id.save_button);


        newNameEditText.setHint(db.sessionDao().getSession(selectedSessionId).sessionName);


        loadSpinnerData(classesSpinner);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define click behaviour
                renameDialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //define click behaviour

                String newName;

                if(newNameEditText.getText().toString().equals(null) || newNameEditText.getText().toString().equals("")){
                    newName = classesSpinner.getSelectedItem().toString();
                }
                else{
                    newName = newNameEditText.getText().toString();
                }

                db.sessionDao().update(newName, selectedSessionId);

                //update session spinner to show new named sessions
                loadSessionData(spinner, db);

                renameDialog.dismiss();
            }
        });
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
        ProfileInfo foundClassmate = null;
        System.out.println(personWavingId);
        for (int i = 0; i < classmateProfileInfos.size(); i++) { //person was in their BoF list
            ProfileInfo classmate = classmateProfileInfos.get(i);
            System.out.println(i + ": " + classmate.getUniqueId() + ", " + classmate.getUniqueId().equals(personWavingId));
            if (classmate.getUniqueId().equals(personWavingId)) {
                foundClassmate = classmate;
                break;
            }
        }

        if (foundClassmate != null) {
            foundClassmate.setWaving(true);//need to update in database
            db.profilesDao().updateWave(true, foundClassmate.getProfileId());
            Log.i(TAG, "Received wave");
            runOnUiThread(() -> {
                        personsViewAdapter.resort();
            });
            Log.i(TAG, "done wave");
        }
    }

    public void onSessionClicked(View view) {
        Intent intent = new Intent(this, ViewFavoritesList.class);
        startActivity(intent);
    }
}
