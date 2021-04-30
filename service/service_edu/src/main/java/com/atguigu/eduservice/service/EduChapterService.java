package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-22
 */
public interface EduChapterService extends IService<EduChapter> {

    // 获取课程章节列表（根据课程id查询）
    List<ChapterVo> getChapterVideoById(String courseId);
    // 删除章节(删除章节时，需要跟着删除小节)
    boolean deleteChapter(String chapterId);

    // 根据courseId删除章节
    boolean removeChapterByCourseId(String courseId);
}
