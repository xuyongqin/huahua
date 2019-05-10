package com.bwie.seckill.utils;

import lombok.Data;

@Data
public class CommonReturnType {
    //状态 200 or ≠200
    //success or fail/error
    private String status;
    private Object data;

    //定义一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }
}