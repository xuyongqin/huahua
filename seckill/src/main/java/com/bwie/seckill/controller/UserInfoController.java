package com.bwie.seckill.controller;

import com.bwie.seckill.controller.vo.UserInfoVo;
import com.bwie.seckill.service.UserInfoService;
import com.bwie.seckill.service.model.UserInfoModel;
import com.bwie.seckill.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Controller
@RequestMapping("/user")
//解决跨域问题
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserInfoController extends BaseController {
    @Value("${APPCODE-SEND}")
    public String appCode;
    @Value("${CODE-API-HOST}")
    public String apiHost;
    @Value("${CODE-API-PATH}")
    public String apiPath;
    @Value("${CODE-API-SIGN}")
    public String sign;
    @Value("${CODE-API-SKIN}")
    public String skin;

    @Autowired private UserInfoService userInfoService;
    @Autowired private HttpServletRequest httpServletRequest;
    /**
     * 
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 10:35 2019/3/23
     * @Param [id]
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    @ResponseBody
    public CommonReturnType getUserInfo(@RequestParam String id) throws BusinessException {
        if (StringUtils.isEmpty(id)){
            throw new BusinessException(EmBusinessError.PARAM_NOT_EXIST);
        }
        //获取用户信息
        UserInfoModel userInfo = userInfoService.getUserInfo(id);
//        userInfo = null;
//        if (userInfo == null){
//            //自定义异常的方式
//            throw new BusinessException(EmBusinessError.UN_ERROR,"用户信息错误");
//        }

        //拼装数据 页面传输层
        UserInfoVo userInfoVo = this.convertUserInfoVoFromUserInfoModel(userInfo);
        return CommonReturnType.create(userInfoVo);
    }

    /**
     * 功能描述：处理用户信息表中的数据 隐藏密码信息
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:14 2019/3/22
     * @Param [userInfo]
     * @return com.bwie.seckill.controller.vo.UserInfoVo
     **/
    private UserInfoVo convertUserInfoVoFromUserInfoModel(UserInfoModel userInfo){
        if(userInfo == null){
            return null;
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }

    /**
     * 功能描述：用户获取opt短信接口
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:13 2019/3/22
     * @Param [telephone]
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @RequestMapping(value = "/getOptCode",method = RequestMethod.POST,consumes = {CONTENTTYPE})
    @ResponseBody
    public CommonReturnType getOptCode(@RequestParam String telephone) throws BusinessException{
        //判断数据是否有效
        if (StringUtils.isEmpty(telephone)){
            throw new BusinessException(EmBusinessError.PHONE_NOT_NULL);
        }
        //校验手机号是否已注册
        if (userInfoService.getUserInfoByPhone(telephone)){
            throw new BusinessException(EmBusinessError.TELPHONE_ISEXIST);
        }
        //需要按照一定的规则生产opt验证码

        String optCode_s = MSUtils.getOptCode();
        //将opt的验证码用对应的手机号关联起来
        httpServletRequest.getSession().setAttribute(telephone,optCode_s);
        //将opt的验证码通过短信通道发送给手机用户
        System.out.println("telephone:"+telephone+"optCode_s:"+optCode_s);
        //发送短信
        SendUtils.sendCode(telephone,optCode_s,apiHost,apiPath,appCode,sign,skin);
        return CommonReturnType.create(null);
    }



    /**
     * 功能描述：注册用户
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:15 2019/3/22
     * @Param [telephone, optCode, name, password, age]
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @RequestMapping(value = "/createUserInfo",method = RequestMethod.POST,consumes = {CONTENTTYPE})
    @ResponseBody
    public CommonReturnType createUserInfo(@RequestParam String telephone,
                                           @RequestParam String optCode,
                                           @RequestParam String name,
                                           @RequestParam String password,
                                           @RequestParam Integer age)
            throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telephone)){
            throw new BusinessException(EmBusinessError.PHONE_NOT_NULL);
        }
        //验证手机号和对应optCode相符合
        String sessionOptCode = (String) httpServletRequest.getSession().getAttribute(telephone);
        if (!StringUtils.equals(optCode,sessionOptCode)){
            throw new BusinessException(EmBusinessError.OPTCODE_IS_ERROR);
        }
        //用户的注册流程
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setTelephone(telephone);
        userInfoModel.setName(name);
        userInfoModel.setAge(age);
        userInfoModel.setPassword(encodeByMD5(password));
        userInfoModel.setRegisterMode("byPhone");
        userInfoModel.setId(UUID.randomUUID().toString().replace("-",""));
        //调用service层方法
        userInfoService.createUserInfo(userInfoModel);
        //成功后 返回状态给前端
        return CommonReturnType.create(null);
    }
    /**
     * 功能描述：MD5的加密算法
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 21:55 2019/3/20
     * @Param [pass]
     * @return java.lang.String
     **/
    public static String encodeByMD5(String pass) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密
        byte[] bytes = pass.getBytes("UTF-8");
        String encode = base64Encoder.encode(md5.digest());
        return encode;
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println(encodeByMD5("110"));
//        }catch (NoSuchAlgorithmException e){
//            e.printStackTrace();
//        }catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 功能描述：登录功能
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 23:15 2019/3/22
     * @Param [telephone, password]
     * @return com.bwie.seckill.utils.CommonReturnType
     **/
    @RequestMapping(value = "/login",method = RequestMethod.POST ,consumes = {CONTENTTYPE} )
    @ResponseBody
    public CommonReturnType login( @RequestParam String telephone ,@RequestParam String  password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw  new BusinessException(EmBusinessError.PARAM_NOT_EXIST);
        }
        //获取用户的信息
        UserInfoModel  userInfoModel = userInfoService.validateUserInfoByPhone(telephone,encodeByMD5(password));
        //放入缓存中（session）
        httpServletRequest.getSession().setAttribute(USER_LOGIN,userInfoModel);
        //用户已登录
        httpServletRequest.getSession().setAttribute(IS_LOGIN,true);
        //返回给前端
        UserInfoVo userInfoVo = this.convertUserInfoVoFromUserInfoModel(userInfoModel);
        return CommonReturnType.create(userInfoVo);
    }
}