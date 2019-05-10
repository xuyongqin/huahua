package com.bwie.seckill.service.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderModel {
    //时间戳+随机数+分库分表
    private String id;
    //购买的用户
    private String userId;
    //商品的id
    private String itemId;
    //商品的单价
    private BigDecimal itemPrice;
    //购买的数量
    private Integer amount;
    //购买的金额
    private BigDecimal orderPrice;
    //活动id
    private String promoId;
}
