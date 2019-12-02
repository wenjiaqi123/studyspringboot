package com.gsm.service;

import com.gsm.model.SmsCode;

import java.util.Map;

public interface SmsCodeService {
    void insertUserIphoneNoAndSmsCode(SmsCode smsCode);

    Map<String, Object> updateSmsCode(SmsCode smsCode);
}
