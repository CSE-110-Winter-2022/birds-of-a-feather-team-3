package com.example.BirdsOfFeather;

import android.util.Log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Session implements Serializable{
    public String sessionName;
    public long id;

    public Session(String sessionName, long id){
        this.sessionName = sessionName;
        this.id = id;
    }

    public String getName(){return sessionName;}

    public String toString(){return sessionName;}


}
