package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AccountAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.dto.ListVo;
import com.zhimeng.caiwuweb.entity.Accountant;
import com.zhimeng.caiwuweb.entity.StringVo;
import com.zhimeng.caiwuweb.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liupengfei on 2018/10/25 10:46
 */
@RestController
@Api(description = "会计审核接口")
public class AccountController {

    @Autowired
    AccountService accountService;

    @ApiOperation(value = "会计审核")
    @PostMapping("/updateAcount")
    @UserAccess
    @AccountAccess
    public JsonData updateAcount(@RequestBody StringVo stringVo){

        JsonData jsonData = accountService.updateStatus(stringVo.getCoding());

        return jsonData;

    }

}
