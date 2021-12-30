package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.CourseInfoForm;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 张智洋
 * @since 2021-11-21
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 保存课程和课程详情信息
     * @param courseInfoForm
     * @return 新生成的课程id
     */
    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    CoursePublishVo publishCourseInfo(String id);

    void removeCourse(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //多条件查询讲师带分页
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    List<EduCourse> selectByTeacherId(String teacherId);

    Map<String, Object> pageListWeb(Page<EduCourse> pageParam, CourseQueryVo courseQuery);

    /**
     * 获取课程信息
     * @param id
     * @return
     */
    CourseWebVo selectInfoWebById(String id);

    /**
     * 更新课程浏览数
     * @param id
     */
    void updatePageViewCount(String id);
}
