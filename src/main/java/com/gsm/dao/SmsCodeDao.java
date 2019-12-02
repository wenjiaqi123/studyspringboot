package com.gsm.dao;

import com.gsm.model.SmsCode;

public interface SmsCodeDao {
    void insertUserIphoneNoAndSmsCode(SmsCode smsCode);

    Integer getSmsCodeByIphoneNo(SmsCode smsCode);
}
