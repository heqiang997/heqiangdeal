package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.DateVo;
import com.zhimeng.caiwuweb.entity.Travel;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface TravelMapper {
    int deleteByPrimaryKey(String id);

    int deleteByNamePrimaryKey(String id,String name);

    int deleteByTimePrimaryKey(Date time,String name);

    int deleteByTimeAndNamePrimaryKey(Date time,String name,String id);

    int insert(Travel record);

    int insertSelective(Travel record);

    Travel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Travel record);

    int updateByPrimaryKeyAndNameSelective(Travel record);

    List<Travel> selectIdByName(String name);

    int updateByPrimaryKey(Travel record);

    List<Travel> selectByName(String name);

    List<Travel> selectBetweenTime(DateVo vo);

    Travel selectByNameTime(Travel travel);

}