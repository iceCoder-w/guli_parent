package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 王冰炜
 * @create 2021-04-16 17:43
 */

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    // 上传头像
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        // 获取上传的文件

        // 获取文件上传到OSS的路径
        String url = ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
