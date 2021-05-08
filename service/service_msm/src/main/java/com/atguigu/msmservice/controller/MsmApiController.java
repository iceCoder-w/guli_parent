package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王冰炜
 * @create 2021-05-08 19:43
 */

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    // 发送短信
    @GetMapping("send/{phone}")
    public R code(@PathVariable String phone){
        String code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code",code);

        boolean isSend = msmService.send(param,phone);
        if (isSend) {
            return R.ok();
        } else {
            return R.error().message("短信发送失败！");
        }
    }
}
