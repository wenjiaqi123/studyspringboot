package com.gsm.controller;

import com.gsm.model.SmsCode;
import com.gsm.service.SmsCodeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 手机短信验证码
 */
@RestController
@RequestMapping("smsCode")
public class SmsCodeController {
    @Autowired
    private SmsCodeService smsCodeService;

    @ApiOperation("根据手机号和手机验证码判断是否手机号本人使用")
    @PostMapping("checkSmsCode")
    public Map<String,Object> checkSmsCode(SmsCode smsCode){
        System.out.println(smsCode);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","success");
        map.put("userInfo","asdkfbnasdjkhkoasdf");
        return map;
    }
}
