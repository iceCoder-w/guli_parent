package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

/**
 * @author 王冰炜
 * @create 2021-04-25 19:22
 */

@Data
public class VideoVo {
    private String id;
    private String title;
    private String videoSourceId; // 阿里云里的视频id
}
