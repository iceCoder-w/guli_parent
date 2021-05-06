package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-05-06
 */
public interface CrmBannerService extends IService<CrmBanner> {
    // 获取首页banner
    List<CrmBanner> selectIndexList();
}
