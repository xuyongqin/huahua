package com.bwie.seckill.controller;

import com.bwie.seckill.utils.BusinessException;
import com.bwie.seckill.utils.CommonReturnType;
import com.bwie.seckill.utils.EmBusinessError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常
 */
public class BaseController {
    public static final String CONTENTTYPE = "application/x-www-form-urlencoded";
    public static final String USER_LOGIN = "USER_LOGIN";
    public static final String IS_LOGIN = "IS_LOGIN";

    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler(Exception.class)  //定义Exception解决未被Controller层处理的异常
    public Object handlerException(HttpServletRequest request, Exception e){
        Map map = new HashMap<>();
        if (e instanceof BusinessException){
            BusinessException businessException = (BusinessException)e;
            map.put("errorCode",businessException.getErrorCode());
            map.put("errorMsg",businessException.getErrorMsg());
        }else {
            map.put("errorCode",EmBusinessError.UN_ERROR.getErrorCode());
            map.put("errorMsg",EmBusinessError.UN_ERROR.getErrorMsg());
        }
        return CommonReturnType.create(map,"fail");
    }
}
