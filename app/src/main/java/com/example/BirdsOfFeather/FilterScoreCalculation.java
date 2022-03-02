package com.example.BirdsOfFeather;

import java.util.Date;
import java.util.List;
import java.util.Calendar;

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
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;

    public int getYear(){
        return this.year;
    }
    public String getQuarter(){
        if (month>8){
            return "Fall";
        }else if (month>6){
            return "Summer";
        }else if (month>3){
            return "Spring";
        }else return "Winter";
    }

    //String thisquarter = "Winter";

    //calculate score for one person using recent
    public int score_recent(ProfileInfo profileInfo){


        int thisyear = getYear();
        String thisquarter = getQuarter();
        int score = 0;
        List<Course> commonCourses = profileInfo.getCommonCourses();



        for (Course c:commonCourses){

            int quarterage = 0;
            String year = c.getYear();
            String quarter = c.getQuarter();
            int yeardiff = 4*(thisyear - Integer.parseInt(year));
            int quardiff = 0;

            if (thisquarter.equals("Winter")){
                if (quarter == "Winter"){quardiff = 0;}
                if (quarter == "Fall"){quardiff = -3;}
                if (quarter == "Summer Session I" || quarter == "Summer Session II" ||quarter =="Special Summer Session"){
                    quardiff = -2;
                }
                if (quarter == "Spring"){quardiff = -1;}
                quarterage = quarterage + yeardiff + quardiff;
            }

            else if (thisquarter.equals("Spring")){
                if (quarter == "Spring"){quardiff = 0;}
                if (quarter == "Winter"){quardiff = -3;}
                if (quarter == "Summer Session I" || quarter == "Summer Session II" ||quarter =="Special Summer Session"){
                    quardiff = -1;
                }
                if (quarter == "Fall"){quardiff = -2;}
                quarterage = quarterage + yeardiff + quardiff;
            }

            else if (thisquarter.equals("Summer")){
                if (quarter == "Winter"){quardiff = -2;}
                if (quarter == "Fall"){quardiff = -1;}
                if (quarter == "Summer Session I" || quarter == "Summer Session II" ||quarter =="Special Summer Session"){
                    quardiff = 0;
                }
                if (quarter == "Spring"){quardiff = -3;}
                quarterage = quarterage + yeardiff + quardiff;
            }

            else if (thisquarter.equals("Fall")){
                if (quarter == "Winter"){quardiff = -1;}
                if (quarter == "Fall"){quardiff = 0;}
                if (quarter == "Summer Session I" || quarter == "Summer Session II" ||quarter =="Special Summer Session"){
                    quardiff = -3;
                }
                if (quarter == "Spring"){quardiff = -2;}
                quarterage = quarterage + yeardiff + quardiff;
            }

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

    public float score_size(Person person, ProfileInfo profileInfo) {
        return 0;
    }
}
