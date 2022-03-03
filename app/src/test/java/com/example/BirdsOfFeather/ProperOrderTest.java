package com.example.BirdsOfFeather;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class ProperOrderTest {

    @Test
    public void chronologicalCompareTest() {
        //
        Course course1 = new Course("Winter", "2020", "Large (150-250)","CSE", "101");
        Course course2 = new Course("Spring", "2020", "Large (150-250)","CSE", "101");
        Course course3 = new Course("Summer Session I", "2020", "Large (150-250)","CSE", "101");
        Course course4 = new Course("Summer Session II", "2020", "Large (150-250)","CSE", "101");
        Course course5 = new Course("Special Summer Session", "2020", "Large (150-250)","CSE", "101");
        Course course6 = new Course("Fall", "2020", "Large (150-250)","CSE", "101");
        Course course7 = new Course("Fall", "2021", "Large (150-250)","CSE", "101");
        Course course8 = new Course("Winter", "2022", "Large (150-250)","CSE", "101");
        Course course9 = new Course("Summer Session I", "2022", "Large (150-250)","CSE", "101");


        Map<Course, Integer> courseOrder = new HashMap<>();

        courseOrder.put(course1, 1);
        courseOrder.put(course2, 2);
        courseOrder.put(course3, 3);
        courseOrder.put(course4, 4);
        courseOrder.put(course5, 5);
        courseOrder.put(course6, 6);
        courseOrder.put(course7, 7);
        courseOrder.put(course8, 8);
        courseOrder.put(course9, 9);

        List<Course> randomList = new ArrayList<>(Arrays.asList(course9, course7, course8, course5, course4, course6, course2, course1, course3));
        Collections.sort(randomList, new ChronologicalComparator());
        List<Integer> expected = new ArrayList<>();
        for (Course course : randomList) {
            expected.add(courseOrder.get(course));
        }
        assertEquals("[1, 2, 3, 4, 5, 6, 7, 8, 9]", expected.toString());
    }
}
