package com.example.inputclasses;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class SearchClassmates {
    public static ProfileInfo detectAndReturnSharedClasses(Person firstPerson, Person secondPerson) {
        List<Course> sharedCourses = new ArrayList<>();
        List<Course> firstPersonCourses = firstPerson.getClasses();
        List<Course> secondPersonCourses = secondPerson.getClasses();
        for (Course compareCourse : firstPersonCourses) {
            for (Course comparingCourse : secondPersonCourses) {
                if (compareCourse.toString().equals(comparingCourse.toString())) {
                    sharedCourses.add(compareCourse);
                }
            }
        }

        if (sharedCourses.size() > 0) {
            ProfileInfo newProfileInfo = new ProfileInfo(secondPerson.getName(), secondPerson.getURL(), sharedCourses);
            return newProfileInfo;
        }
        return null;
    }
    /*
    public static List<String> search(List<Person> Persons, Person me) {
        List<Course> myCourses = me.getClasses();
        List<String> nameList = new ArrayList<>();
        for (int i = 0 ; i < myCourses.size(); i++) {
            Course currCourse = myCourses.get(i);
            for (int j = 0; j < Persons.size(); j ++) {
                Person curr = Persons.get(j);
                for (int k = 0; k < curr.getClasses().size();k++) {
                    List<Course> currClasses = curr.getClasses();
                    if (currCourse.equals(currClasses.get(k))) {
                        if (!nameList.contains(curr.getName())) {
                            nameList.add(curr.getName());
                        }
*/
    public static List<Person> search (List <Person> Persons, Person me){
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

             //new algorithm using hashset that allows duplicate names and keep track of matches
         List<Course> myCourses = me.getClasses();
         List<Person> personsList = new ArrayList<>();
         HashSet<Course> myCourseSet = new HashSet<>();
         boolean allowDupNames;

         // populate the hashset
         for (Course course : myCourses) {
             myCourseSet.add(course);
         }

         for (int i = 0; i < Persons.size(); i++){
             allowDupNames = true;
             Person curr = Persons.get(i);
             List<Course> currClasses = curr.getClasses();
             int matches = 0;
             for (Course currClass : currClasses){
                 if (myCourseSet.contains(currClass))  {
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