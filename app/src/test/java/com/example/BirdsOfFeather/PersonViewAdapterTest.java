package com.example.BirdsOfFeather;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PersonViewAdapterTest {
    @Test
    public void properMatchAmount() {
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

        /***commonalities
         * Course1: Rodney, Grace, Vicki, DupVicki
         * Course2: Rodney, Grace, Mark, DupVicki
         * Course3: Mark
         * Course4: Vicki, Lucas
         */

        Person Rodney = new Person("Rodney", "", RodneyClasses);
        Person Lucas = new Person("Lucas", "", LucasClasses);
        Person Grace = new Person("Grace", "", GraceClasses);
        Person Mark = new Person("Mark", "", MarkClasses);
        Person Vicki = new Person("Vicki", "", VickiClasses);
        Person DupVicki = new Person("Vicki", "", DupVickiClasses);
        List<Course> selfClasses = new ArrayList<>(Arrays.asList(course1, course2, course3, course4));

        Person self = new Person("Self", "", selfClasses);

        ProfileInfo RodneyInfo = SearchClassmates.detectAndReturnSharedClasses(self, Rodney);
        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(self, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(self, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(self, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(self, Vicki);
        ProfileInfo DupVickiInfo = SearchClassmates.detectAndReturnSharedClasses(self, DupVicki);

        assertEquals(2, RodneyInfo.getCommonCourses().size());
        assertEquals(1, LucasInfo.getCommonCourses().size());
        assertEquals(3, GraceInfo.getCommonCourses().size());
        assertEquals(2, MarkInfo.getCommonCourses().size());
        assertEquals(2, VickiInfo.getCommonCourses().size());
        assertEquals(3, DupVickiInfo.getCommonCourses().size());
    }
    @Test
    public void orderedByMatchAmount() {
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

        /***commonalities
         * Course1: Rodney, Grace, Vicki, DupVicki
         * Course2: Rodney, Grace, Mark, DupVicki
         * Course3: Mark
         * Course4: Vicki, Lucas
         */

        Person Rodney = new Person("Rodney", "", RodneyClasses);
        Person Lucas = new Person("Lucas", "", LucasClasses);
        Person Grace = new Person("Grace", "", GraceClasses);
        Person Mark = new Person("Mark", "", MarkClasses);
        Person Vicki = new Person("Vicki", "", VickiClasses);
        Person DupVicki = new Person("Vicki", "", DupVickiClasses);
        List<Course> selfClasses = new ArrayList<>(Arrays.asList(course1, course2, course3, course4));

        Person self = new Person("Self", "", selfClasses);

        ProfileInfo RodneyInfo = SearchClassmates.detectAndReturnSharedClasses(self, Rodney);
        ProfileInfo LucasInfo = SearchClassmates.detectAndReturnSharedClasses(self, Lucas);
        ProfileInfo GraceInfo = SearchClassmates.detectAndReturnSharedClasses(self, Grace);
        ProfileInfo MarkInfo = SearchClassmates.detectAndReturnSharedClasses(self, Mark);
        ProfileInfo VickiInfo = SearchClassmates.detectAndReturnSharedClasses(self, Vicki);
        ProfileInfo DupVickiInfo = SearchClassmates.detectAndReturnSharedClasses(self, DupVicki);
        personViewAdapter.addPerson(Rodney, RodneyInfo, true);
        personViewAdapter.addPerson(Lucas, LucasInfo, true);
        personViewAdapter.addPerson(Grace, GraceInfo, true);
        personViewAdapter.addPerson(Mark, MarkInfo, true);
        personViewAdapter.addPerson(Vicki, VickiInfo, true);
        personViewAdapter.addPerson(DupVicki, DupVickiInfo, true);
        List<String> expectedArray = new ArrayList<>(Arrays.asList(
                "Vicki: 3",
                "Grace: 3",
                "Vicki: 2",
                "Mark: 2",
                "Rodney: 2",
                "Lucas: 1"
        ));
        //sorted despite inputted in wrong order.
        List<Person> sortedPeopleList = personViewAdapter.getPeople();
        Map<Person, ProfileInfo>  sortedPeopleMap = personViewAdapter.getPeopleInfoMap();

        List<String> toStringPeopleList = new ArrayList<>();

        for (Person person : sortedPeopleList) {
            toStringPeopleList.add(person.getName() + ": " + sortedPeopleMap.get(person).getCommonCourses().size());
        }

        assertEquals(expectedArray.toString(), toStringPeopleList.toString());
    }
}