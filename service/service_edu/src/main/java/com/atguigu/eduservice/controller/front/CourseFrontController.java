package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontvo.CourseQueryVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author 王冰炜
 * @create 2021-05-23 21:07
 */


@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {

    @Autowired
    EduCourseService courseService;
    @Autowired
    EduChapterService chapterService;

    // 条件查询带分页
    @ApiOperation(value = "分页课程列表")
    @PostMapping(value = "getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "courseQuery", value = "查询对象", required = false)
            @RequestBody(required = false) CourseQueryVo courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseQuery);
        return R.ok().data(map);
    }

    // 单个课程详情
    @ApiOperation(value = "根据ID查询课程详细信息")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(
            @ApiParam(name = "courseId)", value = "课程id", required = true)
            @PathVariable String courseId) {
        // 查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        // 根据课程id查询章节和小节
        List<ChapterVo> chapterVoList = chapterService.getChapterVideoById(courseId);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVoList", chapterVoList);
    }


}
