package com.example.inputclasses;

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

import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ViewPersonsListTest {

    //Unit Tests
    @Test
    public void testSearchClassmatesNormal() {
        List<Person> persons;

        // fake data of my classes
        Person Rodney = new Person("Rodney", new String[]{"CSE21","MATH18"});

        // fake data of people around
        Person Lucas = new Person("Lucas", new String[]{"ECE45","ECE35","CSE21"});
        Person Grace = new Person("Grace", new String[]{"ECE45","ECE35","MATH18"});
        Person Mark = new Person("Mark", new String[]{"CSE21","MATH18","WCWP10A"});
        Person Vicky = new Person("Vicky", new String[]{"WCWP10B","ECON109","WCWP10A"});

        List<Person> fakeData = new ArrayList<>();
        fakeData.add(Lucas);
        fakeData.add(Grace);
        fakeData.add(Mark);
        fakeData.add(Vicky);

        persons  = SearchClassmates.search(fakeData,Rodney);

        List<String> output = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            output.add(persons.get(i).getName());
        }
        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("Mark");
        expectedOutput.add("Grace");
        expectedOutput.add("Lucas");

        assertEquals(expectedOutput, output);
    }

    // unit test
    @Test
    public void testSearchClassmatesDupNames() {
        List<Person> persons;
        // fake data of my classes
        Person Rodney = new Person("Rodney", new String[]{"CSE21","MATH18", "CSE30", "CSE21", "CSE101"});

        // fake data of people around
        Person Lucas = new Person("Lucas", new String[]{"CSE21","MATH18", "CSE30", "ECE101", "ECE109"});
        Person Grace = new Person("Grace", new String[]{"CSE21","MATH18", "CSE30", "CSE21"});
        Person DupVicki = new Person("Vicki", new String[]{"CSE21","MATH18", "CSE30", "CSE21", "CSE101"});
        Person Mark = new Person("Mark", new String[]{"CSE21","MATH18", "ECON100", "MATH20A"});
        Person Vicky = new Person("Vicki", new String[]{"WCWP10B","ECON109","WCWP10A", "CSE101"});

        List<Person> fakeData = new ArrayList<>();
        fakeData.add(Lucas);
        fakeData.add(Grace);
        fakeData.add(DupVicki);
        fakeData.add(Mark);
        fakeData.add(Vicky);


        persons  = SearchClassmates.search(fakeData,Rodney);
        List<String> output = new ArrayList<>();
        for (int i = 0; i < persons.size(); i++) {
            output.add(persons.get(i).getName());
        }

        List<String> expectedOutput = new ArrayList<>();
        expectedOutput.add("Vicki");
        expectedOutput.add("Grace");
        expectedOutput.add("Lucas");
        expectedOutput.add("Mark");
        expectedOutput.add("Vicki");


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
