package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 王冰炜
 * @create 2021-05-08 19:44
 */

@Service
public class MsmServiceImpl implements MsmService {
    // 发送短信
    @Override
    public boolean send(Map<String, Object> param, String PhoneNumbers) {
        if(StringUtils.isEmpty(PhoneNumbers)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "${aliyun.msm.keyid}", "${aliyun.msm.keysecret}");
        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关的固定参数
        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        // 设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
        request.putQueryParameter("SignName", "${aliyun.msm.signname}"); // 签名
        request.putQueryParameter("TemplateCode", "${aliyun.msm.templatecode}"); // 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            // 最终发送
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }
}
