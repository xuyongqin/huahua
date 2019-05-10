package com.bwie.seckill.service.impl;

import com.bwie.seckill.dao.PromoDOMapper;
import com.bwie.seckill.dto.PromoDO;
import com.bwie.seckill.dto.PromoDOExample;
import com.bwie.seckill.service.PromoService;
import com.bwie.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {
    @Autowired PromoDOMapper promoDOMapper;
    /**
     * 功能描述：根据商品itemId查询秒杀活动
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 19:16 2019/3/25
     * @Param [itemId]
     * @return com.bwie.seckill.service.model.PromoModel
     **/
    @Override
    public PromoModel getPromoByItemId(String itemId) {
        //获取活动信息
        PromoDOExample example = new PromoDOExample();
        PromoDOExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<PromoDO> promoDOS = promoDOMapper.selectByExample(example);
        //判断是否有数据
        if(promoDOS != null && promoDOS.size()>0){
            PromoDO promoDO = promoDOS.get(0);
            //promoDO --> promoModel
            PromoModel promoModel = this.convertPromoDOFromPromoDO(promoDO);
            //抢救
            if (promoModel == null){
                return null;
            }
            //判断当前时间是否是 秒杀活动即将开始或者正在进行中或结束
            //活动状态 1未开始 2进行中 3结束
            if (promoModel.getStartDate().isAfterNow()){
                promoModel.setStatus(1);
            }else if (promoModel.getEndDate().isBeforeNow()){
                promoModel.setStatus(3);
            }else{
                promoModel.setStatus(2);
            }
            return promoModel;
        }
        return null;
    }
    /**
     * 功能描述：promoDO --> promoModel
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 19:02 2019/3/25
     * @Param [promoDO]
     * @return com.bwie.seckill.service.model.PromoModel
     **/
    private PromoModel convertPromoDOFromPromoDO(PromoDO promoDO){
        if (promoDO == null){
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoDO,promoModel);
        //时间转化 价格转换
        promoModel.setPromoItemPrice(new BigDecimal(promoDO.getPromoItemPrice()));
        promoModel.setStartDate(new DateTime(promoDO.getStartDate()));
        promoModel.setEndDate(new DateTime(promoDO.getEndDate()));
        return promoModel;
    }
}
