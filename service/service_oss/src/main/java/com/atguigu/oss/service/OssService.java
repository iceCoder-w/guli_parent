package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王冰炜
 * @create 2021-04-16 17:43
 */
public interface OssService {
    // 上传头像到OSS
    String uploadFileAvatar(MultipartFile file);
}
