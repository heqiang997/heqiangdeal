package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Area;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Customer;

import java.util.List;
import java.util.Set;

/**
 * Created by liupengfei on 2018/11/7 13:12
 */
public interface ExcelService {
    /**
     * 导入地区
     * @param list
     * @return
     */
    JsonData inputArea(List<City> list);

    /**
     * 导入用户
     * @param list
     * @return
     */
    JsonData inputCustomer(Set<Customer> list);

}
