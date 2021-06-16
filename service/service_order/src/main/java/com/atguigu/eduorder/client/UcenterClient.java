package com.atguigu.eduorder.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 王冰炜
 * @create 2021-06-16 13:37
 */

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //根据课程id查询课程信息
    @PostMapping("/educenter/member/getUserInfoOrder/{id}")
    public com.atguigu.commonutils.vo.UcenterMemberOrder getInfo(@PathVariable("id") String id);
}