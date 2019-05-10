package com.bwie.seckill.utils;

public interface CommonError {
    /**
     * 获取错误码
     * @return
     */
    public int getErrorCode();
    /**
     * 获取错误信息
     * @return
     */
    public String getErrorMsg();

    public CommonError setErrorMsg(String errorMsg);
}
