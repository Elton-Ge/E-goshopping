package com.ego.cart.interceptor;

import com.ego.pojo.TbUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.cart.interceptor
 * @version: 1.0
 */
@Component
public class LoginIntercept implements HandlerInterceptor {
    @Value("${ego.passport.loginUrl}")
    private String loginUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        TbUser loginUser = (TbUser) request.getSession().getAttribute("loginUser");
        if (loginUser!=null){
//            System.out.println("登录成功");
            return true;
        }
        response.sendRedirect(loginUrl);
        return false;
    }
}
