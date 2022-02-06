package com.example.inputclasses;

import java.util.ArrayList;
import java.util.List;

public class SearchClassmates {
    public static List<String> search(List<Person> Persons, Person me) {
        String [] myCourse = me.getClasses();
        List<String> nameList = new ArrayList<>();
        for (int i = 0 ; i < myCourse.length; i++){
            String currCourse = myCourse[i];

            for (int j = 0; j < Persons.size(); j ++){
                Person curr = Persons.get(j);
                for (int k = 0; k < curr.getClasses().length;k++){
                    String[] currClasses = curr.getClasses();
                    if (currCourse.equals(currClasses[k]))  {
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
