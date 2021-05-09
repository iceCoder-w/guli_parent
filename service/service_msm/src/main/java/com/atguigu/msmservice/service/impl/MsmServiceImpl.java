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
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author 王冰炜
 * @create 2021-05-08 19:44
 */

@Service
public class MsmServiceImpl implements MsmService {
    @Value("${tencent.SecretId}")
    private String SecretId;
    @Value("${tencent.SecretKey}")
    private String SecretKey;

    // 发送短信
    @Override
    public boolean send(Map<String, Object> param, String PhoneNumbers) {
        if(StringUtils.isEmpty(PhoneNumbers)) return false;
            Credential cred = new Credential(SecretId, SecretKey);
            // 实例化一个 http 选项，可选，无特殊需求时可以跳过
            HttpProfile httpProfile = new HttpProfile();
            // 设置代理
            // httpProfile.setProxyHost("host");
            // httpProfile.setProxyPort();

            httpProfile.setReqMethod("POST");
            // SDK 默认的超时时间，非必要请不要进行调整
            httpProfile.setConnTimeout(60);
            /* SDK 会自动指定域名，通常无需指定域名，但访问金融区的服务时必须手动指定域名
             * 例如 SMS 的上海金融区域名为 sms.ap-shanghai-fsi.tencentcloudapi.com */
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            /* 非必要步骤:
             * 实例化一个客户端配置对象，可以指定超时时间等配置 */
            ClientProfile clientProfile = new ClientProfile();
            /* SDK 默认用 TC3-HMAC-SHA256 进行签名
             * 非必要请不要修改该字段 */
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);
            /* 实例化 SMS 的 client 对象
             * 第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量 */
            SmsClient client = new SmsClient(cred, "",clientProfile);
            /* 实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
             * 您可以直接查询 SDK 源码确定接口有哪些属性可以设置
             * 属性可能是基本类型，也可能引用了另一个数据结构
             * 推荐使用 IDE 进行开发，可以方便地跳转查阅各个接口和数据结构的文档说明 */
            SendSmsRequest req = new SendSmsRequest();
            /* 填充请求参数，这里 request 对象的成员变量即对应接口的入参
             * 您可以通过官网接口文档或跳转到 request 对象的定义处查看请求参数的定义
             * 基本类型的设置:
             * 帮助链接：
             * 短信控制台：https://console.cloud.tencent.com/smsv2
             * sms helper：https://cloud.tencent.com/document/product/382/3773 */
            /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
            String appid = "1400518901";
            req.setSmsSdkAppid(appid);
            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 */
            String sign = "不断寻找";
            req.setSign(sign);
            /* 国际/港澳台短信 senderid: 国内短信填空，默认未开通，如需开通请联系 [sms helper] */
            req.setSenderId(null);
            /* 用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回 */
            String session = "用户的 session 内容: 可以携带用户侧 ID 等上下文信息，server 会原样返回";
            req.setSessionContext(session);
            /* 短信码号扩展号: 默认未开通，如需开通请联系 [sms helper] */
//            String extendcode = "xxx";
//            req.setExtendCode(extendcode);
            /* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
            String templateID = "951989";
            req.setTemplateID(templateID);
            /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 例如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
            String number = "+86" +PhoneNumbers;
            String[] phoneNumbers = {number};
            req.setPhoneNumberSet(phoneNumbers);
            /* 模板参数: 若无模板参数，则设置为空*/
            String[] templateParams = {param.get("code").toString(),"5"};
            req.setTemplateParamSet(templateParams);

        try {
            // 最终发送
            SendSmsResponse res = client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));
            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());
            return SendSmsResponse.toJsonString(res).contains("Ok");
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
//        DefaultProfile profile =
//                DefaultProfile.getProfile("default", "${aliyun.msm.keyid}", "${aliyun.msm.keysecret}");
//        IAcsClient client = new DefaultAcsClient(profile);
//
//        // 设置相关的固定参数
//        CommonRequest request = new CommonRequest();
//        //request.setProtocol(ProtocolType.HTTPS);
//        request.setMethod(MethodType.POST);
//        request.setDomain("dysmsapi.aliyuncs.com");
//        request.setVersion("2017-05-25");
//        request.setAction("SendSms");
//
//        // 设置发送相关的参数
//        request.putQueryParameter("PhoneNumbers", PhoneNumbers);
//        request.putQueryParameter("SignName", "${aliyun.msm.signname}"); // 签名
//        request.putQueryParameter("TemplateCode", "${aliyun.msm.templatecode}"); // 模板code
//        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
//
//        try {
//            // 最终发送
//            CommonResponse response = client.getCommonResponse(request);
//            System.out.println(response.getData());
//            return response.getHttpResponse().isSuccess();
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        return false;
    }
}
