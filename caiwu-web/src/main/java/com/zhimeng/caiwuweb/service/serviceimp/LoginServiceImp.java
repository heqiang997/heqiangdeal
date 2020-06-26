package com.zhimeng.caiwuweb.service.serviceimp;

import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.dto.JsonData;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.service.LoginService;
import com.zhimeng.caiwuweb.util.MD5Utils;
import com.zhimeng.caiwuweb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by liupengfei on 2018/10/16 12:01
 */
@Service
public class LoginServiceImp implements LoginService {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    RedisUtil redisUtil;
    /**
     * 用户登录
     * @param zhanghao  用户账号
     * @param password  用户密码
     * @return
     */

    @Override
    public JsonData login(String zhanghao, String password, HttpServletRequest request) {
//        判定账号和密码不能为空
        if (zhanghao==null || zhanghao==""){
            return new JsonData(101,"账号不能为空！");
        }else if (password==null ||password==""){
            return  new JsonData(102,"密码不能为空！");
        }
        Customer customerSelect = customerMapper.selectByZhanghao(zhanghao);
//        判定是否有该用户
        if (customerSelect==null){
            return new JsonData(103,"没有该用户！");
        }
//        密码是否正确
        if (!MD5Utils.convertMD5(password).equals(customerSelect.getCusPwd())){
            return new JsonData(104,"密码不正确!");
        }
//      token存redis
        String token = UUID.randomUUID().toString().replaceAll("-","")+System.currentTimeMillis();
//        设置过期时间
        redisUtil.set(token,customerSelect.getCusName(),Long.valueOf(60 * 60 * 12));
        redisUtil.set(customerSelect.getCusName(),token,Long.valueOf(60 * 60 * 12));
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        map.put("customer",customerSelect);
        return new JsonData(200,"登录成功！",map);

    }

}
