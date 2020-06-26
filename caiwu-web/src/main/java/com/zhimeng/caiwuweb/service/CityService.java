package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.StringVo;

/**
 * Created by liupengfei on 2018/11/9 10:29
 */
public interface CityService {

    JsonData SelectAll(String name);

}
