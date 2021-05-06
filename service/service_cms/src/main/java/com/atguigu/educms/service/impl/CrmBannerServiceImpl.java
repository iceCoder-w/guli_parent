package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-05-06
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
    // 获取首页banner
    @Override
    public List<CrmBanner> selectAllList() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }
}
