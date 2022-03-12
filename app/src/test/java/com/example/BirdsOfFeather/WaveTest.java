package com.example.BirdsOfFeather;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class WaveTest {
    @Test
    public void waveBringsUserToTop() {
        List<ProfileInfo> persons = new ArrayList<>();
        PersonsViewAdapter personViewAdapter = new PersonsViewAdapter(persons);
        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course3 = new Course("Winter", "2020", "Medium (75-150)","CSE", "101");
        Course course4 = new Course("Fall", "2020", "Tiny (<40)","WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));

        /**commonalities
         * Course1: Rodney, Grace, Vicki
         * Course2: Rodney, Grace, Mark
         * Course3: Mark
         * Course4: Vicki, Lucas
         */

        String img1 = "";
        //Rodney is app user
        Person Rodney = new Person("Rodney", img1, RodneyClasses);
        Person Lucas = new Person("Lucas", img1, LucasClasses);
        Person Grace = new Person("Grace", img1, GraceClasses);
        Person Mark = new Person("Mark", img1, MarkClasses);
        Person Vicki = new Person("Vicki", img1, VickiClasses);

        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Vicki);

        VickiInfo.setWaving(true);
        personViewAdapter.addPerson(LucasInfo, true);
        personViewAdapter.addPerson(GraceInfo, true);
        personViewAdapter.addPerson(MarkInfo, true);
        personViewAdapter.addPerson(VickiInfo, true);

        List<ProfileInfo> output = personViewAdapter.getProfileInfos();
        List<String> nameList = new ArrayList<>();
        for (ProfileInfo person : output) {
            nameList.add(person.getName());
        }
        String expectedOutput = "[Vicki, Grace, Mark]";
        assertEquals(expectedOutput, nameList.toString());
    }

    @Test
    public void wavesSortedCorrectly() {
        List<ProfileInfo> persons = new ArrayList<>();
        PersonsViewAdapter personViewAdapter = new PersonsViewAdapter(persons);
        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course3 = new Course("Winter", "2020", "Medium (75-150)","CSE", "101");
        Course course4 = new Course("Fall", "2020", "Tiny (<40)","WCWP", "10A");
        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));

        /**commonalities
         * Course1: Rodney, Grace, Vicki
         * Course2: Rodney, Grace, Mark
         * Course3: Mark
         * Course4: Vicki, Lucas
         */

        String img1 = "";
        //Rodney is app user
        Person Rodney = new Person("Rodney", img1, RodneyClasses);
        Person Lucas = new Person("Lucas", img1, LucasClasses);
        Person Grace = new Person("Grace", img1, GraceClasses);
        Person Mark = new Person("Mark", img1, MarkClasses);
        Person Vicki = new Person("Vicki", img1, VickiClasses);

        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Vicki);

        VickiInfo.setWaving(true);
        MarkInfo.setWaving(true);
        personViewAdapter.addPerson(LucasInfo, true);
        personViewAdapter.addPerson(GraceInfo, true);
        personViewAdapter.addPerson(MarkInfo, true);
        personViewAdapter.addPerson(VickiInfo, true);

        List<ProfileInfo> output = personViewAdapter.getProfileInfos();
        List<String> nameList = new ArrayList<>();
        for (ProfileInfo person : output) {
            nameList.add(person.getName());
        }
        String expectedOutput = "[Mark, Vicki, Grace]";
        assertEquals(expectedOutput, nameList.toString());
    }
}
