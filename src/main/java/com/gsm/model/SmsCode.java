package com.gsm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("手机短信验证对象模型")
public class SmsCode {
    @ApiModelProperty("手机号")
    private BigInteger iphoneNo;
    @ApiModelProperty("手机短信验证码")
    private Integer smsCode;
    @ApiModelProperty("阿里云短信模板json")
    private String json;
}
