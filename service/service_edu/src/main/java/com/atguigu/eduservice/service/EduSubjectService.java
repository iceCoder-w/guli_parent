package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-19
 */
public interface EduSubjectService extends IService<EduSubject> {
    // 添加课程分类
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    // 获得课程分类列表（树形实现）
    List<OneSubject> getAllOneTwoSubject();
}
