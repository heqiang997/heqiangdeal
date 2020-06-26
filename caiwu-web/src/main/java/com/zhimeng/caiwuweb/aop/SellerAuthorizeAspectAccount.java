package com.zhimeng.caiwuweb.aop;


import com.zhimeng.caiwuweb.dao.CustomerMapper;
import com.zhimeng.caiwuweb.entity.Customer;
import com.zhimeng.caiwuweb.exception.AuthorityNotException;
import com.zhimeng.caiwuweb.exception.TokenNullException;
import com.zhimeng.caiwuweb.util.RedisUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:paul
 * @Description:
 */
@Component
@Aspect
public class SellerAuthorizeAspectAccount {

    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Pointcut(value = "@annotation(com.zhimeng.caiwuweb.aop.AccountAccess)")
    void access() {}

    @Before("access()")
    void doVerify() throws Exception {
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String token = request.getHeader("Authentication");

        if (token==null ||token.equals("")){
            throw new TokenNullException("请先登录");
        }
        //从redis中查询
        String tokenValue = redisUtil.get(token);

        if (tokenValue==null){
            throw new TokenNullException("token已过期");
        }

        Customer customer = customerMapper.selectByName(tokenValue);

        if (customer.getCusAuthority()==1){
            throw new AuthorityNotException("没有权限查询");
        }

    }



}
