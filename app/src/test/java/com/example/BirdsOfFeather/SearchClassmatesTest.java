package com.example.BirdsOfFeather;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;


public class  SearchClassmatesTest {
    @Test
    public void test() {
        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course3 = new Course("Winter", "2020", "Medium (75-150)","CSE", "101");
        Course course4 = new Course("Fall", "2020", "Tiny (<40)","WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));

        /***commonalities
         * Course1: Rodney, Grace, Vicki
         * Course2: Rodney, Grace, Mark
         * Course3: Mark
         * Course4: Vicki, Lucas
         */

        String img1 = "";

        Person Rodney = new Person("Rodney", img1, RodneyClasses);
        Person Lucas = new Person("Lucas", img1, LucasClasses);
        Person Grace = new Person("Grace", img1, GraceClasses);
        Person Mark = new Person("Mark", img1, MarkClasses);
        Person Vicki = new Person("Vicki", img1, VickiClasses);

        SearchClassmates.detectAndReturnSharedClasses(Rodney, Lucas);
    }
}
