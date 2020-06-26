package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liupengfei on 2018/10/16 15:02
 */
public interface CustomerService {
    /**
     * 新增用户
     * @param customer 用户实体
     * @return
     */
    JsonData addCustomer(Customer customer);

    /**
     * 修改用户密码
     * @param password 旧密码
     * @param newpassword 新密码
     */
    JsonData updateCustomer(String password,String newpassword,HttpServletRequest request);

    JsonData updateAuth(Customer customer);

    JsonData updateCity(City city);
    /**
     * 初始化用户秘密
     * @param customer 用户实体
     * @return
     */
    JsonData initPwd(Customer customer);

    /**
     * 判断用户是否存在
     * @param name
     * @return
     */
    JsonData cusIsExist(String name);

}
