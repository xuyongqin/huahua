package com.bwie.seckill.controller;

import com.bwie.seckill.service.OrderInfoService;
import com.bwie.seckill.service.model.OrderModel;
import com.bwie.seckill.service.model.UserInfoModel;
import com.bwie.seckill.utils.BusinessException;
import com.bwie.seckill.utils.CommonReturnType;
import com.bwie.seckill.utils.EmBusinessError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderInfoController extends BaseController{
    @Autowired OrderInfoService orderInfoService;
    @Autowired HttpServletRequest httpServletRequest;
    /**
     * 功能描述：创建订单
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 15:51 2019/3/25
     * @Param [itemId, amount]
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @RequestMapping(value = "/createOrder",method = RequestMethod.POST,consumes = {CONTENTTYPE})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam String itemId,
                                        @RequestParam Integer amount,
                                        @RequestParam String promoId) throws BusinessException {
        //校验用户是否登录
        Boolean b = (Boolean) httpServletRequest.getSession().getAttribute(IS_LOGIN);
        if (b == null || !b.booleanValue()){
            throw new BusinessException(EmBusinessError.USER_IS_SESSION_NULL);
        }
        //获取用户信息
        UserInfoModel userInfoModel = (UserInfoModel) httpServletRequest.getSession().getAttribute(USER_LOGIN);
        OrderModel order = orderInfoService.createOrder(userInfoModel.getId(), itemId, amount,promoId);
        return CommonReturnType.create(null);
    }
}