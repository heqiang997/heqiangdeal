package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.Vehicle;

import java.util.List;

public interface VehicleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Vehicle record);

    int insertSelective(Vehicle record);

    Vehicle selectByPrimaryKey(Integer id);

    Vehicle selectByVehicle(String ve);

    int updateByPrimaryKeySelective(Vehicle record);

    int updateByPrimaryKey(Vehicle record);

    List<Vehicle> selectAll();
}