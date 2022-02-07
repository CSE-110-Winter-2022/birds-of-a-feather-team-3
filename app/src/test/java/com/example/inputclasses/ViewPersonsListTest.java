package com.example.inputclasses;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowAlertDialog;
import static org.junit.Assert.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ViewPersonsListTest {

    //Unit Tests
    @Test
    public void testSearchClassmates() {
        List<String> persons;
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

        List<String> expectedOutput = new ArrayList<String>();
        expectedOutput.add("Lucas");
        expectedOutput.add("Mark");
        expectedOutput.add("Grace");



        assertEquals(persons, expectedOutput);
    }
}
