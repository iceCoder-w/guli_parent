package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-04-22
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    // 注入VodClient
    @Autowired
    private VodClient vodClient;

    // 根据courseId删除小节
    @Override
    public boolean removeVideoByCourseId(String courseId) {
        // 根据课程id查询所有的video小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id"); // 只查询这一列
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        // 得到所有视频列表的云端原始视频id
        // List<EduVideo> 变成 List<String>
        List<String> videoSourceIdList = new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo video = eduVideoList.get(i);
            String videoSourceId = video.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)){
                videoSourceIdList.add(videoSourceId);
            }
        }

        // 调用vod服务删除多个远程视频
        if(videoSourceIdList.size() > 0){
            vodClient.removeVideoList(videoSourceIdList);
        }

        QueryWrapper<EduVideo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Integer count = baseMapper.delete(queryWrapper);
        return null != count && count > 0;
    }
}
