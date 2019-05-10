package com.bwie.seckill.dao;

import com.bwie.seckill.dto.UserPassDO;
import com.bwie.seckill.dto.UserPassDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPassDOMapper {
    int countByExample(UserPassDOExample example);

    int deleteByExample(UserPassDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(UserPassDO record);

    int insertSelective(UserPassDO record);

    List<UserPassDO> selectByExample(UserPassDOExample example);

    UserPassDO selectByPrimaryKey(String id);

    UserPassDO selectByPrimaryByUserId(String id);

    int updateByExampleSelective(@Param("record") UserPassDO record, @Param("example") UserPassDOExample example);

    int updateByExample(@Param("record") UserPassDO record, @Param("example") UserPassDOExample example);

    int updateByPrimaryKeySelective(UserPassDO record);

    int updateByPrimaryKey(UserPassDO record);

}