package com.gsm.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Data = set      get     equal   hashCode    toString
 * @AllArgsConstructor  构造器
 * @NoArgsConstructor   空构造器
 * @Builder 建造者模式
 * @ApiModel()     @ApiOperation() 使用在 javaBean 上，说明该 javaBean 的作用
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel("用户对象模型")
public class User {
    @ApiModelProperty(value = "用户id")
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户年龄")
    private Integer userAge;

}
