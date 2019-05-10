package com.bwie.seckill.service.impl;

import com.bwie.seckill.dao.OrderInfoDOMapper;
import com.bwie.seckill.dto.OrderInfoDO;
import com.bwie.seckill.service.ItemService;
import com.bwie.seckill.service.OrderInfoService;
import com.bwie.seckill.service.PromoService;
import com.bwie.seckill.service.UserInfoService;
import com.bwie.seckill.service.model.ItemModel;
import com.bwie.seckill.service.model.OrderModel;
import com.bwie.seckill.service.model.PromoModel;
import com.bwie.seckill.service.model.UserInfoModel;
import com.bwie.seckill.utils.BusinessException;
import com.bwie.seckill.utils.EmBusinessError;
import com.bwie.seckill.utils.MSUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired private UserInfoService userInfoService;
    @Autowired private ItemService itemService;
    @Autowired private OrderInfoDOMapper orderInfoDOMapper;
    @Autowired private PromoService promoService;
    /**
     * 功能描述：创建订单
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 11:43 2019/3/23
     * @Param [userId, itemId, amount]
     * @return com.bwie.seckill.service.model.OrderModel
     **/
    @Override
    public OrderModel createOrder(String userId, String itemId, Integer amount,String promoId) throws BusinessException {
        //校验下单的状态,下单的商品是否存在,用户是否登录
        ItemModel itemById = itemService.getItemById(itemId);
        if (itemById == null){
            throw new BusinessException(EmBusinessError.ITEM_ID_NULL);
        }
        //user信息的校验
        UserInfoModel userInfo = userInfoService.getUserInfo(userId);
        if (userInfo == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        //商品的库存是否足够
        if (amount <= 0 || amount > 99 ){
            throw new BusinessException(EmBusinessError.ITEM_ID_AMOUNT_ERROR);
        }



        //2:下单减库存或者支付减库存(锁表)
        boolean result = itemService.decreaseItemStock(itemId, amount);
        if (!result){
            throw new BusinessException(EmBusinessError.ITEM_ID_AMOUNT_ERROR);
        }

        //3:订单入库
        OrderModel orderModel = new OrderModel();
        orderModel.setItemId(itemId);
        orderModel.setUserId(userId);
        orderModel.setAmount(amount);
        //查询秒杀活动价格
        PromoModel promoByItemId = promoService.getPromoByItemId(itemId);
        //计算秒杀活动的订单价格
        if (promoByItemId != null && promoByItemId.getStatus() == 2){
            if (StringUtils.equals(promoId,promoByItemId.getId())){
                orderModel.setItemPrice(promoByItemId.getPromoItemPrice());
                orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));
            }
        }else{
            //正常的订单下单逻辑
            orderModel.setItemPrice(itemById.getPrice());
            orderModel.setOrderPrice(itemById.getPrice().multiply(new BigDecimal(amount)));
        }

        //生成订单流水号
        orderModel.setId(MSUtils.generateOrderNO());
        //把数据放入OrderVo
        OrderInfoDO orderInfoDO = convertOrderVoFromOrderModel(orderModel);
        //写入数据库
        orderInfoDOMapper.insertSelective(orderInfoDO);
        itemService.addSalesCount(itemId,amount);
        return orderModel;
    }
    /**
     * 功能描述：orderModel --> orderInfoDO
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 11:44 2019/3/23
     * @Param [orderModel]
     * @return com.bwie.seckill.dto.OrderInfoDO
     **/
    private OrderInfoDO convertOrderVoFromOrderModel(OrderModel orderModel) {
        if(orderModel == null){
            return null;
        }
        OrderInfoDO orderInfoDO = new OrderInfoDO();
        BeanUtils.copyProperties(orderModel,orderInfoDO);
        orderInfoDO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderInfoDO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderInfoDO;
    }
}
