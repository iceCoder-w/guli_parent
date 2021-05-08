package com.atguigu.msmservice.service;

import java.util.Map;

/**
 * @author 王冰炜
 * @create 2021-05-08 19:44
 */

public interface MsmService {
    // 发送短信
    boolean send(Map<String, Object> param, String phone);
}
