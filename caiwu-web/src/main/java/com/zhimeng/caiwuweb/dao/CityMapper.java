package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Divisions;
import com.zhimeng.caiwuweb.entity.StringVo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    List<City> selectByPid(Integer id);

    List<Divisions> selectDivisionsPid(Integer id);

    List<City> selectByCity(String city);

    City selectByCityName(String city);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);

    /**
     * 查询毕节单位信息,可级联查询
     * 2020-06-17
     * hewanli
     * @param id 单位Id
     * @return
     */
    List<City> selectUnit(Integer id);
}