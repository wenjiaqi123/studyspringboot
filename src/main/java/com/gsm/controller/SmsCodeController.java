package com.gsm.controller;

import com.gsm.model.SmsCode;
import com.gsm.service.SmsCodeService;
import com.gsm.util.RandomNumUtils;
import com.gsm.util.SmsCodeUtils;
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

    @ApiOperation("根据手机号发送短信")
    @PostMapping("sendSmsCode")
    public Map<String,Object> sendSmsCode(SmsCode smsCode){
        //设置随机数为验证码
        Integer random4 = RandomNumUtils.getRandom4();
        smsCode.setSmsCode(random4);
        //发送短信
        SmsCodeUtils.sendSmsCodeOneIphoneNo(smsCode);
        //将用户手机号和随机数存入数据库，用于之后接口验证
        smsCodeService.insertUserIphoneNoAndSmsCode(smsCode);
        //返回信息
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("msg","success");
        return map;
    }

    @ApiOperation("根据手机号和手机验证码判断是否手机号本人使用")
    @PostMapping("checkSmsCode")
    public Map<String,Object> checkSmsCode(SmsCode smsCode){
        Map<String,Object> map = smsCodeService.updateSmsCode(smsCode);
        return map;
    }
}
