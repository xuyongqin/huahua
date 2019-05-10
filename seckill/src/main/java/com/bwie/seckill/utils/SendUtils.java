package com.bwie.seckill.utils;

import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信功能接口
 */
public class SendUtils {
    public static String method_POST = "GET";

    public static Boolean  sendCode(String telephone,String code,String host,String path,String appCode,String sign,String skin){
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("code", "code:"+code);
        querys.put("phone", telephone);
        querys.put("sign", sign);
        querys.put("skin", skin);
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            HttpResponse httpResponse = HttpUtils.doGet(host, path, method_POST, headers, querys);
//            HttpEntity entity = httpResponse.getEntity();
//            String toJSONString = JSON.toJSONString(entity);
//            System.out.println(entity);
//            if (toJSONString.contains("true")){
//                return true;
//            }else {
//                return false;
//            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true ;
    }

    public static void main(String[] args) {
        sendCode("17600467376","儿砸在不在","https://fesms.market.alicloudapi.com","/sms/","ccff60df9bee4ef1867e255e5e185b38","1","1");
    }
}
