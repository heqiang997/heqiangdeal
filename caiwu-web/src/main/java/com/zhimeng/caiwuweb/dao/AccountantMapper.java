package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.Accountant;

import java.util.List;

public interface AccountantMapper {

    int insert(Accountant record);

    int insertSelective(Accountant record);

    List<Accountant> selectByCoding(Accountant accountant);

    Accountant selectById(Integer id);

    List<Accountant> selectAll(Accountant accountant);

    List<Accountant> selectByBatchNumber(String batchNumber);

    List<Accountant> selectByName(Accountant accountant);

    List<Accountant> selectName(String name);

    List<Accountant> selectAccJoinTravel(Accountant accountant);

    List<Accountant> selectAccJoinTravelById(Accountant accountant);

    int updateByPrimaryKeySelective(Accountant accountant);

    int updateBySelectIdSelective(Accountant accountant);

}