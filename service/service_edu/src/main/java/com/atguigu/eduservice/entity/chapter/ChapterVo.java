package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王冰炜
 * @create 2021-04-25 19:21
 */

@Data
public class ChapterVo {
    private String id;
    private String title;
    // 章节中的小节
    private List<VideoVo> children = new ArrayList<>();
}
