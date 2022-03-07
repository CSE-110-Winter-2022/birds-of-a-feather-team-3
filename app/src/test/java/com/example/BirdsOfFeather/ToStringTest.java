package com.example.BirdsOfFeather;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ToStringTest {
    @Test
    public void testPersonToString() {
        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        List<Course> TestClasses = new ArrayList<>(Arrays.asList(course1, course2));
        Person testPerson = new Person("Test", "test.com", TestClasses);
        testPerson.setUniqueId("abababababbabbabbabab");
        String expectedToString = "abababababbabbabbabab";
        assertEquals(expectedToString, testPerson.toString());
    }

    @Test
    public void testCourseToString() {
        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        String expectedToString = "Spring 2020 Large (150-250) CSE 110";
        assertEquals(expectedToString, course1.toString());
    }
}
