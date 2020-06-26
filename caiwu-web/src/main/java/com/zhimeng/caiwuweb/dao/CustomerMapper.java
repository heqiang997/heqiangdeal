package com.zhimeng.caiwuweb.dao;

import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.entity.StringVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerMapper {

    int deleteByPrimaryKey(Integer cusId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer cusId);

    Customer selectByZhanghao(String zhanghao);

    Customer selectByName(String name);

    Customer selectByTel(String tel);

    List<Customer> selectAllRegion(String region);

    List<Customer> selectAll(String name,String region,String cusAuthority);

    List<Customer> selectBackAll(String name);

    int updateByPrimaryKeySelective(Customer record);

//    int updateAuthorityByName(Customer customer);
}