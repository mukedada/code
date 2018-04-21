package com.circle.ood.course;

/**
 * @author KeWeiYang
 * @date 2018/2/28 下午8:50
 * 选课服务类
 */
public class CourseService {
    /**
     * 如果学生上过该科目的先修科目，并且该课程学生还没报满，则可以加入
     * @param student
     * @param courseOffering
     */
    public void chooseCourse(Student student, CourseOffering courseOffering) {
        if (student.getCoursesAlreadyTaken().containsAll(
                courseOffering.getCourse().getPreRequisites())
                && courseOffering.getMaxStudents() > courseOffering.getStudents().size()) {

            courseOffering.getStudents().add(student);
        }
    }

    /**
     * 相交于第一个版本的代码，职责更加明确
     * 封装性更好，前一个方法需要调用getxxx方法，但是这个版本就不需要了
     * 第一个版本的Student、CourseOffering只提供简单的getxxx、setxxx，只能算是bean，谈不上是对象，它们缺少行为，因为类包含属性+行为
     * 前一个版本的代码是基于对象编程，而这个版本是真正面向对象开发的
     * @param student
     * @param courseOffering
     */
    public void chooseCourseV1(Student student, CourseOffering courseOffering) {
        courseOffering.addStudent(student);
    }
}
