package com.example.BirdsOfFeather;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.BirdsOfFeather.R;
import com.example.BirdsOfFeather.database.ClassEntity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Turnoffstudentsearchtest {

    @Rule
    public ActivityTestRule<ViewPersonsList> viewPersonsListActivityTestRule = new ActivityTestRule<>(ViewPersonsList.class);

    @Before
    public void setup() {
        viewPersonsListActivityTestRule.getActivity().db.classesDao().insert(new ClassEntity("Fall", "2021", "Small (40-75)", "CSE", "210"));
    }

    @After
    public void recoverDatabase() {
        viewPersonsListActivityTestRule.getActivity().db.classesDao().delete(new ClassEntity("Fall", "2021", "Small (40-75)", "CSE", "210"));
    }

    @Test
    public void turnoffstudentsearchtest() {
//        ViewInteraction materialButton = onView(
//        allOf(withId(android.R.id.button1), withText("Understood"),
//        childAtPosition(
//        childAtPosition(
//        withClassName(is("android.widget.ScrollView")),
//        0),
//        3)));
//                materialButton.perform(scrollTo(), click());
//
//                ViewInteraction appCompatEditText = onView(
//        allOf(withId(R.id.enter_name_view),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        0),
//        isDisplayed()));
//                appCompatEditText.perform(replaceText("test_1"), closeSoftKeyboard());
//
//                ViewInteraction materialButton2 = onView(
//        allOf(withId(R.id.save_name_button), withText("Save"),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        1),
//        isDisplayed()));
//                materialButton2.perform(click());
//
//                ViewInteraction materialButton3 = onView(
//        allOf(withId(R.id.confirm_profile_picture_button), withText("Confirm"),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        1),
//        isDisplayed()));
//                materialButton3.perform(click());
//
//                ViewInteraction appCompatSpinner = onView(
//        allOf(withId(R.id.year_dropdown),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        1),
//        isDisplayed()));
//                appCompatSpinner.perform(click());
//
//                DataInteraction appCompatCheckedTextView = onData(anything())
//        .inAdapterView(childAtPosition(
//        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//        0))
//        .atPosition(1);
//                appCompatCheckedTextView.perform(click());
//
//                ViewInteraction appCompatSpinner2 = onView(
//        allOf(withId(R.id.size_dropdown),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        0),
//        isDisplayed()));
//                appCompatSpinner2.perform(click());
//
//                DataInteraction appCompatCheckedTextView2 = onData(anything())
//        .inAdapterView(childAtPosition(
//        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//        0))
//        .atPosition(1);
//                appCompatCheckedTextView2.perform(click());
//
//                ViewInteraction appCompatEditText2 = onView(
//        allOf(withId(R.id.subject_edittext),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        5),
//        isDisplayed()));
//                appCompatEditText2.perform(replaceText("CSE"), closeSoftKeyboard());
//
//                ViewInteraction appCompatEditText3 = onView(
//        allOf(withId(R.id.class_number_edittext),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        4),
//        isDisplayed()));
//                appCompatEditText3.perform(replaceText("210"), closeSoftKeyboard());
//
//                ViewInteraction materialButton4 = onView(
//        allOf(withId(R.id.enter_class_button), withText("Enter"),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        7),
//        isDisplayed()));
//                materialButton4.perform(click());
//
//                ViewInteraction materialButton5 = onView(
//        allOf(withId(R.id.done_input_classes_button), withText("Done"),
//        childAtPosition(
//        childAtPosition(
//        withId(android.R.id.content),
//        0),
//        6),
//        isDisplayed()));
//                materialButton5.perform(click());

                ViewInteraction materialButton6 = onView(
        allOf(withId(R.id.back_button), withText("Mock"),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        2),
        isDisplayed()));
                materialButton6.perform(click());

                ViewInteraction appCompatEditText4 = onView(
        allOf(withId(R.id.mockDataTextView),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        0),
        isDisplayed()));
                appCompatEditText4.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac120002,,,,\nBill,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2021,FA,CSE,210,Small\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

                ViewInteraction materialButton7 = onView(
        allOf(withId(R.id.mock_enterButton), withText("Enter"),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        1),
        isDisplayed()));
                materialButton7.perform(click());

                ViewInteraction recyclerView = onView(
        allOf(withId(R.id.favorites_view),
        withParent(withParent(withId(android.R.id.content))),
        isDisplayed()));
                recyclerView.check(matches(isDisplayed()));
        }
    
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
