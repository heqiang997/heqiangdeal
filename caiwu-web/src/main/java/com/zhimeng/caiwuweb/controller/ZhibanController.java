package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Zhiban;
import com.zhimeng.caiwuweb.entity.ZhibanVo;
import com.zhimeng.caiwuweb.service.ZhibanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liupengfei on 2018/10/17 10:58
 */
@RestController
@Api(description = "值班录入接口")
public class ZhibanController {

    @Autowired
    ZhibanService zhibanService;

    @PostMapping("/insertZhiban")
    @ApiOperation(value = "录入接口",notes = "值班录入接口")
    @ApiImplicitParam(name = "zhiban",value = "用户名和值班日期必填",required = true,dataType = "ZhibanVo")
    @UserAccess
    public JsonData zhibanIn(@RequestBody ZhibanVo zhiban){

        if (zhiban==null){
            return new JsonData(502,"信息不能为空！");
        }

        JsonData jsonData = zhibanService.insertZhiban(zhiban.getList());

        return jsonData;
    }

}
