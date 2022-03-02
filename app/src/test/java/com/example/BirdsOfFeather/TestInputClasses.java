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
        Course course1 = new Course("Fall", "2020", "CSE", "100","Large");
        Course course2 = new Course("Fall", "2020", "CSE", "100","Large");
        Course course3 = new Course("Winter", "2020", "CSE", "101","Large");
        List<Course> mockCourseList = new ArrayList<>();
        mockCourseList.add(course1);
        InputClasses inputClasses = new InputClasses();
        assertEquals(true, inputClasses.checkIsDuplicate(mockCourseList, course2));
        assertEquals(false, inputClasses.checkIsDuplicate(mockCourseList, course3));
    }

    @Test
    public void testCheckValuesEmpty () {
        Course course1 = new Course("Fall", "2020", "CSE", "100","Large");
        Course course2 = new Course("Fall", "2020", "CSE", "100","Large");
        Course course3 = new Course("Winter", "2020", "CSE", "101","Large");
        InputClasses inputClasses = new InputClasses();
        boolean empty1 = inputClasses.checkValuesEmpty("Fall", "2020", "ECE" ,"");
        boolean empty2 = inputClasses.checkValuesEmpty("", "2021", "" ,"2020");
        boolean full1 = inputClasses.checkValuesEmpty("Winter", "2021", "CSE" ,"12");
        boolean full2 = inputClasses.checkValuesEmpty("Spring", "2019", "CSE" ,"15");
        assertEquals(true, empty1);
        assertEquals(true, empty2);
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
            quarterSpinner.setPrompt("Fall");
            yearSpinner.setPrompt("2018");
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
