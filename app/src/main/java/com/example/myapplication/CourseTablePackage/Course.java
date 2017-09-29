package com.example.myapplication.CourseTablePackage;

import java.io.Serializable;

/**
 * Created by aaa on 2017/9/29.
 */

public class Course implements Serializable {
    private String courseName;//课程名称
    private String courseWeek;//课程周数
    private String coursePlace;//课程地点
    private String courseTeacher;//课程教师

    public Course(String courseName, String courseWeek,
                      String coursePlace, String courseTeacher) {
        super();
        this.courseName = courseName;
        this.courseWeek = courseWeek;
        this.coursePlace = coursePlace;
        this.courseTeacher = courseTeacher;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseWeek(String courseWeek) {
        this.courseWeek = courseWeek;
    }

    public String getCourseWeek() {
        return this.courseWeek;
    }

    public void setCoursePlace(String coursePlace) {
        this.coursePlace = coursePlace;
    }

    public String getCoursePlace() {
        return this.coursePlace;
    }

    public void setCourseTeacher(String courseTeacher) {
        this.courseTeacher = courseTeacher;
    }

    public String getCourseTeacher() {
        return this.courseTeacher;
    }

    @Override
    public String toString() {
        return courseName + "\n" + coursePlace + "\n" + courseTeacher;
    }
}
