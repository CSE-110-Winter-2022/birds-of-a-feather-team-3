package com.example.BirdsOfFeather;

import java.util.List;

//filter score calculation class for different type of flitters

//                Course course1 = new Course("Winter", "2020", "CSE", "101");
//                Course course2 = new Course("Spring", "2020", "CSE", "101");
//                Course course3 = new Course("Summer Session I", "2020", "CSE", "101");
//                Course course4 = new Course("Summer Session II", "2020", "CSE", "101");
//                Course course5 = new Course("Special Summer Session", "2020", "CSE", "101");
//                Course course6 = new Course("Fall", "2020", "CSE", "101");
//                Course course7 = new Course("Fall", "2021", "CSE", "101");
//                Course course8 = new Course("Winter", "2022", "CSE", "101");
//                Course course9 = new Course("Summer Session I", "2022", "CSE", "101");

public class FilterScoreCalculation {

    //claim this year's information
    String thisyear = "2022";
    //String thisquarter = "Winter";

    //calculate score for one person using recent
    public int score_recent(Person person, Person self){


        int score = 0;
        ProfileInfo profileInfo = SearchClassmates.detectAndReturnSharedClasses(self, person);
        List<Course> commonCourses = profileInfo.getCommonCourses();
        for (Course c:commonCourses){
            int quarterage = 0;
            String year = c.getYear();
            String quarter = c.getQuarter();
            int yeardiff = 4*(Integer.parseInt(thisyear) - Integer.parseInt(year));
            int quardiff = 0;
            if (quarter == "Winter"){quardiff = 0;}
            if (quarter == "Fall"){quardiff = -3;}
            if (quarter == "Summer Session I" || quarter == "Summer Session II" ||quarter =="Special Summer Session"){
                quardiff = -2;
            }
            if (quarter == "Spring"){quardiff = -1;}

            quarterage = quarterage + yeardiff + quardiff;
            if (quarterage == 0){
                score += 5;
            }else if (quarterage == 1){
                score += 4;
            }else if (quarterage == 2){
                score += 3;
            }else if (quarterage == 3){
                score += 2;
            }else {score += 1;}


        }

        return score;

    }
}
