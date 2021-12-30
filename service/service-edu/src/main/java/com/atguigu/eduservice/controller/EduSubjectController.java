package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-02-29
 */
@RestController
@Api(description = "课程分类管理", tags = {"课程分类管理"})
@RequestMapping("/eduService/subject")
//@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
    @ApiOperation(value = "添加课程分类")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file) {
        //上传过来excel文件
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @ApiOperation(value = "获取课程分类列表（树形）")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("items", list);
    }

    //课程分类列表（树形）
    @ApiOperation(value = "获取课程分类列表（树形）")
    @PostMapping("getSubjectById/{id}")
    public R getSubject(@PathVariable String id) {
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> hashMap = new HashMap<>();
        //list集合泛型是一级分类
        QueryWrapper<EduSubject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", id);
        EduSubject subject = subjectService.getOne(queryWrapper1);
        //list集合泛型是二级分类
        QueryWrapper<EduSubject> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("parentId", subject.getParentId());
        return R.ok().data("items", hashMap);
    }
}

