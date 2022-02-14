package com.example.BirdsOfFeather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
}

class ChronologicalComparator implements Comparator<Course> {
    List<String> quarterOrder = new ArrayList<>(Arrays.asList("Winter",
            "Spring", "Summer Session I", "Summer Session II", "Special Summer Session", "Fall"));

    @Override
    public int compare(Course course1, Course course2) {
        int diff = Integer.valueOf(course1.getYear()) - Integer.valueOf(course2.getYear());
        if (diff != 0) {
            return diff;
        }
        return quarterOrder.indexOf(course1.getQuarter()) - quarterOrder.indexOf(course2.getQuarter());
    }

}