package com.bwie.seckill.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
//声明他是spring容器的一个bean
@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator;
    /**
     * 功能描述：当请求参数发送到控制层时,会通过我们自定义的校验规则,进行检查
     * 实例化校验器
     * @Author LiHuaMing
     * @Description //TODO
     * @Date 19:57 2019/3/21
     * @Param []
     * @return void
     **/
    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate-validator通过工程初始化的方式使用实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    //实现校验的方法并返回校验的结
    public ValidatorResult validationResult(Object bean){
        ValidatorResult result = new ValidatorResult();
        //如果bean参数未被了validation定义的规则,则返回结果validate
        Set<ConstraintViolation<Object>> validate = validator.validate(bean);
        if (validate != null && validate.size()>0){//如果大于零,则有异常,有错误
            result.setHasError(true);//有错误
            //使用拉姆达表达式获取错误信息
            validate.forEach(objectConstraintViolation -> {
                String errMsg = objectConstraintViolation.getMessage();//错误信息
                String propertyPath = objectConstraintViolation.getPropertyPath().toString();//错误名称
                String put = result.getErrorMsgMap().put(propertyPath, errMsg);
            });
            return result;
        }
        return result;
    }
}
