package com.bwie.seckill.service;

import com.bwie.seckill.service.model.UserInfoModel;
import com.bwie.seckill.utils.BusinessException;

public interface UserInfoService {
    /**
     * 功能描述：
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:18 2019/3/22
     * @Param [id]
     * @return com.bwie.seckill.service.model.UserInfoModel
     **/
    UserInfoModel getUserInfo(String id);
    /**
     * 功能描述：
     * @Author LiHuaMing
     * @Description //TODO 
     * @Date 23:18 2019/3/22
     * @Param [userInfoModel]
     * @return void
     **/
    void createUserInfo(UserInfoModel userInfoModel) throws BusinessException;
    /**
    * 功能描述：校验手机号是否已注册
    * @Author LiHuaMing
    * @Description //TODO
    * @Date 23:38 2019/3/20
    * @Param [telephone]
    * @return boolean
    */
    boolean getUserInfoByPhone(String telephone);
    /**
     * 功能描述：登录效验
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 15:00 2019/3/21
     * @Param [telephone, encodeByMD5]
     * @return com.bwie.seckill.service.model.UserInfoModel
     **/
    UserInfoModel validateUserInfoByPhone(String telephone, String password) throws BusinessException;
}
