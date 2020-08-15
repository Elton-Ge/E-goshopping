package com.ego.cart.config;

import com.ego.cart.interceptor.LoginIntercept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.cart.config
 * @version: 1.0
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginIntercept loginIntercept;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercept).addPathPatterns("/cart/order-cart.html");
    }
}


