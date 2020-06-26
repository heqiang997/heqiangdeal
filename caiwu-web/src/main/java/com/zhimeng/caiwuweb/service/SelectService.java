package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.dto.JsonList;
import com.zhimeng.caiwuweb.entity.DateVo;
import com.zhimeng.caiwuweb.entity.StringVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by liupengfei on 2018/10/18 20:21
 */
public interface SelectService {
    /**
     * 查询所有用户
     * @return
     */
    JsonData selectCus(String name,HttpServletRequest request);

    JsonData selectBackCus(String name);
    /**
     * 查询所有地区
     */
    JsonData selectCity(Integer travelStatus,Integer pid);


    /**
     * 2020-06-17
     * hewanli
     * 查询毕节单位信息可级联查询
     */
    JsonData selectUnit(Integer pid);


    /**
     * 根据父级ID查询本级以及子级相关的地区信息
     * @param pid
     * @return
     */
    JsonData selectDivisionsPid(Integer pid);

    /**
     * 查询所有车种
     */
    JsonData selectVehicle();

    /**
     * 根据编码查询统计
     */
    JsonData selectAccountCoding(String coding,HttpServletRequest request,Integer type);

    /**
     * 分页查询当前用户所有出差统计
     * @param request
     * @param travelType
     * @param veType
     * @param pageNum
     * @param pageSize
     * @param type  默认是升序，不为空则降序
     * @return
     */
    JsonData selectAccountAll(HttpServletRequest request, String name,Integer travelType, Integer veType, Integer pageNum, Integer pageSize, Integer type, Date start,Date end);

    /**
     * 分页查询当前用户所有值班记录
     * @param request
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    JsonData selectAccountAllZhiban(HttpServletRequest request,String name,Integer pageNum,Integer pageSize,Integer type,Date start,Date end);
    /**
     * 根据用户名查询travel带分页
     */
    JsonData selectTravelByName(String name,HttpServletRequest request,Integer pageNum,Integer pageSize);
    /**
     * 根据时间段查询travel带分页
     */
    JsonData selectTravelByTime(DateVo vo,HttpServletRequest request,Integer pageNum,Integer pageSize);
    /**
     * 根据用户名查询Zhiban带分页
     */
    JsonData selectZhibanByName(String name,HttpServletRequest request,Integer pageNum,Integer pageSize);
    /**
     * 根据时间段查询zhiban带分页
     */
    JsonData selectZhibanByTime(DateVo vo,HttpServletRequest request,Integer pageNum,Integer pageSize);

    /**
     * 查询用户未审核的值班和出差记录
     * @param request
     * @return
     */
    JsonList selectZhibanAndTravel(HttpServletRequest request);

    /**
     * 连表查询出差详情
     * @param id
     * @return
     */
    JsonData selectAccJoinTravel(Integer id,String coding,HttpServletRequest request);

    /**
     * 根据查询码查询值班
     * @param coding
     * @return
     */
    JsonData selectZhibanByCoding(Integer id,String coding,HttpServletRequest request);

    /**
     * id
     * @param id
     * @return
     */
//    JsonData selectByid(Integer id);

    /**
     * 出差删除记录
     * @param id
     * @return
     */
    JsonData deleteByCoding(Integer id,String coding);
    /**
     * 值班删除记录
     * @param id
     * @return
     */
    JsonData deleteZhiban(Integer id,String coding);


    /**
     * 删除所有出差记录
     * @return
     */
    JsonData deleteAll();
}
