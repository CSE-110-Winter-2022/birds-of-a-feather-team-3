package com.example.BirdsOfFeather;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterScoreCalculationTest {
    @Test
    public void properMatchAmount() {
        List<Person> persons = new ArrayList<>();
        PersonsViewAdapter personViewAdapter = new PersonsViewAdapter(persons);
        Course course1 = new Course("Winter", "2022", "Large (150-250)", "CSE","110");
        //score = 5, 0.1
        Course course2 = new Course("Fall", "2021", "Gigantic (400+)", "CSE","100");
        //score = 4, 0.03
        Course course3 = new Course("Spring", "2021", "Small (40-75)", "CSE","101");
        //score = 2, 0.33
        Course course4 = new Course("Fall", "2020", "Tiny (<40)", "WCWP","10A");
        //score = 1, 1.00
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));
        List<Course> DupVickiClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));

        /***commonalities
         * Course1: Rodney, Grace, Vicki, DupVicki
         * Course2: Rodney, Grace, Mark, DupVicki
         * Course3: Mark, Grace, DupVicki
         * Course4: Vicki, Lucas
         */

        Person Rodney = new Person("Rodney", "", RodneyClasses);
        Person Lucas = new Person("Lucas", "", LucasClasses);
        Person Grace = new Person("Grace", "", GraceClasses);
        Person Mark = new Person("Mark", "", MarkClasses);
        Person Vicki = new Person("Vicki", "", VickiClasses);
        Person DupVicki = new Person("Vicki", "", DupVickiClasses);
        List<Course> selfClasses = new ArrayList<>(Arrays.asList(course1, course2, course3, course4));

        Person self = new Person("Self", "", selfClasses);

        ProfileInfo RodneyInfo = SearchClassmates.detectAndReturnSharedClasses(self, Rodney);
        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(self, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(self, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(self, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(self, Vicki);
        ProfileInfo DupVickiInfo = SearchClassmates.detectAndReturnSharedClasses(self, DupVicki);

        assertEquals(2, RodneyInfo.getCommonCourses().size());
        assertEquals(1, LucasInfo.getCommonCourses().size());
        assertEquals(3, GraceInfo.getCommonCourses().size());
        assertEquals(2, MarkInfo.getCommonCourses().size());
        assertEquals(2, VickiInfo.getCommonCourses().size());
        assertEquals(3, DupVickiInfo.getCommonCourses().size());


        //FilterScoreCalculation fliterScoreCalculation = new FilterScoreCalculation();
        //assertEquals(9,fliterScoreCalculation.score_recent(Rodney,self));
        //assertEquals(1,fliterScoreCalculation.score_recent(Lucas,self));
        //assertEquals(6,fliterScoreCalculation.score_recent(Mark,self));


        //here check if the score calculation with time is correct
        FilterScoreCalculation filterScoreCalculation = new FilterScoreCalculation();
        assertEquals(9,filterScoreCalculation.score_recent(RodneyInfo));
        assertEquals(1,filterScoreCalculation.score_recent(LucasInfo));
        assertEquals(6,filterScoreCalculation.score_recent(MarkInfo));
        assertEquals(11,filterScoreCalculation.score_recent(GraceInfo));

        //here check if the size score calculation is correct
        assertEquals(0.13,filterScoreCalculation.score_size(RodneyInfo),0.001);
        assertEquals(1.00,filterScoreCalculation.score_size(LucasInfo),0.001);
        assertEquals(0.36,filterScoreCalculation.score_size(MarkInfo),0.001);
        assertEquals(0.46,filterScoreCalculation.score_size(GraceInfo),0.001);

    }
}