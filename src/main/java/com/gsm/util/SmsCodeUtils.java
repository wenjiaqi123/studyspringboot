package com.gsm.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.gsm.model.SmsCode;

/**
 * 发送短信验证码
 */
public class SmsCodeUtils {

    /**
     * 阿里云短信验证
     *
     * @param smsCode
     */
    private static void sendSmsCode(SmsCode smsCode) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", SmsCodeConstants.SMS_CODE_ACCESSKEYID, SmsCodeConstants.SMS_CODE_ACCESSKEYSECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("SignName", SmsCodeConstants.SMS_CODE_SIGNNAME);
        request.putQueryParameter("TemplateCode", SmsCodeConstants.TEMPLATECODE_IPHONE_REGISTER);
        request.putQueryParameter("PhoneNumbers", smsCode.getIphoneNo().toString());
        request.putQueryParameter("TemplateParam", smsCode.getJson());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据一个手机号发送一条 短信验证码
     *
     * @param smsCode
     */
    public static void sendSmsCodeOneIphoneNo(SmsCode smsCode) {
        String jsonTemplate = "{\"code\":" + smsCode.getSmsCode() + "}";
        smsCode.setJson(jsonTemplate);
        sendSmsCode(smsCode);
    }
}
