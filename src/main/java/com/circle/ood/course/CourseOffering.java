package com.circle.ood.course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KeWeiYang
 * @date 2018/2/28 下午8:47
 * 课程类
 */
public class CourseOffering {
    private Course course;
    private String location;
    private String teacher;
    private int maxStudents;

    List<Student> students = new ArrayList<Student>();

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(int maxStudents) {
        this.maxStudents = maxStudents;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        if (student.canAttend(course) && isFull(students.size(), maxStudents)) {
            students.add(student);
        }
    }

    public boolean isFull(int currentNum, int maxStudents) {
        return currentNum < maxStudents;
    }
}
