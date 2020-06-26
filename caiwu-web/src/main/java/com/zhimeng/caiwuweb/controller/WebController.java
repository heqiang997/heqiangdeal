package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AccountAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.StringVo;
import com.zhimeng.caiwuweb.service.CityService;
import com.zhimeng.caiwuweb.service.WebService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liupengfei on 2018/10/31 11:20
 */
@RestController
public class WebController {

    @Autowired
    WebService webService;
    @Autowired
    CityService cityService;

    @ApiOperation(value = "当前登录用户所在地区所有出差记录（status0未审核，status1审核完成）")
    @PostMapping("/selectAllAccountStatus")
    @UserAccess
    @AccountAccess
    public JsonData selectAllAccountStatus(HttpServletRequest request,@RequestBody(required = false) StringVo stringVo){

        JsonData jsonData = webService.selectAccountStatus(request,stringVo.getStatus(),stringVo.getPaixu());

        return jsonData;
    }

    @ApiOperation(value = "当前登录用户所在地区所有值班记录（status0未审核，status1审核完成）")
    @PostMapping("/selectAllZhibanStatus")
    @UserAccess
    @AccountAccess
    public JsonData selectAllZhibanStatus(HttpServletRequest request,@RequestBody(required = false) StringVo stringVo){

        JsonData jsonData = webService.selectZhibanStatus(request, stringVo.getStatus(),stringVo.getPaixu());

        return jsonData;

    }

    @ApiOperation(value = "查询所有地区（根据城市名称查）")
    @PostMapping("/selectAllCityByName")
    @UserAccess
    @AccountAccess
    public JsonData selectAllCityByName(@RequestParam(required = false) String name){

        JsonData jsonData = cityService.SelectAll(name);

        return jsonData;

    }


}
