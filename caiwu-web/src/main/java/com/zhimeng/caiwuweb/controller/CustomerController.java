package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AdminAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dao.CityMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.City;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.entity.StringVo;
import com.zhimeng.caiwuweb.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by liupengfei on 2018/10/16 14:59
 */

@Api(description = "用户接口")
@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;
    @Autowired
    CityMapper cityMapper;

    @ApiOperation("新增用户")
    @ApiImplicitParam(name = "customer",value = "" +
            "必须填写用户名（cusName）、用户手机号(cusTel)、所在地区(cusRegion)，所属角色(CusAuthority)",dataType = "Customer")
    @PostMapping("/addCustomer")
    @UserAccess
    @AdminAccess
    public JsonData addCustomer(@RequestBody Customer customer){

        if (customer.getCusPwd().length()<6){
            return new JsonData(205,"密码长度不能低于6位");
        }

        JsonData jsonData = customerService.addCustomer(customer);

        return jsonData;
    }
    @ApiOperation("初始化用户密码")
    @ApiImplicitParam(name = "customer",value = "cusZhanghao用户账号",dataType = "Customer")
    @PostMapping("/initPwd")
    public JsonData initPwd(@RequestBody Customer customer){

        JsonData jsonData = customerService.initPwd(customer);

        return jsonData;
    }

    @ApiOperation("修改个人密码")
    @PostMapping("/updateCus")
    @UserAccess
    public JsonData updateCus(@RequestBody StringVo stringVo, HttpServletRequest request){

        if (stringVo.getPassword().trim().equals("") || stringVo.getNewpassword().trim().equals("")
                ||stringVo.getPassword()==null || stringVo.getNewpassword()==null
        ){
            return new JsonData(204,"参数不能为空！");
        }
        if (stringVo.getNewpassword().length()<6){
            return new JsonData(205,"密码长度不能低于6位");
        }
        JsonData jsonData = customerService.updateCustomer(stringVo.getPassword(),stringVo.getNewpassword(),request);

        return jsonData;
    }

    @ApiOperation("修改角色，只有系统管理员能修改其他人的权限")
    @ApiImplicitParam(name = "customer",value = "修改账户的所属权限，用户名(cusName)，角色字段（cusAuthority1用户2会计，3超管）",
            dataType = "Customer")
    @PostMapping("/updateAuth")
    @UserAccess
    @AdminAccess
    public JsonData updateAuth(@RequestBody Customer customer){

        if (customer.getCusPwd().trim().length()<6){
            return new JsonData(205,"密码长度不能低于6位");
        }

        if (customer.getCusPwd().trim().equals("") || customer.getCusPwd()==null){
            return new JsonData(205,"密码不能为空");
        }

        if (customer.getCusZhanghao().trim().equals("")){
            return new JsonData(205,"账号不能为空！");
        }
        JsonData jsonData = customerService.updateAuth(customer);

        return jsonData;
    }

    @ApiOperation("修改地区，只有系统管理员能修改其他人的权限")
    @ApiImplicitParam(name = "city",value = "修改城市名称，所属上级pid,类型",
            dataType = "City")
    @PostMapping("/updateCity")
    @UserAccess
    @AdminAccess
    public JsonData updateCity(@RequestBody City city){

        if (city.getCityname().trim().equals("")){
            return new JsonData(205,"城市名称不能为空");
        }

        if (city.getPid()==null){
            return new JsonData(205,"所属上级城市id不能为空");
        }

        if (city.getType()==null){
            return new JsonData(205,"城市类型不能为空");
        }

        JsonData jsonData = customerService.updateCity(city);

        return null;
    }

    @ApiOperation("新增地区")
    @ApiImplicitParam()
    @PostMapping("/addCity")
    @UserAccess
    @AdminAccess
    public JsonData addCity(@RequestBody City city){

        if (city.getCityname()==null){
            return new JsonData(301,"城市名称不能为空");
        }

        int i = cityMapper.insertSelective(city);
        if (i!=0){
            return new JsonData(200,"新增成功！");
        }

        return new JsonData(500,"新增失败！");
    }

    @ApiOperation("判断用户是否存在")
    @PostMapping("/cusIsExist")
    @UserAccess
    public JsonData cusIsExist(String name){

    if (name==null ||name.trim().equals("")){
        return new JsonData(406,"用户不能为空！");
    }
        JsonData jsonData = customerService.cusIsExist(name);

        return jsonData;
    }

}
