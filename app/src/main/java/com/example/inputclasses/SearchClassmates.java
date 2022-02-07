package com.example.inputclasses;

import java.util.ArrayList;
import java.util.List;

public class SearchClassmates {
    public static List<String> search(List<Person> Persons, Person me) {
        List<Course> myCourses = me.getClasses();
        List<String> nameList = new ArrayList<>();
        for (int i = 0 ; i < myCourses.size(); i++){
            Course currCourse = myCourses.get(i);

            for (int j = 0; j < Persons.size(); j ++){
                Person curr = Persons.get(j);
                for (int k = 0; k < curr.getClasses().size();k++){
                    List<Course> currClasses = curr.getClasses();
                    if (currCourse.equals(currClasses.get(k)))  {
                        if (!nameList.contains(curr.getName())){
                            nameList.add(curr.getName());
                        }
                    }
                }
            }

        }

        return nameList;
    }



}
