package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.TravelVo;
import com.zhimeng.caiwuweb.service.TravelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by liupengfei on 2018/10/16 17:49
 */
@RestController
@Api(description = "差旅审核录入接口")
public class TravelController {

    @Autowired
    TravelService travelService;

    /**
     * 录入差旅审核单
     * @return
     */
    @ApiOperation(value = "录入差旅",notes = "差旅审核录入接口")
    @ApiImplicitParam(name = "travelVo",value = "Travel的List集合包装类," +
            "用户名，出差日期，出差地区id必填，是否当日往返(0否1是),是否是培训或者会议(0否1是),"
            +"travelStatus出差地区类型，travelArea出差地点，institutionid单位id",
            required = true,dataType = "TravelVo")
    @PostMapping("/inisertTravel")
    @UserAccess
    public JsonData insertTravel(@RequestBody TravelVo travelVo){

        JsonData jsonData = travelService.insertTravel(travelVo.getTravelStatus(),travelVo.getTravelArea(),travelVo.getInstitutionid(),travelVo.getNames(),travelVo.getList(),travelVo.getFoodtype());

        return jsonData;

    }

}
