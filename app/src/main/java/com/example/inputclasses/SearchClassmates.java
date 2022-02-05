package com.example.inputclasses;

import java.util.ArrayList;
import java.util.List;

public class SearchClassmates {
    public static List<String> search(List<Person> Persons, Person me) {
        String [] mycourse = me.getClasses();
        List<String> namelist = new ArrayList<>();
        for (int i = 0 ; i < mycourse.length; i++){
            String currcouse = mycourse[i];

            for (int j = 0; j < Persons.size(); j ++){
                Person curr = Persons.get(j);
                for (int k = 0; k < curr.getClasses().length;k++){
                    String[] currclasses = curr.getClasses();
                    if (currcouse.equals(currclasses[k]))  {
                        if (!namelist.contains(curr.getName())){
                            namelist.add(curr.getName());
                        }
                    }
                }
            }

        }

        return namelist;
    }



}
