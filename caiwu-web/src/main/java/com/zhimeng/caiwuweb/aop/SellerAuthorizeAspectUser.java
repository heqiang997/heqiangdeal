package com.zhimeng.caiwuweb.aop;


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
import javax.servlet.http.HttpSession;

/**
 * @Author:paul
 * @Description:
 */
@Component
@Aspect
public class SellerAuthorizeAspectUser {
    @Autowired
    private RedisUtil redisUtil;
    @Pointcut(value = "@annotation(com.zhimeng.caiwuweb.aop.UserAccess)")
    void access() {

    }
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
        String ken = redisUtil.get(tokenValue);
//
//        System.out.println("Authentication:"+token);
//        System.out.println("ken:"+ken);

        if (tokenValue==null){
            throw new TokenNullException("token已过期");
        }
        if (!token.equals(ken)){
            throw new AuthorityNotException("此账号已在别处登录，您已下线！");
        }
        //redis查询为空
        if (tokenValue==null ||tokenValue.equals("")) {
            throw new TokenNullException("请先登录");
        }

    }



}
