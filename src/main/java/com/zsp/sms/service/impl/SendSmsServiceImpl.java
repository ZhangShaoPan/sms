package com.zsp.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.zsp.sms.service.SendSmsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SendSmsServiceImpl implements SendSmsService {

    /**
     * @param phoneNum     发送的手机号
     * @param templateCode 模板
     * @param code         验证码
     * @return
     */


    @Override
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) {

        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G6ss5W85iT43KmPwcVx", "xxx");
        IAcsClient client = new DefaultAcsClient(profile);
        // 构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        // 不可以修改
        request.setSysDomain("dysmsapi.aliyuncs.com");
        // 不可以修改
        request.setSysVersion("2017-05-25");
        // 不可以修改
        request.setSysAction("SendSms");
        // 自定义的参数(手机号，验证码，签名，模板)
        request.putQueryParameter("PhoneNumbers", phoneNum);// 手机号
        request.putQueryParameter("SignName", "xxx");// (阿里云签名名称)
        request.putQueryParameter("TemplateCode", templateCode);// 模板(阿里云模版CODE)
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));// 验证码

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

}
