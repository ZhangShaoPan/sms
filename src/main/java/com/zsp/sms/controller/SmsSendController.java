package com.zsp.sms.controller;

import com.aliyuncs.utils.StringUtils;
import com.zsp.sms.service.SendSmsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class SmsSendController {
    @Resource
    private SendSmsService sendSmsService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


    @GetMapping("/send/{phone}")
    public String code(@PathVariable("phone") String phone) {
        // 判断redis是否存在该手机号验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return "还没有过期";
        }
        // 生成验证码
        code = UUID.randomUUID().toString().substring(0, 4);
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);

        // 发送验证码
        boolean isSend = sendSmsService.send(phone, "SMS_192530864", map);
        if (isSend) {
            // 发送成功 存储到redis
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return "发送成功";
        }
        return "发送失败";
    }


}
