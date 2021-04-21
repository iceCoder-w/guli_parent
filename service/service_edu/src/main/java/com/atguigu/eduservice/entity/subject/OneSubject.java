package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 王冰炜
 * @create 2021-04-21 17:03
 */

// 一级分类
@Data
public class OneSubject {
    private String id;
    private String title;
    // 一个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
