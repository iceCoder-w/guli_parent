package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseQueryVo;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-22
 */
public interface EduCourseService extends IService<EduCourse> {
    // 添加课程基本信息
    String saveCourseInfo(CourseInfoVo courseInfoVo);
    // 根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);
    // 修改课程基本信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    // 根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String courseId);

    // 分页查询课程列表
    void pageQuery(Page<EduCourse> pageParam, CourseQuery courseQuery);

    // 根据ID删除课程
    boolean removeCourseById(String id);


    // 根据讲师id查询讲师所讲课程列表
    List<EduCourse> selectByTeacherId(String teacherId);

    // 条件查询带分页查询课程
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseQueryVo courseQuery);

    // 查询课程信息和讲师信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
