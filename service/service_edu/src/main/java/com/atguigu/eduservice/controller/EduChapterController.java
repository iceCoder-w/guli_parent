package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-22
 */

@Api(tags = "章节管理")
@RestController
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    // 获得课程大纲列表
    @ApiOperation(value = "所有章节列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoById(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     * @ApiOperation(value = "所有讲师列表")
     *     @GetMapping("findAll")
     *     public R findAllTeacher(){
     *         List<EduTeacher> list = eduTeacherService.list(null);
     *         return R.ok().data("items",list);
     *     }
     */

}

