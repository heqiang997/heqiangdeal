package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.AreaMapper;
import com.zhimeng.caiwuweb.dao.CityMapper;
import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.dao.InstitutionMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Area;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.entity.Institution;
import com.zhimeng.caiwuweb.service.ExcelService;
import com.zhimeng.caiwuweb.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by liupengfei on 2018/11/7 13:12
 */
@Service
public class ExcelServiceImp implements ExcelService {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    CityMapper cityMapper;
    @Autowired
    InstitutionMapper institutionMapper;
    @Override
    public JsonData inputArea(List<City> list) {

        for (City ci : list) {
            cityMapper.insertSelective(ci);
        }

        return new JsonData(200,"导入成功！");
    }

    @Override
    public JsonData inputCustomer(Set<Customer> list) {

        for (Customer customer : list) {

            Institution institution = institutionMapper.selectByName(customer.getCusRegion());
            if (institution==null){
                Institution ins = new Institution();
                ins.setInstitutionName(customer.getCusRegion());
                institutionMapper.insertSelective(ins);
            }
            customer.setCusFirstName(StringUtil.StringToFirstChar(customer.getCusName())+customer.getCusName());
            customerMapper.insertSelective(customer);
        }

        return new JsonData(200,"导入成功！");
    }
}
