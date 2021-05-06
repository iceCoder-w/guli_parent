package com.atguigu.educms.controller;


import com.atguigu.commonutils.R;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 后台banner管理接口
 * </p>
 *
 * @author 王冰炜
 * @since 2021-05-06
 */

@Api(tags = "后台管理：网站首页Banner")
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {
    @Autowired
    private CrmBannerService bannerService;

    @ApiOperation(value = "获取Banner分页列表")
    @GetMapping("pageBanner/{current}/{limit}")
    public R pageBanner(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {

        System.out.println("page:"+current+", limit:"+limit);

        Page<CrmBanner> pageBanner = new Page<>(current, limit);
        bannerService.page(pageBanner, null);

        List<CrmBanner> items = pageBanner.getRecords();
        long total = pageBanner.getTotal();
        System.out.println("total:"+total);
        return R.ok().data("items", items).data("total", total);
    }

    @ApiOperation(value = "新增Banner")
    @PostMapping("add")
    public R add(@RequestBody CrmBanner banner) {
        bannerService.save(banner);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PutMapping("update")
    public R updateById(@RequestBody CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "获取Banner")
    @GetMapping("get/{id}")
    public R get(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

}

