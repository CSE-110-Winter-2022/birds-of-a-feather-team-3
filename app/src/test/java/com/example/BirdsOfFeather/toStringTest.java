package com.example.BirdsOfFeather;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class toStringTest {
    @Test
    public void testPersonToString() {
        Course course1 = new Course("Spring", "2020", "CSE", "110","Large");
        Course course2 = new Course("Fall", "2020", "CSE", "100","Large");
        List<Course> TestClasses = new ArrayList<>(Arrays.asList(course1, course2));
        Person testPerson = new Person("Test", "test.com", TestClasses);
        String expectedToString = "Test, test.com, [Spring 2020 CSE 110, Fall 2020 CSE 100], 2";
        assertEquals(expectedToString, testPerson.toString());
    }

    @Test
    public void testCourseToString() {
        Course course1 = new Course("Spring", "2020", "CSE", "110","Large");
        String expectedToString = "Spring 2020 CSE 110";
        assertEquals(expectedToString, course1.toString());
    }
}
