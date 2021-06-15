package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduReport;
import com.atguigu.eduservice.entity.vo.ReportQuery;
import com.atguigu.eduservice.service.EduReportService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 周报 前端控制器
 * </p>
 *
 * @author 王冰炜
 * @since 2021-06-15
 */

@Api(tags = "周报管理")
@RestController
@RequestMapping("/eduservice/edu-report")
@CrossOrigin //解决跨域
public class EduReportController {
    @Autowired
    EduReportService reportService;

    //添加周报
    @ApiOperation(value = "添加周报")
    @PostMapping("addReport")
    public R addReport(
            @ApiParam(name = "EduReport",value = "周报对象",required = true)
            @RequestBody EduReport eduReport){
        boolean save = reportService.save(eduReport);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //逻辑删除周报
    @ApiOperation(value = "根据ID删除周报")
    @DeleteMapping("removeById/{id}")
    public R removeReport(
            @ApiParam(name = "id", value = "周报ID", required = true)
            @PathVariable String id){
        boolean flag = reportService.removeById(id);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //修改周报信息
    @ApiOperation(value = "修改讲师信息")
    @PostMapping("updateReport/{id}")
    public R updateReport(
            @ApiParam(name = "id",value = "周报id",required = true)
            @PathVariable String id,

            @ApiParam(name = "report", value = "周报对象", required = true)
            @RequestBody EduReport eduReport){
        eduReport.setId(id);
        boolean flag = reportService.updateById(eduReport);
        return flag ? R.ok() : R.error();
    }

    //根据ID查询周报
    @ApiOperation(value = "根据ID查询周报")
    @GetMapping("getReport/{id}")
    public R getReport(
            @ApiParam(name = "id",value = "周报id",required = true)
            @PathVariable String id){
        EduReport eduReport = reportService.getById(id);
        return null != eduReport ? R.ok().data("report",eduReport) : R.error().data("msg","该id对应的周报不存在！");
    }

    //条件查询，分页
    @ApiOperation(value = "条件查询，分页")
    @PostMapping("pageReportCondition/{current}/{limit}")
    public R pageReportCondition(
            @ApiParam(name = "current",value = "当前页",required = true)
            @PathVariable long current,

            @ApiParam(name = "limit",value = "每页记录数",required = true)
            @PathVariable long limit,

            @RequestBody(required = false)
                    ReportQuery reportQuery
    ){

        //创建page对象
        Page<EduReport> pageReport = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduReport> wrapper = new QueryWrapper<>();

        //多条件组合查询，动态SQL
        String name = reportQuery.getName();
        String begin = reportQuery.getBegin();
        String end = reportQuery.getEnd();

        if(!ObjectUtils.isEmpty(name)){
            wrapper.like("user_name",name);
        }
        if(!ObjectUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!ObjectUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //按照添加时间排序
        wrapper.orderByDesc("gmt_create");

        //调用方法查询分页
        reportService.page(pageReport,wrapper);
        //返回结果
        long total = pageReport.getTotal();
        List<EduReport> records = pageReport.getRecords();

        return R.ok().data("totalPage",total).data("items",records);
    }

}

