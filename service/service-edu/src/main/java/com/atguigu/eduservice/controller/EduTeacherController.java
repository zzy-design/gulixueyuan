package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 张智洋
 * @since 2021-10-20
 */
@Api(description = "讲师管理", tags = {"讲师管理"})
@RestController
//@CrossOrigin //跨域
@RequestMapping("/eduService/edu-teacher")
public class EduTeacherController {
    public static final Logger logger = LoggerFactory.getLogger(EduTeacherController.class);

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "获取所有讲师列表", notes = "注意问题点")
    @GetMapping("findAll")
    public R list() {
        List<EduTeacher> eduTeacherList = teacherService.list(null);
        return R.ok().data("total", eduTeacherList.size()).data("rows", eduTeacherList);
    }

    @ApiOperation(value = "根据ID删除讲师(逻辑删除)")
    @DeleteMapping("delete/{id}")
    public R deleteById(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        return teacherService.removeById(id) ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID查找讲师")
    @GetMapping("get/{id}")
    public R get(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id) {
        return R.ok().data("items", teacherService.getById(id));
    }

    @ApiOperation(value = "分页讲师列表")
    @PostMapping("{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false) @RequestBody(required = false) TeacherQuery teacherQuery) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        teacherService.pageQuery(pageParam, teacherQuery);
        List<EduTeacher> records = pageParam.getRecords();
        logger.info(records.toString());
        long total = pageParam.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("/addEduTeacher")//作为json数据格式传输不能加到Mapping里
    public R save(
            @ApiParam(name = "eduTeacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {
        return teacherService.save(eduTeacher) ? R.ok() : R.error();
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PutMapping("update/{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,

            @ApiParam(name = "eduTeacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher) {

        eduTeacher.setId(id);
        teacherService.updateById(eduTeacher);
        return R.ok();
    }

    @ApiOperation(value = "分页讲师列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        Page<EduTeacher> pageParam = new Page<EduTeacher>(page, limit);

        Map<String, Object> map = teacherService.pageListWeb(pageParam);

        return R.ok().data(map);
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping(value = "{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        //查询讲师信息
        EduTeacher teacher = teacherService.getById(id);

        //根据讲师id查询这个讲师的课程列表
        List<EduCourse> courseList = courseService.selectByTeacherId(id);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }
}

