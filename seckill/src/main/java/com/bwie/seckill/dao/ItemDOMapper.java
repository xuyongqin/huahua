package com.bwie.seckill.dao;

import com.bwie.seckill.dto.ItemDO;
import com.bwie.seckill.dto.ItemDOExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemDOMapper {
    int countByExample(ItemDOExample example);

    int deleteByExample(ItemDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    List<ItemDO> selectByExample(ItemDOExample example);

    ItemDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    int updateByExample(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    int updateDecreaseItemStock(@Param("itemId")String itemId,@Param("amount") Integer amount);
}