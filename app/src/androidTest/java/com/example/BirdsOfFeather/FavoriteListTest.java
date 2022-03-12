package com.example.BirdsOfFeather;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BirdsOfFeather.database.ClassEntity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FavoriteListTest {

    @Rule
    public ActivityTestRule<ViewPersonsList> viewPersonsListActivityTestRule = new ActivityTestRule<>(ViewPersonsList.class);

    @Before
    public void setup() {
        viewPersonsListActivityTestRule.getActivity().db.classesDao().insert(new ClassEntity("Special Summer Session", "2018", "Gigantic (400+)", "VIS", "60"));
    }

    @After
    public void recoverDatabase() {
        viewPersonsListActivityTestRule.getActivity().db.classesDao().delete(new ClassEntity("Special Summer Session", "2018", "Gigantic (400+)", "VIS", "60"));
    }




    @Test
    public void favoriteListTest() {



//        ViewInteraction materialButton = onView(
//                allOf(withId(android.R.id.button1), withText("Understood"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withClassName(is("android.widget.ScrollView")),
//                                        0),
//                                3)));
//        materialButton.perform(scrollTo(), click());
//
//        ViewInteraction appCompatEditText = onView(
//                allOf(withId(R.id.enter_name_view),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatEditText.perform(replaceText("test"), closeSoftKeyboard());
//
//        ViewInteraction materialButton2 = onView(
//                allOf(withId(R.id.save_name_button), withText("Save"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                1),
//                        isDisplayed()));
//        materialButton2.perform(click());
//
//        ViewInteraction materialButton3 = onView(
//                allOf(withId(R.id.confirm_profile_picture_button), withText("Confirm"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                1),
//                        isDisplayed()));
//        materialButton3.perform(click());
//
//        ViewInteraction appCompatSpinner = onView(
//                allOf(withId(R.id.quarter_dropdown),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                3),
//                        isDisplayed()));
//        appCompatSpinner.perform(click());
//
//        DataInteraction appCompatCheckedTextView = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(0);
//        appCompatCheckedTextView.perform(click());
//
//        ViewInteraction appCompatSpinner2 = onView(
//                allOf(withId(R.id.year_dropdown),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                1),
//                        isDisplayed()));
//        appCompatSpinner2.perform(click());
//
//        DataInteraction appCompatCheckedTextView2 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(4);
//        appCompatCheckedTextView2.perform(click());
//
//        ViewInteraction appCompatSpinner3 = onView(
//                allOf(withId(R.id.size_dropdown),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatSpinner3.perform(click());
//
//        DataInteraction appCompatCheckedTextView3 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(4);
//        appCompatCheckedTextView3.perform(click());
//
//        ViewInteraction appCompatSpinner4 = onView(
//                allOf(withId(R.id.size_dropdown),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                0),
//                        isDisplayed()));
//        appCompatSpinner4.perform(click());
//
//        DataInteraction appCompatCheckedTextView4 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(5);
//        appCompatCheckedTextView4.perform(click());
//
//        ViewInteraction appCompatSpinner5 = onView(
//                allOf(withId(R.id.quarter_dropdown),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                3),
//                        isDisplayed()));
//        appCompatSpinner5.perform(click());
//
//        DataInteraction appCompatCheckedTextView5 = onData(anything())
//                .inAdapterView(childAtPosition(
//                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
//                        0))
//                .atPosition(5);
//        appCompatCheckedTextView5.perform(click());
//
//        ViewInteraction appCompatEditText2 = onView(
//                allOf(withId(R.id.subject_edittext),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                5),
//                        isDisplayed()));
//        appCompatEditText2.perform(replaceText("VIS"), closeSoftKeyboard());
//
//        ViewInteraction appCompatEditText3 = onView(
//                allOf(withId(R.id.class_number_edittext),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                4),
//                        isDisplayed()));
//        appCompatEditText3.perform(replaceText("60"), closeSoftKeyboard());
//
//        ViewInteraction materialButton4 = onView(
//                allOf(withId(R.id.enter_class_button), withText("Enter"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                7),
//                        isDisplayed()));
//        materialButton4.perform(click());
//
//        ViewInteraction materialButton5 = onView(
//                allOf(withId(R.id.done_input_classes_button), withText("Done"),
//                        childAtPosition(
//                                childAtPosition(
//                                        withId(android.R.id.content),
//                                        0),
//                                6),
//                        isDisplayed()));
//        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.shareButton), withText("Start"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.new_session_button), withText("New Session"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.custom),
                                        0),
                                4),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.back_button), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.mockDataTextView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.mockDataTextView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nBill3,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.mock_enterButton), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton9.perform(click());


        SystemClock.sleep(5000);


        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.favorites_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.favorite_button_profile),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        pressBack();

        ViewInteraction materialButton10 = onView(
                allOf(withId(R.id.back_button), withText("Mock"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton10.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.mockDataTextView),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nBill3,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nBill3,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText7.perform(click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nBill3,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nrODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"));

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nrODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText9.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nrODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText10.perform(click());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nrODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText11.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nRODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"));

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nRODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText12.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nRODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText13.perform(click());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac190002,,,,\nRODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("a4ca50b6-941b-11ec-b909-0242ac192002,,,,\nRODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"));

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.mockDataTextView), withText("a4ca50b6-941b-11ec-b909-0242ac192002,,,,\nRODNEY,,,,\nhttps://lh3.googleusercontent.com/pw/AM-JKLXQ2ix4dg-PzLrPOSMOOy6M3PSUrijov9jCLXs4IGSTwN73B4kr-F6Nti_4KsiUU8LzDSGPSWNKnFdKIPqCQ2dFTRbARsW76pevHPBzc51nceZDZrMPmDfAYyI4XNOnPrZarGlLLUZW9wal6j-z9uA6WQ=w854-h924-no?authuser=0,,,,\n2018,SSS,VIS,60,Gigantic\n2022,WI,CSE,110,Large\n4b295157-ba31-4f9f-8401-5d85d9cf659a,wave,,,"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText15.perform(closeSoftKeyboard());

        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.mock_enterButton), withText("Enter"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton11.perform(click());
        SystemClock.sleep(5000);
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.favorites_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));
        SystemClock.sleep(5000);
        pressBack();

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.favorite_person_row_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.favorites_view),
                                        1),
                                4),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction recyclerView3 = onView(
                allOf(withId(R.id.favorites_view),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView3.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.favorite_button_profile),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        pressBack();

        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.go_to_sessions_button), withText("Favorites"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton12.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.person_row_name), withText("Bill3"),
                        withParent(withParent(withId(R.id.favorites_view))),
                        isDisplayed()));
        textView.check(matches(withText("Bill3")));
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
