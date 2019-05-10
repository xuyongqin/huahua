package com.bwie.seckill.dao;

import com.bwie.seckill.dto.StockDO;
import com.bwie.seckill.dto.StockDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockDOMapper {
    int countByExample(StockDOExample example);

    int deleteByExample(StockDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(StockDO record);

    int insertSelective(StockDO record);

    List<StockDO> selectByExample(StockDOExample example);

    StockDO selectByPrimaryKey(String id);

    StockDO selectByItemId(String id);

    int updateByExampleSelective(@Param("record") StockDO record, @Param("example") StockDOExample example);

    int updateByExample(@Param("record") StockDO record, @Param("example") StockDOExample example);

    int updateByPrimaryKeySelective(StockDO record);

    int updateByPrimaryKey(StockDO record);
}