package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Standard;
import io.swagger.models.auth.In;

/**
 * Created by liupengfei on 2018/11/4 21:10
 */
public interface StandardService {

    JsonData updateStandard(Standard standard);

    JsonData selectById(Integer id);

}
