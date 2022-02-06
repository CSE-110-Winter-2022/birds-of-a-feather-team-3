package com.example.inputclasses;
public class Person {
    private String name;
    private String[] classes;

    public Person(String name, String[] classes){
        this.name = name;
        this.classes = classes;
    }

    public String[] getClasses(){
        return this.classes;
    }

    public String getName(){
        return this.name;
    }

}
