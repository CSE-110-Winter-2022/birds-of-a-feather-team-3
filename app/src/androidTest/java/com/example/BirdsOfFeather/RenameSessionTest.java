package com.example.BirdsOfFeather;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.BirdsOfFeather.database.AppDatabase;
import com.example.BirdsOfFeather.database.ProfileEntity;
import com.example.BirdsOfFeather.database.SessionEntity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class RenameSessionTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.BirdsOfFeather", appContext.getPackageName());
    }

    @Test
    public void testInsertSessionAndProfiles(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        AppDatabase db = AppDatabase.singleton(appContext);

        SessionEntity newSessionEntity = new SessionEntity("MyFirstSession");
        long generatedId =  db.sessionDao().insert(newSessionEntity);
        db.sessionDao().getSession(generatedId).id = generatedId;

        List<ProfileInfo> persons = new ArrayList<>();

        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course3 = new Course("Winter", "2020", "Medium (75-150)","CSE", "101");
        Course course4 = new Course("Fall", "2020", "Tiny (<40)","WCWP", "10A");

        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));

        ProfileEntity Lucas = new ProfileEntity("Lucas", "", generatedId, LucasClasses, "abc", false);
        ProfileEntity Grace = new ProfileEntity("Grace", "", generatedId, GraceClasses, "def", false);
        ProfileEntity Mark = new ProfileEntity("Mark", "", generatedId, MarkClasses, "ghi", false);
        ProfileEntity Vicki = new ProfileEntity("Vicki", "", generatedId, VickiClasses, "jkl", false);
        ProfileEntity Rodney = new ProfileEntity("Rodney", "", generatedId, RodneyClasses, "mno", false);

        db.profilesDao().insert(Lucas);
        db.profilesDao().insert(Grace);
        db.profilesDao().insert(Mark);
        db.profilesDao().insert(Vicki);
        db.profilesDao().insert(Rodney);


        assertEquals(1,db.sessionDao().getAll().size());
        assertEquals(5,db.profilesDao().getForSession(generatedId).size());
        assertEquals(1,db.sessionWithProfilesDao().count());

    }

    @Test
    public void testRenameSession(){
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        AppDatabase.useTestSingleton(appContext);
        AppDatabase db = AppDatabase.singleton(appContext);

        SessionEntity newSessionEntity = new SessionEntity("incorrect");
        long generatedId =  db.sessionDao().insert(newSessionEntity);
        db.sessionDao().getSession(generatedId).id = generatedId;

        List<ProfileInfo> persons = new ArrayList<>();

        Course course1 = new Course("Spring", "2020", "Large (150-250)","CSE", "110");
        Course course2 = new Course("Fall", "2020", "Large (150-250)","CSE", "100");
        Course course3 = new Course("Winter", "2020", "Medium (75-150)","CSE", "101");
        Course course4 = new Course("Fall", "2020", "Tiny (<40)","WCWP", "10A");

        List<Course> RodneyClasses = new ArrayList<>(Arrays.asList(course1, course2));
        List<Course> LucasClasses = new ArrayList<>(Arrays.asList(course4));
        List<Course> GraceClasses = new ArrayList<>(Arrays.asList(course1, course2, course3));
        List<Course> MarkClasses = new ArrayList<>(Arrays.asList(course3, course2));
        List<Course> VickiClasses = new ArrayList<>(Arrays.asList(course1, course4));

        ProfileEntity Lucas = new ProfileEntity("Lucas", "", generatedId, RodneyClasses, "abc", false);
        ProfileEntity Grace = new ProfileEntity("Grace", "", generatedId, RodneyClasses, "def", false);
        ProfileEntity Mark = new ProfileEntity("Mark", "", generatedId, RodneyClasses, "ghi", false);
        ProfileEntity Vicki = new ProfileEntity("Vicki", "", generatedId, RodneyClasses, "jkl", false);

        db.profilesDao().insert(Lucas);
        db.profilesDao().insert(Grace);
        db.profilesDao().insert(Mark);
        db.profilesDao().insert(Vicki);

        assertEquals("incorrect", db.sessionDao().getSession(generatedId).getName().toString());

        db.sessionDao().update("correct", generatedId);

        assertEquals("correct", db.sessionDao().getSession(generatedId).getName().toString());



    }
}
