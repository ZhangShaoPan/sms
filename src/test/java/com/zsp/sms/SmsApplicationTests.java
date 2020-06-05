package com.zsp.sms;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SmsApplicationTests {

    @Test
    void contextLoads() {
        // 连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "<accessKeyId>", "<accessSecret>");
        IAcsClient client = new DefaultAcsClient(profile);
        // 构建请求
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        // 不可以修改
        request.setSysDomain("dysmsapi.aliyuncs.com");
        // 不可以修改
        request.setSysVersion("2017-05-25");
        request.setSysAction("AddSmsSign");
        // 自定义的参数(手机号，验证码，签名，模板)
        request.putQueryParameter("PhoneNumbers", "");// 手机号
        request.putQueryParameter("SignName", "小怪兽科技");// 签名
        request.putQueryParameter("TemplateCode","SMS_192530864");// 模板

        Map map= new HashMap<>();
        map.put("code",1111);

        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }



    }

}
