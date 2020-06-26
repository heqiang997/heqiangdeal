package com.zhimeng.caiwuweb.controller;

import com.zhimeng.caiwuweb.aop.AccountAccess;
import com.zhimeng.caiwuweb.aop.AdminAccess;
import com.zhimeng.caiwuweb.aop.UserAccess;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.dto.JsonList;
import com.zhimeng.caiwuweb.entity.DateVo;
import com.zhimeng.caiwuweb.entity.StringVo;
import com.zhimeng.caiwuweb.service.InstitutionService;
import com.zhimeng.caiwuweb.service.SelectService;
import com.zhimeng.caiwuweb.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liupengfei on 2018/10/17 15:24
 */
@RestController
@Api(description = "所有查询接口")
public class SelectController {

    @Autowired
    SelectService selectService;

    @Autowired
    InstitutionService institutionService;

    @ApiOperation(value = "查询登陆用户所在地区的所有用户")
    @PostMapping("/selectCus")
    @UserAccess
    public JsonData selectCus(String name,HttpServletRequest request) {

        JsonData jsonData = selectService.selectCus(name,request);

        return jsonData;

    }

    @ApiOperation(value = "查询所有用户")
    @PostMapping("/selectBackCus")
    @UserAccess
    public JsonData selectBackCus(String name) {

        JsonData jsonData = selectService.selectBackCus(name);

        return jsonData;

    }

    @ApiOperation(value = "查询所有地区不带分页主要给选择框")
    @PostMapping("/selectArea")
    @UserAccess
    public JsonData selectCity(@RequestParam(required = false,defaultValue = "0")Integer travelStatus,@RequestParam(defaultValue = "0") Integer pid) {

        JsonData jsonData = selectService.selectCity(travelStatus,pid);

        return jsonData;

    }


    /**
     * 2020-06-17
     * hewanli
     * @param pid  单位ID  默认 112   表示毕节
     * @return
     */
    @ApiOperation(value = "查询单位主要是毕节级联下的单位信息")
    @PostMapping("/selectUnit")
    @UserAccess
    public JsonData selectUnit(@RequestParam(defaultValue = "112") Integer pid) {

        JsonData jsonData = selectService.selectUnit(pid);

        return jsonData;

    }


    @ApiOperation(value = "查询所有交通工具种类")
    @PostMapping("/selectVehicle")
    @UserAccess
    public JsonData selectVehicle() {

        JsonData jsonData = selectService.selectVehicle();

        return jsonData;

    }

    @ApiOperation(value = "查询所有单位")
    @PostMapping("/selectAllInstitution")
    @UserAccess
    public JsonData selectAllInstitution() {

        JsonData jsonData = institutionService.selectAll();

        return jsonData;

    }

    @ApiOperation(value = "根据查询码查询记录（包括出差统计和值班）")
    @PostMapping("/selectByCoding")
    @UserAccess
    @AccountAccess
    public JsonData selectByCoding(String coding, HttpServletRequest request,Integer type) {

        if (coding == null) {
            new JsonData(678, "查询码不能为空！");
        }

        JsonData jsonData = selectService.selectAccountCoding(coding, request,type);

        return jsonData;

    }

    @ApiOperation(value = "根据用户名查找出差判重记录")
    @PostMapping("/selectTravelByName")
    @UserAccess
    @AccountAccess
    public JsonData selectTravelByName(String name, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "6") Integer pageSize) {

        if (name == null) {
            return new JsonData(777, "用户名不能为空！");
        }

        JsonData jsonData = selectService.selectTravelByName(name, request, pageNum, pageSize);

        return jsonData;

    }

    @ApiOperation(value = "根据用户名查找值班记录")
    @PostMapping("/selectZhibanByName")
    @UserAccess
    @AccountAccess
    public JsonData selectZhibanByName(String name, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "6") Integer pageSize) {

        if (name == null) {
            return new JsonData(777, "用户名不能为空！");
        }

        JsonData jsonData = selectService.selectZhibanByName(name, request, pageNum, pageSize);

        return jsonData;

    }

    @ApiOperation(value = "根据时间段查找当前用户出差判重记录")
    @PostMapping("/selectTravelByTime")
    @UserAccess
    @AccountAccess
    public JsonData selectTravelByTime(@RequestBody DateVo dateVo, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "6") Integer pageSize) {

        if (dateVo.getStart() == null || dateVo.getEnd() == null) {
            return new JsonData(779, "开始日期和结束日期不能为空！");
        }
        if (dateVo.getEnd().getTime() < dateVo.getStart().getTime()) {
            return new JsonData(800, "结束日期不能大于开始日期！");
        }

        JsonData jsonData = selectService.selectTravelByTime(dateVo, request, pageNum, pageSize);

        return jsonData;

    }

    @ApiOperation(value = "根据时间段查找当前用户值班记录")
    @PostMapping("/selectZhibanByTime")
    @UserAccess
    @AccountAccess
    public JsonData selectZhibanByTime(@RequestBody DateVo dateVo, HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "6") Integer pageSize) {

        if (dateVo.getStart() == null || dateVo.getEnd() == null) {
            return new JsonData(779, "开始日期和结束日期不能为空！");
        }
        if (dateVo.getEnd().getTime() < dateVo.getStart().getTime()) {
            return new JsonData(800, "结束日期不能大于开始日期！");
        }

        JsonData jsonData = selectService.selectZhibanByTime(dateVo, request, pageNum, pageSize);

        return jsonData;

    }

    @ApiOperation(value = "用户查询默认显示登陆用户所有出差记录（带各种条件查询和分页，排序）")  // 2020-06-17 添加单位过滤
    @PostMapping("/selectAccountAll")
    @UserAccess
    @AccountAccess
    public JsonData selectAccountAll(HttpServletRequest request, String name, Integer travelType, Integer veType, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "500") Integer pageSize, Integer type, Long start, Long end) {

        Date startDa = null;
        Date endDa = null;

        if (start != null  && end != null) {
            startDa = DateUtil.getStartOfDay(new Date(start));
            endDa = DateUtil.getEndOfDay(new Date(end));
        }

        JsonData jsonData = selectService.selectAccountAll(request, name, travelType, veType, pageNum, pageSize, type, startDa, endDa);

        return jsonData;

    }

    @ApiOperation(value = "查询当前登录用户所在地区所有值班记录（带分页和排序,时间，姓名、单位）")       //  2020-06-17 添加单位过滤查询
    @PostMapping("/selectAccountAllZhiban")
    @UserAccess
    @AccountAccess
    public JsonData selectAccountAllZhiban(HttpServletRequest request, String name, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "500") Integer pageSize, Integer paixu,Long start,Long end) {

        Date startDa = null;
        Date endDa = null;

        if (start != null  && end != null) {
            startDa = DateUtil.getStartOfDay(new Date(start));
            endDa = DateUtil.getEndOfDay(new Date(end));
        }
        System.out.println("11111");
        JsonData jsonData = selectService.selectAccountAllZhiban(request,name, pageNum, pageSize, paixu,startDa,endDa);
        System.out.println("2222");
        System.out.println(jsonData);
        return jsonData;

    }

    @ApiOperation(value = "查询登录用户所有记录（包括值班和出差统计）")
    @PostMapping("/selectAccAndTravel")
    @UserAccess
    public JsonList selectAccAndTravel(HttpServletRequest request) {

        JsonList jsonData = selectService.selectZhibanAndTravel(request);

        return jsonData;
    }

    @ApiOperation(value = "出差删除记录")
    @PostMapping("/delete")
    @UserAccess
    @AccountAccess
    public JsonData delete(Integer id,String coding) {

        JsonData jsonData = selectService.deleteByCoding(id,coding);

        return jsonData;
    }


    @ApiOperation(value = "值班删除记录")
    @PostMapping("/deleteZhiban")
    @UserAccess
    @AccountAccess
    public JsonData deleteZhiban(Integer id,String coding) {

        JsonData jsonData = selectService.deleteZhiban(id,coding);

        return jsonData;
    }

    @ApiOperation(value = "删除所有出差记录")
    @PostMapping("/deleteAll")
    @UserAccess
    @AccountAccess
    public JsonData deleteAll() {

        JsonData jsonData = selectService.deleteAll();

        return jsonData;
    }
}
