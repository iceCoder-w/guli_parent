package com.atguigu.vod.service.impl;

import com.atguigu.vod.service.VodService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王冰炜
 * @create 2021-05-02 17:32
 */

@Service
public class VodServiceImpl implements VodService {
    // 上传视频到阿里云
    @Override
    public String uploadVideoAly(MultipartFile file) {
        return null;
    }
}
