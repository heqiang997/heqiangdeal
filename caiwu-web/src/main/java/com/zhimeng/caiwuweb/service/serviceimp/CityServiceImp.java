package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.CityMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.StringVo;
import com.zhimeng.caiwuweb.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liupengfei on 2018/11/9 10:29
 */
@Service
public class CityServiceImp implements CityService {

    @Autowired
    CityMapper cityMapper;

    @Override
    public JsonData SelectAll(String name) {

        List<City> citys = cityMapper.selectByCity(name);

        if (citys==null){
            return new JsonData(204,"没有该地区");
        }

        return new JsonData(200,"查询成功！",citys);
    }
}
