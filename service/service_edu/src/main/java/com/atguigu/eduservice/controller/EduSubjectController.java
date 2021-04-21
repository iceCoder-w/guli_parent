package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-19
 */

@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService eduSubjectService;

    // 添加课程分类
    @ApiOperation(value = "Excel批量导入")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file, eduSubjectService);
        return R.ok();
    }

    // 获得课程分类列表（树形实现）
    @ApiOperation(value = "获得课程分类列表")
    @PostMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

