package com.bwie.seckill.service.impl;

import com.bwie.seckill.dao.UserDOMapper;
import com.bwie.seckill.dao.UserPassDOMapper;
import com.bwie.seckill.dto.UserDO;
import com.bwie.seckill.dto.UserDOExample;
import com.bwie.seckill.dto.UserPassDO;
import com.bwie.seckill.service.UserInfoService;
import com.bwie.seckill.service.model.UserInfoModel;
import com.bwie.seckill.utils.BusinessException;
import com.bwie.seckill.utils.EmBusinessError;
import com.bwie.seckill.utils.ValidatorImpl;
import com.bwie.seckill.utils.ValidatorResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired UserDOMapper userDOMapper;
    @Autowired UserPassDOMapper userPassDOMapper;
    @Autowired ValidatorImpl validator;
    /**
     * 
     * @Author LiHuaMing
     * @Description //TODO 
     * @Date 10:57 2019/3/23
     * @Param [id]
     * @return com.bwie.seckill.service.model.UserInfoModel
     **/
    @Override
    public UserInfoModel getUserInfo(String id) {
        //通过id获取用户的信息
        UserDO userDo = userDOMapper.selectByPrimaryKey(id);
        //userDo --> userInfoModel
        UserInfoModel userInfoModel = this.convertUserInfoModelFromUserDO(userDo);
        //通过userId查询用户密码
        UserPassDO userPassDO = userPassDOMapper.selectByPrimaryByUserId(id);
        //判断密码是否为空
        if (userPassDO != null){
            userInfoModel.setPassword(userPassDO.getPassword());
        }
        return userInfoModel;
    }

    /**
     * 功能描述：注册用户
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 10:57 2019/3/23
     * @Param [userInfoModel]
     * @return void
     **/
    @Override
    @Transactional
    public void createUserInfo(UserInfoModel userInfoModel) throws BusinessException {
        //校验注册信息
        ValidatorResult result = this.validator.validationResult(userInfoModel);
        //判断校验是否正确
        if (result.isHasError()){
            throw new BusinessException(EmBusinessError.UN_ERROR,result.getErrorMsg());
        }
        //userInfoModel --> userDO
        UserDO userDO = this.convertUserDOFromUserInfoModel(userInfoModel);
        if (userDO == null){
            throw new BusinessException(EmBusinessError.UN_ERROR,"用户数据有误,请联系管理员");
        }
        UserPassDO userPassDO = new UserPassDO();
        userPassDO.setId(UUID.randomUUID().toString().replace("-",""));
        userPassDO.setPassword(userInfoModel.getPassword());
        userPassDO.setUserId(userInfoModel.getId());
        //提交事务
        try {
            userDOMapper.insertSelective(userDO);
        }catch (DuplicateKeyException ex){
            throw new BusinessException(EmBusinessError.TELPHONE_ISEXIST);
        }
        userPassDOMapper.insertSelective(userPassDO);
    }
    /**
     * 功能描述：根据手机号查询用户信息
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 11:25 2019/3/23
     * @Param [telephone]
     * @return boolean
     **/
    @Override
    public boolean getUserInfoByPhone(String telephone) {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andTelephoneEqualTo(telephone);
        List<UserDO> userDOList = userDOMapper.selectByExample(example);
        //判断是否根据手机号查询到用户信息
        if (userDOList != null && userDOList.size()>0){
            return true;
        }
        return false;
    }
    /**
     * 功能描述：验证登录
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 11:30 2019/3/23
     * @Param [telephone, password]
     * @return com.bwie.seckill.service.model.UserInfoModel
     **/
    @Override
    public UserInfoModel validateUserInfoByPhone(String telephone, String password) throws BusinessException {
        UserDOExample example = new UserDOExample();
        UserDOExample.Criteria criteria = example.createCriteria();
        criteria.andTelephoneEqualTo(telephone);
        List<UserDO> userDO = userDOMapper.selectByExample(example);
        //判断根据手机号查询的用户信息是否存在
        if (userDO != null && userDO.size()>0) {
            UserDO user = userDO.get(0);
            //通过userId获取密码信息
            UserPassDO userPassDO = userPassDOMapper.selectByPrimaryByUserId(user.getId());
            //对比密码是否一致
            if (!StringUtils.equals(password,userPassDO.getPassword())){
                throw  new BusinessException(EmBusinessError.USER_PASSWORD_ERROR);
            }
            //userDO --> userInfoModel
            UserInfoModel userInfoModel = this.convertUserInfoModelFromUserDO(user);
            //userDO 里面没有密码 需要自己手动赋值
            userInfoModel.setPassword(userPassDO.getPassword());
            return  userInfoModel;
        }else{
            throw  new BusinessException(EmBusinessError.USER_PASSWORD_ERROR);
        }
    }
    /**
     * 功能描述：userInfoModel --> userDO
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 11:05 2019/3/23
     * @Param [userInfoModel]
     * @return com.bwie.seckill.dto.UserDO
     **/
    private UserDO convertUserDOFromUserInfoModel(UserInfoModel userInfoModel){
        if (userInfoModel == null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userInfoModel,userDO);
        return userDO;
    }
    /**
     * 功能描述：userDo --> userInfoModel
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 10:58 2019/3/23
     * @Param [userDo]
     * @return com.bwie.seckill.service.model.UserInfoModel
     **/
    private UserInfoModel convertUserInfoModelFromUserDO(UserDO userDo){
        if (userDo == null){
            return null;
        }
        UserInfoModel userInfoModel = new UserInfoModel();
        BeanUtils.copyProperties(userDo,userInfoModel);
        return userInfoModel;
    }
}
