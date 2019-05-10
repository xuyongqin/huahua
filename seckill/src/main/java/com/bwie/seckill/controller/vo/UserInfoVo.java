package com.bwie.seckill.controller.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    //主键
    private String id;
    //名称
    private String name;
    //年龄
    private Integer age;
    //手机号
    private String telephone;
    //注册方式
    private String registerMode;
    //支付方式
    private String thirdPayId;
}
