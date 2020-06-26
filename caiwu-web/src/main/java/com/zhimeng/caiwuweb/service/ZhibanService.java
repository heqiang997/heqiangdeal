package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Zhiban;

import java.util.List;

public interface ZhibanService {
    /**
     * 录入值班审核记录
     * @return
     */
    JsonData insertZhiban(List<Zhiban> zhiban);


}
