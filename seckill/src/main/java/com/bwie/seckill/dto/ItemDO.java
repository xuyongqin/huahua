package com.bwie.seckill.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户对象",description = "用户对象的model")
public class ItemDO {
    @ApiModelProperty(value = "id",name="商品id")
    private String id;
    @ApiModelProperty(value = "商品标题",name="title")
    private String title;
    @ApiModelProperty(value = "商品价格",name="price")
    private Double price;
    @ApiModelProperty(value = "商品描述",name="description")
    private String description;
    @ApiModelProperty(value = "商品售价",name="sales")
    private Integer sales;
    @ApiModelProperty(value = "图片",name="imgUrl")
    private String imgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }
}