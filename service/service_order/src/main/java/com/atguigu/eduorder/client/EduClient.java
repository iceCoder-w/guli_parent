package com.atguigu.eduorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王冰炜
 * @create 2021-06-16 13:35
 */

@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public com.atguigu.commonutils.vo.CourseWebVoOrder getCourseInfoDto(@PathVariable("courseId") String courseId);
}
