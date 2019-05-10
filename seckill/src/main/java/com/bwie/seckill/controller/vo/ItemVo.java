package com.bwie.seckill.controller.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVo {
    private String id;
    private String title;
    private BigDecimal price;
    private Integer stock;
    private String description;
    private Integer sales;
    private String imgUrl;
    //新增字段
    private String promoName;
    private Integer status;
    private BigDecimal promoItemPrice;
    private String startDate;
    private String promoId;
}
