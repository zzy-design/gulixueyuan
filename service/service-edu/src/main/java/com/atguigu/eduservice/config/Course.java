package com.atguigu.eduservice.config;

import lombok.Data;

@Data
public class Course {
    public static String COURSE_DRAFT = "Draft";
    public static String COURSE_NORMAL = "Normal";
    private String status;
}
