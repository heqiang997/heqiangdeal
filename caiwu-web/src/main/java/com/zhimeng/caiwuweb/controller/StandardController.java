package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AdminAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.entity.Standard;
import com.zhimeng.caiwuweb.service.StandardService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liupengfei on 2018/11/4 21:08
 */
@RestController
public class StandardController {

    @Autowired
    StandardService standardService;

    @ApiOperation("修改费用标准")
    @ApiImplicitParam(name = "standard", dataType = "Standard")
    @PostMapping("/updateStandard")
    @UserAccess
    @AdminAccess
    public JsonData updateStandard(@RequestBody Standard standard){

        if (standard.getId()==null){
            return new JsonData(203,"id不能为空");
        }

        JsonData jsonData = standardService.updateStandard(standard);

        return jsonData;
    }

    @ApiOperation(value = "查询费用标准")
    @PostMapping("/selectStandardById")
    @UserAccess
    public JsonData selectStandardById(){

        JsonData jsonData = standardService.selectById(1);

        return jsonData;
    }

}
