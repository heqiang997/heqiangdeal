package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.exception.AuthorityNotException;
import com.zhimeng.caiwuweb.service.LoginService;
import com.zhimeng.caiwuweb.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liupengfei on 2018/10/15 9:57
 */

@Api(description = "登录接口")
@RestController
public class LoginController {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginService LoginService;
    @Autowired
    CustomerMapper customerMapper;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "web后台登录传1"),
            @ApiImplicitParam(name = "cusZhanghao",value = "账号"),
            @ApiImplicitParam(name = "cusPwd",value = "密码")
    })
    public JsonData login(String cusZhanghao,String cusPwd,@RequestParam(defaultValue = "0") Integer id,HttpServletRequest request){

        if (id==1){
            Customer customer1 = customerMapper.selectByZhanghao(cusZhanghao);
            if (customer1.getCusAuthority()==1){
                throw new AuthorityNotException("没有权限登录");
            }
        }

        JsonData login = LoginService.login(cusZhanghao,cusPwd,request);

        return login;
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/loginout")
    @UserAccess
    public JsonData loginout(HttpServletRequest request){

        String authorization = request.getHeader("Authentication");

        redisUtil.remove(authorization);

        String customer = redisUtil.get(authorization);

        if (customer==null || customer.equals("")){
            return new JsonData(200,"退出成功");
        }
        return new JsonData(202,"退出失败！");
    }
}
