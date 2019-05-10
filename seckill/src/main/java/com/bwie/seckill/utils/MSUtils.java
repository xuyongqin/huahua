package com.bwie.seckill.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class MSUtils {
    /**
     * 功能描述：生成UUID 32位
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 22:57 2019/3/21
     * @Param
     * @return
     **/
    public static String getUUID32(){
        String replace = UUID.randomUUID().toString().replace("-", "");
        return replace;
    }
    /**
     * 功能描述：生成订单流水号
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 16:29 2019/3/22
     * @Param
     * @return
     **/
    public static String generateOrderNO(){
        StringBuilder stringBuilder = new StringBuilder();
        //yyyy-MM-dd-hh:mm:ss+随机数
        //获取当前的时间
        LocalDateTime now = LocalDateTime.now();
        String replace = now.format(DateTimeFormatter.ISO_DATE_TIME)
                .replace("-", "").replace("T","")
                .replace(":","").replace(".","");
        String s = stringBuilder.append(replace).append(getOptCode()).toString();
        return s;
    }
    public static String getOptCode() {
        Random random = new Random();
        int anInt = random.nextInt(999999);
        String valueOf = String.valueOf(anInt);
        if (StringUtils.isEmpty(valueOf)){
            if (valueOf.length() < 6){
                int length = valueOf.length();
                int len = 6 -length;    //缺的 位数
                for (int i = 0;i < len;i++){
                    valueOf += "0";
                }
            }
        }
        return valueOf;
    }

    public static void main(String[] args) {
        System.out.println(generateOrderNO());
    }
    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /***
     * 将date类型的时间转化为string类型
     * @param date 需要转化的date类型的时间
     * @param formatStr 转化规则
     * @return 返回转化后的string类型的时间
     */
    public static String dateToStr(DateTime date, String formatStr){
        if(date == null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(formatStr);
    }
}
