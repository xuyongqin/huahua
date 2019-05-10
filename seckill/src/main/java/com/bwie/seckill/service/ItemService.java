package com.bwie.seckill.service;

import com.bwie.seckill.service.model.ItemModel;
import com.bwie.seckill.utils.BusinessException;

import java.util.List;

public interface ItemService {

    //添加商品
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
    //查询商品
    List<ItemModel> itemList();
    //商品详情
    ItemModel getItemById(String id);
    //对库存减减
    boolean decreaseItemStock(String itemId, Integer amount);
    //对销量加加
    void addSalesCount(String itemId, Integer amount);
    //修改商品
    ItemModel updateItem(ItemModel itemModel) throws BusinessException;
    //删除商品
    boolean deleteItem(String itemId);
}
