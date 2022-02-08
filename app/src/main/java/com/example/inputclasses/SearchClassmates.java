package com.example.inputclasses;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class SearchClassmates {


    public static List<Person> search(List<Person> Persons, Person me) {
//        String [] myCourse = me.getClasses();
//        List<String> nameList = new ArrayList<>();
//        for (int i = 0 ; i < myCourse.length; i++){
//            String currCourse = myCourse[i];
//
//            for (int j = 0; j < Persons.size(); j ++){
//                Person curr = Persons.get(j);
//                for (int k = 0; k < curr.getClasses().length;k++){
//                    String[] currClasses = curr.getClasses();
//                    if (currCourse.equals(currClasses[k]))  {
//                        if (!nameList.contains(curr.getName())){
//                            nameList.add(curr.getName());
//                        }
//                    }
//                }
//            }
//
//        }
//
//        return nameList;

        // new algorithm using hashset that allows duplicate names and keep track of matches
        String [] myCourse = me.getClasses();
        List<Person> personsList = new ArrayList<>();
        HashSet<String> myCourseSet = new HashSet<>();
        Boolean allowDupNames;

        // populate the hashset
        for (int i = 0; i < myCourse.length; i++) {
            myCourseSet.add(myCourse[i]);
        }

        for (int i = 0; i < Persons.size(); i++){
            allowDupNames = true;
            Person curr = Persons.get(i);
            String[] currClasses = curr.getClasses();
            int matches = 0;
            for (int j = 0; j < currClasses.length;j++){
                if (myCourseSet.contains(currClasses[j]))  {
                    matches++;
                    if (allowDupNames){
                        personsList.add(curr);
                        allowDupNames = false;
                    }
                }
            }
            curr.setMatches(matches);
        }

        // sort the name list according to number of matches by pushing them into a priority queue
        PriorityQueue<Person> pq = new PriorityQueue<Person>(personsList.size(), new Comparator<Person>() {
            @Override
            public int compare(Person s, Person t) {
                if (s.getMatches() > t.getMatches()) {
                    return 1;
                }
                else if (s.getMatches() == t.getMatches()) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
        });

        for (int i = 0; i < personsList.size(); i++) {
            pq.add(personsList.get(i));
        }

        List<Person> orderedPersons = new ArrayList<>();
        while (pq.size() > 0) {
            // add to the front of the list as pq pop small number first
            orderedPersons.add(0,pq.poll());
        }
        return orderedPersons;
    }
}
