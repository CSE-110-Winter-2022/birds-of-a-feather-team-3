package com.example.BirdsOfFeather;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PersonSerializerTest {
    @Test
    public void testPersonSerializationAndDeserialization() throws Exception {
        PersonSerializer personSerializer = new PersonSerializer();
        Course course1 = new Course("Spring", "2020", "CSE", "110","Large");
        Course course2 = new Course("Fall", "2020", "CSE", "100","Large");
        List<Course> testClasses = new ArrayList<>(Arrays.asList(course1, course2));
        Person testPerson = new Person("Rodney", "",  testClasses);
        byte[] result = personSerializer.convertToByteArray(testPerson);
        Person deserializedPerson = personSerializer.convertFromByteArray(result);
        assertEquals(true, deserializedPerson.toString().equals(testPerson.toString()));
    }
}
