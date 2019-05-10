package com.bwie.seckill.service;

import com.bwie.seckill.service.model.OrderModel;
import com.bwie.seckill.utils.BusinessException;

public interface OrderInfoService {

    //创建订单
    OrderModel createOrder(String userId,String itemId,Integer amount,String promoId) throws BusinessException;

}
