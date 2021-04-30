package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-22
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    // 获取课程章节列表（根据课程id查询）
    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {
        // 1.根据id查chapter章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        wrapperChapter.orderByAsc("sort","title"); //或id
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2.根据id查video小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.orderByAsc("sort","title"); //或id
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // 创建最终封装数据的list集合
        List<ChapterVo> finalList = new ArrayList<>();

        // 3.遍历章节list进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            // 4.遍历小节list进行封装
            ArrayList<VideoVo> videoList = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                EduVideo eduVideo = eduVideoList.get(j);
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoList.add(videoVo);
                }
            }
            // 把封装之后小节list集合，放到章节对象里
            chapterVo.setChildren(videoList);

            finalList.add(chapterVo);
        }

        return finalList;
    }

    // 删除章节(删除章节时，需要跟着删除小节)
    @Override
    public boolean deleteChapter(String chapterId) {
        // 根据章节chapterId查询小节表，若查到数据则不能删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        // count为能查出几条记录
        int count = videoService.count(wrapper);
        if (count > 0) {
            throw new GuliException(20001,"该章节含有小节，不能删除！");
        }else { // 未查到小节数据，可以删除该章节
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }
    }

    // 根据courseId删除章节
    @Override
    public boolean removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }
}
