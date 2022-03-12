package com.example.BirdsOfFeather;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowAlertDialog;


@RunWith(AndroidJUnit4.class)
public class MockInputTest {


    //Unit Tests

    @Test
    public void testQuarterCodeConverter(){

        MockInputPeople mockInputPeople = new MockInputPeople();
        String code;

        code = "FA";
        assertEquals("Fall", mockInputPeople.quarterCodeToQuarter(code));
        code = "WI";
        assertEquals("Winter", mockInputPeople.quarterCodeToQuarter(code));
        code = "SP";
        assertEquals("Spring", mockInputPeople.quarterCodeToQuarter(code));
        code = "SS1";
        assertEquals("Summer Session I", mockInputPeople.quarterCodeToQuarter(code));
        code = "SS2";
        assertEquals("Summer Session II", mockInputPeople.quarterCodeToQuarter(code));
        code = "SSS";
        assertEquals("Special Summer Session", mockInputPeople.quarterCodeToQuarter(code));
    }

    @Test
    public void testParseCourse(){

        MockInputPeople mockInputPeople = new MockInputPeople();

        String courseToParse = "2022,WI,CSE,110,Tiny";
        Course parsedCourse = mockInputPeople.parseCourse(courseToParse);

        assertEquals("2022", parsedCourse.year);
        assertEquals("Winter", parsedCourse.quarter);
        assertEquals("CSE", parsedCourse.subject);
        assertEquals("110", parsedCourse.classNumber);
        assertEquals("Tiny (<40)", parsedCourse.classSize);

    }

    @Test
    public void testParseWave(){
        MockInputPeople mockInputPeople = new MockInputPeople();

        String waveToParse = "4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,";

        assertEquals("4b295157-ba31-4f9f-8401-5d85d9cf659a", mockInputPeople.parseWave(waveToParse));
    }


    @Test
    public void testSizeToClassSize(){
        MockInputPeople mockInputPeople = new MockInputPeople();
        String size;

        size = "Tiny";
        assertEquals("Tiny (<40)", mockInputPeople.sizeToClassSize(size));
        size = "Small";
        assertEquals("Small (40-75)", mockInputPeople.sizeToClassSize(size));
        size = "Medium";
        assertEquals("Medium (75-150)", mockInputPeople.sizeToClassSize(size));
        size = "Large";
        assertEquals("Large (150-250)", mockInputPeople.sizeToClassSize(size));
        size = "Huge";
        assertEquals("Huge (250-400)", mockInputPeople.sizeToClassSize(size));
        size = "Gigantic";
        assertEquals("Gigantic (400+)", mockInputPeople.sizeToClassSize(size));
    }



    @Rule
    public ActivityScenarioRule<MockInputPeople> scenarioRule = new ActivityScenarioRule<>(MockInputPeople.class);

    @Test
    public void testNoCourses(){
        ActivityScenario<MockInputPeople> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button enterButton = activity.findViewById(R.id.mock_enterButton);
            EditText inputText = activity.findViewById(R.id.mockDataTextView);

            inputText.setText("UNIQUEID,,,," + System.lineSeparator() + "Joe,,,,"+ System.lineSeparator() +
                    "URL,,,,");
            enterButton.performClick();

            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());

        });

    }



}
