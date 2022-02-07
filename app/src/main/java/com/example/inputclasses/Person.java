package com.example.inputclasses;

import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {
    private String name;
    //private String profileURL;
    private List<Course> classes;

    public Person(String name, List<Course> classes){
        this.name = name;
        this.classes = classes;
        //this.profileURL = profileURL;
    }

    public List<Course> getClasses(){
        return this.classes;
    }

    public String getName(){
        return this.name;
    }

    //public String getProfileURL(){return this.profileURL;}

}


