package com.bwie.seckill.service.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserInfoModel {
    //主键
    private String id;
    //名称
    @NotBlank(message = "用户姓名不能为空")
    private String name;
    //年龄
    @Min(value = 0,message = "年龄必须大于0")
    @Max(value = 999,message = "年龄必须小于999")
    private Integer age;
    //手机号
    @NotBlank(message = "手机号不能为空")
    private String telephone;
    //注册方式
    private String registerMode;
    //支付方式
    private String thirdPayId;
    //密码
    @NotBlank(message = "密码不能为空")
    private String password;
}
