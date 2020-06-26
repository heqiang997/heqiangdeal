package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liupengfei on 2018/10/31 11:24
 */
public interface WebService {

    /**
     * 查询当前登录用户所在地区的所有出差记录（未审核or已审核status0or1）
     */
    JsonData selectAccountStatus(HttpServletRequest request,Integer status,Integer paixu);

    /**
     * 查询当前登录用户所在地区的所有值班记录（未审核or已审核status0or1）
     */

    JsonData selectZhibanStatus(HttpServletRequest request,Integer status,Integer paixu);
}
