package com.bwie.seckill.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 校验原型
 * @Author LiHuaMing
 * @Description //TODO
 * @Date 19:50 2019/3/21
 * @Param
 * @return
 **/
public class ValidatorResult {
    //校验效果是否有错
    private boolean hasError = false;
    //存放错误信息的map
    private Map<String,String> errorMsgMap = new HashMap<>();

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    //实现通过通用的格式化字符串信息获取错误的msg方法
    public String getErrorMsg(){
        String join = StringUtils.join(errorMsgMap.values());
        return join;
    }
}
