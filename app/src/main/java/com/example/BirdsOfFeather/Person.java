package com.example.BirdsOfFeather;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Person implements Serializable {
    private String name;
    private String profileURL;
    private List<Course> classes;
    private int scoreRecent;
    private float scoreClassSize;
    private String uniqueId;
    private List<String> waveMocks;
    private boolean isWaving;
    public Person(String name, String profileURL, List<Course> classes, String uniqueId) {
        this.name = name;
        this.classes = classes;
        this.profileURL = profileURL;
        if (uniqueId == null) {
            this.uniqueId = UUID.randomUUID().toString();
        }
        else {
            this.uniqueId = uniqueId;
        }
        this.waveMocks = new ArrayList<>();
        this.isWaving = false;
    }

    public void addWaveMocks(String otherPersonId) {
        this.waveMocks.add(otherPersonId);
    }

    public List<String> getWaveMocks() {
        return this.waveMocks;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getUniqueId() {
        return this.uniqueId;
    }

    public List<Course> getClasses(){
        return this.classes;
    }

    public String getURL(){ return this.profileURL; }

    public String getName(){
        return this.name;
    }

    public String toString() {
        //return name + ", " + profileURL + ", " + this.classes.toString() + ", " + this.classes.size();
        return this.uniqueId;
    }
}


