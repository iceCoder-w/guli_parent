package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-05-09
 */

public interface UcenterMemberService extends IService<UcenterMember> {

    String login(LoginVo loginVo);

    void register(RegisterVo registerVo);

    // 根据openid查询是否有用户
    UcenterMember getByOpenid(String openid);
}
