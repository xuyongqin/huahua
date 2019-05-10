package com.bwie.seckill.dao;

import com.bwie.seckill.dto.PromoDO;
import com.bwie.seckill.dto.PromoDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PromoDOMapper {
    int countByExample(PromoDOExample example);

    int deleteByExample(PromoDOExample example);

    int deleteByPrimaryKey(String id);

    int insert(PromoDO record);

    int insertSelective(PromoDO record);

    List<PromoDO> selectByExample(PromoDOExample example);

    PromoDO selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PromoDO record, @Param("example") PromoDOExample example);

    int updateByExample(@Param("record") PromoDO record, @Param("example") PromoDOExample example);

    int updateByPrimaryKeySelective(PromoDO record);

    int updateByPrimaryKey(PromoDO record);
}