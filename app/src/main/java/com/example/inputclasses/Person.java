package com.example.inputclasses;
import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {
    private String name;
    private String profileURL;
    private List<Course> classes;
    private int matches;
    public Person(String name, String profileURL, List<Course> classes){
        this.name = name;
        this.classes = classes;
        this.profileURL = profileURL;
    }

    public List<Course> getClasses(){
        return this.classes;
    }
    public void setMatches(int i) {
        matches = i;
    }

    /*
    public String[] getClasses(){
        return this.classes;
    }
*/
    public String getURL(){ return this.profileURL; }

    public String getName(){
        return this.name;
    }

    public int getMatches() {
        return this.matches;
    }

    public String toString() {
        return name + ", " + profileURL + ", " + this.classes.toString() + ", " + this.classes.size();
    }

    public boolean equals(Person otherPerson) {
        return this.toString().equals(otherPerson.toString());
    }
}


