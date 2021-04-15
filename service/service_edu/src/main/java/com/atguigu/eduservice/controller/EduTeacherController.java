package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 王冰炜
 * @since 2021-03-28
 */

@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
@CrossOrigin //解决跨域
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    //获取所有老师
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除老师
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("deleteByID/{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //分页查询
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable int current,

            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable int limit){

        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //模拟一个全局异常
//        int i = 10/0;

        //调用方法实现分页
        eduTeacherService.page(pageTeacher, null);

        //总记录数和list集合
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("totalPage",total).data("items",records);
    }


    //条件查询，分页
    @ApiOperation(value = "条件查询，分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,

            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,

            @RequestBody(required = false)
            TeacherQuery teacherQuery
    ){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态SQL
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if(!ObjectUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!ObjectUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if(!ObjectUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!ObjectUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //按照添加时间排序
        wrapper.orderByDesc("gmt_create");

        //调用方法查询分页
        eduTeacherService.page(pageTeacher,wrapper);
        //返回结果
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();

        return R.ok().data("totalPage",total).data("items",records);
    }


    //添加讲师
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "EduTeacher",value = "讲师对象",required = true)
            @RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //根据ID查询讲师
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return null != eduTeacher ? R.ok().data("teacher",eduTeacher) : R.error().data("msg","该id对应的讲师不存在！");
    }

    //修改讲师信息
    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateTeacher/{id}")
    public R updateTeacher(
            @ApiParam(name = "id",value = "讲师id",required = true)
            @PathVariable String id,

            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher){
        teacher.setId(id);
        boolean flag = eduTeacherService.updateById(teacher);
        return flag ? R.ok() : R.error();
    }

    //测试自定义异常
    @ApiOperation(value = "自定义异常")
    @GetMapping("testGuliException")
    public R testGuliException(){
        try {
            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(20001,"出现自定义异常");
        }
        return R.ok().message("测试自定义异常");
    }
}

