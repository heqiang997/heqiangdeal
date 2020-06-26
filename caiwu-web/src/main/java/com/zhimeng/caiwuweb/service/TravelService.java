package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Travel;
import com.zhimeng.caiwuweb.entity.TravelVo;

import java.util.List;
import java.util.Map;

public interface TravelService {

    /**
     * .录入审核单
     * @return
     */
    JsonData insertTravel(Integer travelStatus,String travelArea,Integer institutionid,List<String> names,List<Travel> list,Integer foodtype);

}
