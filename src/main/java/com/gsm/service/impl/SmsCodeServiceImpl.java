package com.gsm.service.impl;

import com.gsm.dao.SmsCodeDao;
import com.gsm.model.SmsCode;
import com.gsm.service.SmsCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsCodeServiceImpl implements SmsCodeService {
    @Autowired
    private SmsCodeDao smsCodeDao;

    @Override
    public void insertUserIphoneNoAndSmsCode(SmsCode smsCode) {
        smsCodeDao.insertUserIphoneNoAndSmsCode(smsCode);
    }

    /**
     * 校验验证码是否正确
     *
     * @param smsCode
     */
    @Override
    public Map<String, Object> updateSmsCode(SmsCode smsCode) {
        Map<String,Object> map = new HashMap<>(16);

        Integer code = smsCodeDao.getSmsCodeByIphoneNo(smsCode);
        if (code != null && code.equals(smsCode.getSmsCode())) {
            map.put("str","验证码正确");
            map.put("msg","success");
            //生成获取，用户token
            map.put("userInfo","userToken");
        }else {
            map.put("str","验证码不正确");
            map.put("msg","error");
        }
        return map;
    }
}
