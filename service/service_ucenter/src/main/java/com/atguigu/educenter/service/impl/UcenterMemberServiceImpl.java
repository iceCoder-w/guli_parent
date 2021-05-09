package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.LoginVo;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 王冰炜
 * @since 2021-05-09
 */

@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 校验参数
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"输入信息不全，请完整输入！");
        }

        // 获取会员
        UcenterMember member = baseMapper.selectOne(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if (null == member){
            throw new GuliException(20001,"该手机号未注册，请先注册再登录！");
        }

        // 验证密码
        if(!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(20001,"密码错误！请重新输入密码");
        }

        //校验是否被禁用
        if(member.getIsDisabled()) {
            throw new GuliException(20001,"您的账户已锁定，请联系管理员！");
        }

        //使用JWT生成token字符串
        return JwtUtils.getJwtToken(member.getId(), member.getNickname());
    }

    @Override
    public void register(RegisterVo registerVo) {
        //获取注册信息，进行校验
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if(StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(password) ||
                StringUtils.isEmpty(code) ||
                StringUtils.isEmpty(nickname)) {
            throw new GuliException(20001,"注册信息不全，请完整输入！");
        }

        //校验校验验证码
        //从redis获取发送的验证码
        String mobileCode = redisTemplate.opsForValue().get(mobile);
        if(!code.equals(mobileCode)) {
            throw new GuliException(20001,"该验证码无效，请重新获取验证码！");
        }

        //查询数据库中是否存在相同的手机号码
        Integer count = baseMapper.selectCount(new QueryWrapper<UcenterMember>().eq("mobile", mobile));
        if(count > 0) {
            throw new GuliException(20001,"该手机注册，请直接登录或换一个手机号码注册！");
        }

        //添加注册信息到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setIsDisabled(false);
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        baseMapper.insert(member);
    }
}
