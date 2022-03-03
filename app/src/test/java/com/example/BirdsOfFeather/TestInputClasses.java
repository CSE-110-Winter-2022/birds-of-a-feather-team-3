package com.example.BirdsOfFeather;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowAlertDialog;
import static org.junit.Assert.*;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestInputClasses {

    //Unit Tests
    @Test
    public void testCheckIsDuplicate() {
        Course course1 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course3 = new Course("Winter", "2020", "Medium (75-150)","CSE", "101");
        List<Course> mockCourseList = new ArrayList<>();
        mockCourseList.add(course1);
        InputClasses inputClasses = new InputClasses();
        assertEquals(true, inputClasses.checkIsDuplicate(mockCourseList, course2));
        assertEquals(false, inputClasses.checkIsDuplicate(mockCourseList, course3));
    }

    @Test
    public void testCheckValuesEmpty () {
        //Course course1 = new Course("Fall", "2020", "Large (150-250)", "CSE", "100");
        //Course course2 = new Course("Fall", "2020", "Large (150-250)", "CSE", "100");
        //Course course3 = new Course("Winter", "2020", "Medium (75-150)", "CSE", "101");
        InputClasses inputClasses = new InputClasses();
        boolean empty1 = inputClasses.checkValuesEmpty("", "2021", "Large (150-250)", "CSE" ,"101");
        boolean empty2 = inputClasses.checkValuesEmpty("Fall","" ,"Large (150-250)", "CSE" ,"101");
        boolean empty3 = inputClasses.checkValuesEmpty("Fall","2021", "", "CSE", "101" );
        boolean empty4 = inputClasses.checkValuesEmpty("Fall", "2021", "Large (150-250)", "", "101");
        boolean empty5 = inputClasses.checkValuesEmpty("Fall", "2021", "Large (150-250)", "CSE", "");
        boolean full1 = inputClasses.checkValuesEmpty("Winter", "2021", "Large (150-250)","CSE" ,"12");
        boolean full2 = inputClasses.checkValuesEmpty("Spring", "2019", "Medium (75-150)","CSE" ,"15");
        assertEquals(true, empty1);
        assertEquals(true, empty2);
        assertEquals(true, empty3);
        assertEquals(true, empty4);
        assertEquals(true, empty5);
        assertEquals(false, full1);
        assertEquals(false, full2);
    }

    //Integration Tests
    @Rule
    public ActivityScenarioRule<InputClasses> scenarioRule = new ActivityScenarioRule<>(InputClasses.class);
    @Test
    public void testEmptyTextFields() {
        ActivityScenario<InputClasses> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button enterButton = activity.findViewById(R.id.enter_class_button);
            EditText subjectView = activity.findViewById(R.id.subject_edittext);
            EditText courseNumberView = activity.findViewById(R.id.class_number_edittext);
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            subjectView.setText("");
            courseNumberView.setText("");
            enterButton.performClick();
            assertEquals(1, ShadowAlertDialog.getShownDialogs().size());
        });
    }

    @Test
    public void testInputtingDuplicate() {
        ActivityScenario<InputClasses> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            Button enterButton = activity.findViewById(R.id.enter_class_button);
            EditText subjectView = activity.findViewById(R.id.subject_edittext);
            EditText courseNumberView = activity.findViewById(R.id.class_number_edittext);
            Spinner quarterSpinner = activity.findViewById(R.id.quarter_dropdown);
            Spinner yearSpinner = activity.findViewById(R.id.year_dropdown);
            Spinner sizeSpinner = activity.findViewById(R.id.size_dropdown);
            quarterSpinner.setPrompt("Fall");
            yearSpinner.setPrompt("2018");
            sizeSpinner.setPrompt("Medium (75-150)");
            subjectView.setText("CSE");
            courseNumberView.setText("110");
            enterButton.performClick();
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            courseNumberView.setText("100");
            enterButton.performClick();
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            enterButton.performClick();
            assertEquals(1, ShadowAlertDialog.getShownDialogs().size());
        });
    }
}
