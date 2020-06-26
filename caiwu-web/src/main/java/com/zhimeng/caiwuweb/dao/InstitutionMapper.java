package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.Institution;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface InstitutionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Institution record);

    int insertSelective(Institution record);

    Institution selectByPrimaryKey(Integer id);

    Institution selectByName(String name);

    List<Institution> selectAll();

    int updateByPrimaryKeySelective(Institution record);

    int updateByPrimaryKey(Institution record);
}