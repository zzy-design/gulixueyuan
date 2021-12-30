package com.atguigu.orderwxservice.client;

import com.atguigu.commonutils.vo.CourseInfoForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @GetMapping("/eduService/course/getDto/{courseId}")
    CourseInfoForm getCourseInfoDto(@PathVariable("courseId") String courseId);
}
