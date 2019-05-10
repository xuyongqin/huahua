package com.bwie.seckill.dao;

import com.bwie.seckill.dto.OrderInfoDO;
import com.bwie.seckill.dto.OrderInfoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderInfoDOMapper {
    int countByExample(OrderInfoDOExample example);

    int deleteByExample(OrderInfoDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderInfoDO record);

    int insertSelective(OrderInfoDO record);

    List<OrderInfoDO> selectByExample(OrderInfoDOExample example);

    OrderInfoDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderInfoDO record, @Param("example") OrderInfoDOExample example);

    int updateByExample(@Param("record") OrderInfoDO record, @Param("example") OrderInfoDOExample example);

    int updateByPrimaryKeySelective(OrderInfoDO record);

    int updateByPrimaryKey(OrderInfoDO record);
}