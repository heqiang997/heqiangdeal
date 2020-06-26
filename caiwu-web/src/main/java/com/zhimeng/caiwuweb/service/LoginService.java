package com.zhimeng.caiwuweb.service;

import com.zhimeng.caiwuweb.dto.JsonData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liupengfei on 2018/10/16 11:59
 */
public interface LoginService {
    /**
     * 用户登录
     * @param zhanghao  用户账号
     * @param password  用户密码
     * @return
     */
    JsonData login(String zhanghao, String password,HttpServletRequest request);

}
