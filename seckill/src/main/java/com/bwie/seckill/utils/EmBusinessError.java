package com.bwie.seckill.utils;

/**
 * 错误码类
 */
public enum EmBusinessError implements CommonError{
    //90000* 系统错误
    UN_ERROR(900001,"未知错误"),
    PARAM_NOT_EXIST(900002,"参数有误"),
    //10000* 代表用户错误码
    USER_NOT_EXIST(100001,"用户信息不存在"),
    PHONE_NOT_EXIST(100002,"手机号参数有误"),
    PHONE_NOT_NULL(100003,"手机号不能为空"),
    OPTCODE_IS_ERROR(100004,"验证码输入有误,请重新注册"),
    TELPHONE_ISEXIST(100005,"手机号已存在"),
    USER_PASSWORD_ERROR(100006,"用户名或者密码输入有误，请重新输入"),
    USER_IS_SESSION_NULL(100007,"用户信息已失效,请重新登录"),
    //商品code
    ITEM_ID_NULL(200001,"商品信息不存在,请重新刷新"),
    ITEM_ID_AMOUNT_ERROR(200002,"商品库存不足");

    private int errorCode;
    private String errorMsg;
    private EmBusinessError(int errorCode,String  errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMsg() {
        return this.errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
