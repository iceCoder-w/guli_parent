package com.atguigu.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 王冰炜
 * @create 2021-04-20 7:48
 */

@Data
public class SubjectData {
    @ExcelProperty(index = 0) // index = 0 对应excel表中第一列
    private String oneSubjectName;

    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
