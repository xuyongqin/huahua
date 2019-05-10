package com.bwie.seckill.controller;

import com.bwie.seckill.controller.vo.ItemVo;
import com.bwie.seckill.service.ItemService;
import com.bwie.seckill.service.model.ItemModel;
import com.bwie.seckill.utils.BusinessException;
import com.bwie.seckill.utils.CommonReturnType;
import com.bwie.seckill.utils.EmBusinessError;
import com.bwie.seckill.utils.MSUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/item")
@Api(value = "商品管理",tags = "CRUD的日常")
//解决跨域问题
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController{
    @Autowired ItemService itemService;

    /**
     * 商品描述：创建商品
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 13:34 2019/3/22
     * @Param [title, description, price, stock, imgUrl]
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @RequestMapping(value = "/createItem",method = RequestMethod.POST,consumes = {CONTENTTYPE})
    @ResponseBody
    public CommonReturnType creatItem(@RequestParam String title,
                                      @RequestParam String description,
                                      @RequestParam String price,
                                      @RequestParam Integer stock,
                                      @RequestParam String imgUrl) throws BusinessException {
        //封装service请求用来创建商品
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(new BigDecimal(price));
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);
        ItemModel item = itemService.createItem(itemModel);
        //把业务模型赋值给传输模型
        ItemVo itemVo = this.convertItemFromItemModel(item);
        return CommonReturnType.create(itemVo);
    }
    /**
     * 功能描述：把业务模型赋值给传输模型
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 10:05 2019/3/22
     * @Param [itemModel]
     * @return com.bwie.seckill.controller.vo.ItemVo
     **/
    private ItemVo convertItemFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }
        ItemVo itemVo = new ItemVo();
        BeanUtils.copyProperties(itemModel,itemVo);
        itemVo.setPrice(itemModel.getPrice());
        //新增处理字段
        if (itemModel.getPromoModel() != null){
            itemVo.setPromoItemPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVo.setStatus(itemModel.getPromoModel().getStatus());
            itemVo.setStartDate(MSUtils.dateToStr(itemModel.getPromoModel().getStartDate(),MSUtils.STANDARD_FORMAT));
            itemVo.setPromoName(itemModel.getPromoModel().getPromoName());
            itemVo.setPromoId(itemModel.getPromoModel().getId());
        }else{
            itemVo.setStatus(0);//没有活动
        }
        return itemVo;
    }
    /**
     * 功能描述：获取数据商品集合
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 14:38 2019/3/22
     * @Param []
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @ApiOperation(value = "获取所有数据-user",httpMethod = "POST",response =ItemVo.class )
    @RequestMapping(value = "/getItemList",method = RequestMethod.POST,consumes = {CONTENTTYPE})
    @ResponseBody
    public CommonReturnType getItemList(){
        List<ItemModel> itemModels = itemService.itemList();//业务模型的数据集合
        List<ItemVo> itemVoList = new ArrayList<>();//传输层的数据集合
        for (ItemModel itemModel : itemModels){
            ItemVo itemVo = this.convertItemFromItemModel(itemModel);
            itemVoList.add(itemVo);
        }
        return CommonReturnType.create(itemVoList);
    }
    /**
     * 功能描述：获取商品的详情
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 14:38 2019/3/22
     * @Param
     * @return
     **/
    @RequestMapping(value = "/getItemById",method = RequestMethod.POST,consumes = {CONTENTTYPE})
    @ResponseBody
    public CommonReturnType getItemById(@RequestParam String id) throws BusinessException {
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(EmBusinessError.PARAM_NOT_EXIST);
        }
        ItemModel itemById = itemService.getItemById(id);
        ItemVo itemVo = this.convertItemFromItemModel(itemById);
        return CommonReturnType.create(itemVo);
    }
}
