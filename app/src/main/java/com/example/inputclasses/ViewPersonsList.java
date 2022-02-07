package com.example.inputclasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPersonsList extends AppCompatActivity {
    protected RecyclerView personsRecyclerView;
    protected RecyclerView.LayoutManager personsLayoutManager;
    protected PersonsViewAdapter personsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<String> persons;
//        // fake data of my classes
//        Person Rodney = new Person("Rodney", new String[]{"CSE21","MATH18"});
//
//        // fake data of people around
//        Person Lucas = new Person("Lucas", new String[]{"ECE45","ECE35","CSE21"});
//        Person Grace = new Person("Grace", new String[]{"ECE45","ECE35","MATH18"});
//        Person Mark = new Person("Mark", new String[]{"CSE21","MATH18","WCWP10A"});
//        Person Vicky = new Person("Vicky", new String[]{"WCWP10B","ECON109","WCWP10A"});
//
//        List<Person> fakeData = new ArrayList<>();
//        fakeData.add(Lucas);
//        fakeData.add(Grace);
//        fakeData.add(Mark);
//        fakeData.add(Vicky);

//        persons  = SearchClassmates.search(fakeData,Rodney);
        persons = new ArrayList<>();
        persons.add("Dummy1");
        persons.add("Dummy2");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_persons_list);
        setTitle("People with Shared Classes");

        personsRecyclerView = findViewById(R.id.persons_view);

        personsLayoutManager = new LinearLayoutManager(this);
        personsRecyclerView.setLayoutManager(personsLayoutManager);

        personsViewAdapter = new PersonsViewAdapter(persons);
        personsRecyclerView.setAdapter(personsViewAdapter);
    }

}