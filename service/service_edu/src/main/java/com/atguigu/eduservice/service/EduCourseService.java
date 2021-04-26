package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
