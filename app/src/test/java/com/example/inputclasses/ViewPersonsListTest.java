package com.example.inputclasses;
/**
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import org.junit.Rule;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.junit.runner.RunWith;
//import org.robolectric.shadows.ShadowAlertDialog;
import static org.junit.Assert.*;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import androidx.lifecycle.Lifecycle;
//import androidx.test.core.app.ActivityScenario;
//import androidx.test.ext.junit.rules.ActivityScenarioRule;
//import androidx.test.ext.junit.runners.AndroidJUnit4;

import static java.util.Collections.sort;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ViewPersonsListTest {

    //Unit Tests
    @Test
    public void testSearchClassmatesNormal() {
        List<Person> persons;

        // fake data of my classes
        /*
        Person Rodney = new Person("Rodney", new String[]{"CSE21","MATH18"});
        Person Lucas = new Person("Lucas", new String[]{"ECE45","ECE35","CSE21"});
        Person Grace = new Person("Grace", new String[]{"ECE45","ECE35","MATH18"});
        Person Mark = new Person("Mark", new String[]{"CSE21","MATH18","WCWP10A"});
        Person Vicky = new Person("Vicky", new String[]{"WCWP10B","ECON109","WCWP10A"});


        Course course1 = new Course("Spring", "2020", "CSE", "110");
        Course course2 = new Course("Fall", "2020", "CSE", "100");
        Course course3 = new Course("Winter", "2020", "CSE", "101");
        Course course4 = new Course("Fall", "2020", "WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));

        **commonalities
         * Course1: Rodney, Grace, Vicki
         * Course2: Rodney, Grace, Mark
         * Course3: Mark
         * Course4: Vicki, Lucas


        String img1 = "";

        Person Rodney = new Person("Rodney", img1, RodneyClasses);
        Person Lucas = new Person("Lucas", img1, LucasClasses);
        Person Grace = new Person("Grace", img1, GraceClasses);
        Person Mark = new Person("Mark", img1, MarkClasses);
        Person Vicki = new Person("Vicki", img1, VickiClasses);

        List<Person> fakeData = new ArrayList<>();
        fakeData.add(Lucas);
        fakeData.add(Grace);
        fakeData.add(Mark);
        fakeData.add(Vicki);
        persons  = SearchClassmates.search(fakeData, Rodney);

        List<String> output = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            output.add(persons.get(i).getName());
        }
        List<String> expectedOutput = new ArrayList<>(Arrays.asList("Grace", "Vicki", "Mark"));
        assertEquals(expectedOutput, output);
    }

    // unit test
    @Test
    public void testSearchClassmatesDupNames() {
        List<Person> persons;
        // fake data of my classes


        Course course1 = new Course("Spring", "2020", "CSE", "110");
        Course course2 = new Course("Fall", "2020", "CSE", "100");
        Course course3 = new Course("Winter", "2020", "CSE", "101");
        Course course4 = new Course("Fall", "2020", "WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));
        List<Course> DupVickiClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));

        **commonalities
         * Course1: Rodney, Grace, Vicki, DupVicki
         * Course2: Rodney, Grace, Mark, DupVicki
         * Course3: Mark, DupVicki
         * Course4: Vicki, Lucas


        String img1 = "";

        Person Rodney = new Person("Rodney", img1,  RodneyClasses);
        Person Lucas = new Person("Lucas", img1, LucasClasses);
        Person Grace = new Person("Grace", img1, GraceClasses);
        Person Mark = new Person("Mark", img1, MarkClasses);
        Person Vicki = new Person("Vicki", img1, VickiClasses);
        Person DupVicki = new Person("Vicki", img1, DupVickiClasses);

        List<Person> fakeData = new ArrayList<>();
        fakeData.add(Lucas);
        fakeData.add(Grace);
        fakeData.add(DupVicki);
        fakeData.add(Mark);
        fakeData.add(Vicki);


        persons  = SearchClassmates.search(fakeData,Rodney);
        List<String> output = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            output.add(persons.get(i).getName());
        }

        List<String> expectedOutput = new ArrayList<>(Arrays.asList("Vicki", "Vicki", "Grace", "Mark"));
        sort(expectedOutput); //alphabetical order so it can be compared correctly
        sort(output);

        assertEquals(expectedOutput, output);
    }

//    //Integration Tests
//    @Rule
//    public ActivityScenarioRule<InputClasses> scenarioRule = new ActivityScenarioRule<>(ViewPersonsList.class);
//    @Test
//    public void testViewPersonsList() {
//        ActivityScenario<InputClasses> scenario = scenarioRule.getScenario();
//        scenario.moveToState(Lifecycle.State.CREATED);
//
//        scenario.onActivity(activity -> {
//
//            EditText subjectView = activity.findViewById(R.id.subject_edittext);
//            EditText courseNumberView = activity.findViewById(R.id.class_number_edittext);
//            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
//            subjectView.setText("");
//            courseNumberView.setText("");
//
//            assertEquals(1, ShadowAlertDialog.getShownDialogs().size());
//        });
//    }
}
**/