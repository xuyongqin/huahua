package com.bwie.seckill.service.impl;

import com.bwie.seckill.dao.ItemDOMapper;
import com.bwie.seckill.dao.StockDOMapper;
import com.bwie.seckill.dto.ItemDO;
import com.bwie.seckill.dto.ItemDOExample;
import com.bwie.seckill.dto.StockDO;
import com.bwie.seckill.service.ItemService;
import com.bwie.seckill.service.PromoService;
import com.bwie.seckill.service.model.ItemModel;
import com.bwie.seckill.service.model.PromoModel;
import com.bwie.seckill.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired ItemDOMapper itemDOMapper;
    @Autowired StockDOMapper stockDOMapper;
    @Autowired ValidatorImpl validator;
    @Autowired private PromoService promoService;
    /**
     * 功能描述：创建商品
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 10:55 2019/3/23
     * @Param [itemModel]
     * @return com.bwie.seckill.service.model.ItemModel
     **/
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        //工具类获取UUID
        String id = MSUtils.getUUID32();
        itemModel.setId(id);
        ValidatorResult result = validator.validationResult(itemModel);
        //参数校验
        if (result.isHasError()){
            throw new BusinessException(EmBusinessError.UN_ERROR,result.getErrorMsg());
        }
        //itemModel --> itemDO
        ItemDO itemDO = this.convertItemFromItemModel(itemModel);
        //itemModel --> stockDO
        StockDO stockDO = this.convertStockFromItemModel(itemModel);

        itemDOMapper.insertSelective(itemDO);
        stockDOMapper.insertSelective(stockDO);

        return this.getItemById(id);
    }
    /**
     * 功能描述：itemModel --> stockDO
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 10:55 2019/3/23
     * @Param [itemModel]
     * @return com.bwie.seckill.dto.StockDO
     **/
    private StockDO convertStockFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        StockDO stockDO = new StockDO();
        BeanUtils.copyProperties(itemModel,stockDO);
        //创建库存的id
        stockDO.setId(MSUtils.getUUID32());
        stockDO.setItemId(itemModel.getId());
        return stockDO;
    }
    /**
     * 功能描述：itemModel --> itemDO
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:24 2019/3/21
     * @Param [itemModel]
     * @return com.bwie.seckill.dto.ItemDO
     **/
    private ItemDO convertItemFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());
        return itemDO;
    }
    /**
     * 功能描述：商品列表
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 11:50 2019/3/23
     * @Param []
     * @return java.util.List<com.bwie.seckill.service.model.ItemModel>
     **/
    @Override
    public List<ItemModel> itemList() {
        ItemDOExample example = new ItemDOExample();
        example.setOrderByClause("sales desc");//倒序
        List<ItemDO> itemDOS = itemDOMapper.selectByExample(example);

        //查询并且拼装到新的集合中
        List<ItemModel> itemModels = new ArrayList<>();
        //判断是否查询到商品信息
        if (itemDOS != null && itemDOS.size()>0){
            //循环查询到的商品列表
            for (ItemDO itemDO : itemDOS){
                //根据商品id查询库存
                StockDO stockDO = stockDOMapper.selectByItemId(itemDO.getId());
                //itemDO --> itemModel

                ItemModel itemModel = this.convertItemModelFromItemDOAndStockDO(itemDO, stockDO,null);
                if (itemModel != null){
                    itemModels.add(itemModel);
                }
            }
        }
        return itemModels;
    }
    /**
     * 功能描述：通过id 查询商品的信息
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:28 2019/3/21
     * @Param [id]
     * @return com.bwie.seckill.service.model.ItemModel
     **/
    @Override
    public ItemModel getItemById(String id) {
        //通过id获取商品的信息
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        //通过商品的id获取库存
        StockDO stockDO = stockDOMapper.selectByItemId(id);
        //通过商品id查询秒杀活动信息
        PromoModel promoByItemId = promoService.getPromoByItemId(id);
        //拼装数据 并返回
        ItemModel itemModel = this.convertItemModelFromItemDOAndStockDO(itemDO, stockDO, promoByItemId);
        return itemModel;
    }

    @Override
    public boolean decreaseItemStock(String itemId, Integer amount) {
        //如果库存不足
        StockDO stockDO = stockDOMapper.selectByItemId(itemId);
        //对比库存
        if (stockDO.getStock().intValue() >= amount.intValue()){
            if (itemDOMapper.updateDecreaseItemStock(itemId,amount) > 0){
                return true;
            }
        }
        return false;//库存不足
    }

    @Override
    @Transactional
    public void addSalesCount(String itemId, Integer amount) {
        ItemModel itemById = this.getItemById(itemId);
        ItemDO itemDO = this.convertItemFromItemModel(itemById);
        if (itemById != null){
            itemDO.setSales(itemById.getSales()+amount);
            itemDOMapper.updateByPrimaryKey(itemDO);
        }
    }

    @Override
    public ItemModel updateItem(ItemModel itemModel) throws BusinessException {
        return null;
    }

    @Override
    public boolean deleteItem(String itemId) {
        return false;
    }

    /**
     * 功能描述：通过商品的信息和库存的信息.拼装在一个ItemModel中
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:40 2019/3/21
     * @Param [itemDO, stockDO]
     * @return com.bwie.seckill.service.model.ItemModel
     **/
    private ItemModel convertItemModelFromItemDOAndStockDO(ItemDO itemDO, StockDO stockDO, PromoModel promoByItemId) {
        if (itemDO == null || stockDO == null){
            return null;
        }
        ItemModel itemModel = new ItemModel();
        if (promoByItemId != null){
            itemModel.setPromoModel(promoByItemId);
        }
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setPrice(new BigDecimal(itemDO.getPrice()));
        itemModel.setStock(stockDO.getStock());
        return itemModel;
    }
}
