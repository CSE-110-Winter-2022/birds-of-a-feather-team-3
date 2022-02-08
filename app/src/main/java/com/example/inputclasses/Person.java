package com.example.inputclasses;
public class Person {
    private String name;
    private String[] classes;
    private int matches;

    public Person(String name, String[] classes){
        this.name = name;
        this.classes = classes;
    }

    public void setMatches(int i) {
        matches = i;
    }

    public String[] getClasses(){
        return this.classes;
    }

    public String getName(){
        return this.name;
    }

    public int getMatches() {return this.matches;}

}
