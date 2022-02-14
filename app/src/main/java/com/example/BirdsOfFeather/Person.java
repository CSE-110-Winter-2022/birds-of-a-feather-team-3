package com.example.BirdsOfFeather;
import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {
    private String name;
    private String profileURL;
    private List<Course> classes;
    public Person(String name, String profileURL, List<Course> classes){
        this.name = name;
        this.classes = classes;
        this.profileURL = profileURL;
    }

    public List<Course> getClasses(){
        return this.classes;
    }

    public String getURL(){ return this.profileURL; }

    public String getName(){
        return this.name;
    }

    public String toString() {
        return name + ", " + profileURL + ", " + this.classes.toString() + ", " + this.classes.size();
    }
}


