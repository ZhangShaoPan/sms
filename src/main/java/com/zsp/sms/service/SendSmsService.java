package com.zsp.sms.service;

import org.springframework.stereotype.Service;

import java.util.Map;

public interface SendSmsService {


    public boolean send(String phoneNum, String TemplateCode, Map<String, Object> code);


}
