package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AccountAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.dto.JsonList;
import com.zhimeng.caiwuweb.service.SelectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by liupengfei on 2018/10/24 19:39
 */
@Api(description = "详情接口")
@RestController
public class selectJoinController {

    @Autowired
    SelectService selectService;

    @ApiOperation(value = "根据查询码或者id连表查询出差表详情")
    @PostMapping("/selectJoinTravel")
    @UserAccess
    @AccountAccess
    public JsonData selectJoinTravel(Integer id, String coding, HttpServletRequest request){

        if(coding==null && id == null){
            return new JsonData(202,"不能为空！");
        }
        JsonData jsonData = selectService.selectAccJoinTravel(id,coding,request);

        return jsonData;

    }

    @ApiOperation(value = "根据查询码连表查询值班表详情")
    @PostMapping("/selectZhiById")
    @UserAccess
    @AccountAccess
    public JsonData selectZhiById(Integer id,String coding,HttpServletRequest request){
        if(id==null && coding==null){
            return new JsonData(202,"查询码不能为空！");
        }
        JsonData jsonData = selectService.selectZhibanByCoding(id,coding,request);

        return jsonData;

    }

}
