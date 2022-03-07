package com.example.BirdsOfFeather;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Session implements Serializable{
    String sessionName;

    //String timeStamp;

    //String filterOption;
    //String saveType;
    //Calendar calendar;
    //List<ProfileInfo> bofList;

//    Session (String sessionName, String filterOption, String saveType, List<ProfileInfo> bofList, Calendar calendar) {
//        this.calendar = calendar;
//        if (sessionName != null) {
//            this.sessionName = sessionName;
//        }
//        else{
//            final String timeString = new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());
//            String dateFormat = calendar.get(Calendar.DATE) + "/" + calendar.get(Calendar.MONTH) + "/"
//                    + calendar.get(Calendar.YEAR) + " " + timeString;
//            this.sessionName = dateFormat;
//        }
//        Log.i("Session created", sessionName);
//        this.filterOption = filterOption;
//        this.saveType = saveType;
//        this.bofList = bofList;
//    }

    public Session(String sessionName){
        this.sessionName = sessionName;
    }

    public String getName(){return sessionName;}

    public String toString(){return sessionName;}


}
