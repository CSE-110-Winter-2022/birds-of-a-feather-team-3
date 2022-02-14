//package com.example.inputclasses;

package com.example.BirdsOfFeather;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ViewPersonsListTest {

    //Unit Tests
    @Test
    public void testSearchClassmatesNormal() {
        List<Person> persons = new ArrayList<>();
        PersonsViewAdapter personViewAdapter = new PersonsViewAdapter(persons);
        Course course1 = new Course("Spring", "2020", "CSE", "110");
        Course course2 = new Course("Fall", "2020", "CSE", "100");
        Course course3 = new Course("Winter", "2020", "CSE", "101");
        Course course4 = new Course("Fall", "2020", "WCWP", "10A");
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

        persons.add(Lucas);
        persons.add(Grace);
        persons.add(Mark);
        persons.add(Vicki);

        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Vicki);

        personViewAdapter.addPerson(Lucas, LucasInfo, true);
        personViewAdapter.addPerson(Grace, GraceInfo, true);
        personViewAdapter.addPerson(Mark, MarkInfo, true);
        personViewAdapter.addPerson(Vicki, VickiInfo, true);

        List<Person> output = personViewAdapter.getPeople();
        List<String> nameList = new ArrayList<>();
        for (Person person : output) {
            nameList.add(person.getName());
        }
        String expectedOutput = "[Lucas, Grace, Mark, Vicki]";
        assertEquals(expectedOutput, nameList.toString());
    }

    // unit test
    @Test
    public void testSearchClassmatesDupNames() {
        List<Person> persons = new ArrayList<>();
        PersonsViewAdapter personViewAdapter = new PersonsViewAdapter(persons);
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

        /**commonalities
         * Course1: Rodney, Grace, Vicki, DupVicki
         * Course2: Rodney, Grace, Mark, DupVicki
         * Course3: Mark, DupVicki
         * Course4: Vicki, Lucas
         */

        String img1 = "";
        //Rodney is app user
        Person Rodney = new Person("Rodney", img1, RodneyClasses);
        Person Lucas = new Person("Lucas", img1, LucasClasses);
        Person Grace = new Person("Grace", img1, GraceClasses);
        Person Mark = new Person("Mark", img1, MarkClasses);
        Person Vicki = new Person("Vicki", img1, VickiClasses);
        Person DupVicki = new Person("Vicki", "", DupVickiClasses);

        persons.add(Lucas);
        persons.add(Grace);
        persons.add(Mark);
        persons.add(Vicki);
        persons.add(DupVicki);


        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, Vicki);
        ProfileInfo DupVickiInfo = SearchClassmates.detectAndReturnSharedClasses(Rodney, DupVicki);

        personViewAdapter.addPerson(Lucas, LucasInfo, true);
        personViewAdapter.addPerson(Grace, GraceInfo, true);
        personViewAdapter.addPerson(Mark, MarkInfo, true);
        personViewAdapter.addPerson(Vicki, VickiInfo, true);
        personViewAdapter.addPerson(DupVicki, DupVickiInfo, true);

        List<Person> output = personViewAdapter.getPeople();
        List<String> nameList = new ArrayList<>();
        for (Person person : output) {
            nameList.add(person.getName());
        }
        String expectedOutput = "[Lucas, Grace, Mark, Vicki, Vicki]";
        assertEquals(expectedOutput, nameList.toString());
    }
}
