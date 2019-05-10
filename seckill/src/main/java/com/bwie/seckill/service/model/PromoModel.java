package com.bwie.seckill.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;
@Data
public class PromoModel {

    private String id;
    //活动名称
    private String promoName;
    //开始时间
    private DateTime startDate;
    //结束时间
    private DateTime endDate;
    //活动使用的商品id
    private String itemId;
    //秒杀价格
    private BigDecimal promoItemPrice;
    //活动状态 1未开始 2进行中 3结束
    private Integer status;
}
