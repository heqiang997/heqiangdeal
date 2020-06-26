package com.zhimeng.caiwuweb.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author:paul
 * @Description:
 */
public class CookieUtils {
    /**
     * 设置cookie
     */
    public static void set(HttpServletResponse response, String name, String value, int max) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(max);
        response.addCookie(cookie);
    }


    public static Cookie get(HttpServletRequest request, String name)
    {
        Map<String, Cookie> cookieMap = readCookieMap(request);
         if (cookieMap.containsKey(name)) {
           return cookieMap.get(name);
        } else {
           return null;
        }
    }

    public static Map<String, Cookie> readCookieMap(HttpServletRequest request) {

        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }

        return cookieMap;
    }
}
