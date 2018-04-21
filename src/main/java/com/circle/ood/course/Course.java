package com.circle.ood.course;

import java.util.ArrayList;
import java.util.List;

/**
 * @author keweiyang
 * @date 2018/2/28 下午7:51
 * 科目类
 */
public class Course {

    private int id;
    private String des;
    /**
     * 科目时长
     */
    private int duration;

    /**
     * 先修科目
     */
    private List<Course> preRequisites;

    public List<Course> getPreRequisites() {
        return preRequisites;
    }



}
