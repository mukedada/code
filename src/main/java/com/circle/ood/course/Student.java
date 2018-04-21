package com.circle.ood.course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KeWeiYang
 * @date 2018/2/28 下午8:45
 * 学生类
 */
public class Student {

    private int id;
    private String name;
    private List<Course> coursesAlreadyTaken = new ArrayList<Course>();

    public List<Course> getCoursesAlreadyTaken() {
        return this.coursesAlreadyTaken;
    }

    public boolean canAttend(Course course) {
        return this.coursesAlreadyTaken.containsAll(
                course.getPreRequisites());
    }
}
