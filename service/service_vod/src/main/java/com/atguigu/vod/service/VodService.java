package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王冰炜
 * @create 2021-05-02 17:30
 */

public interface VodService {
    // 上传视频到阿里云
    String uploadVideoAly(MultipartFile file);
    // 根据视频id删除视频
    void removeVideo(String videoId);
}
