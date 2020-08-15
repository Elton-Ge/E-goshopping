package com.ego.passport.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class CookieConfig {

    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer cookie = new DefaultCookieSerializer();
        cookie.setCookieName("TT_TOKEN");
        cookie.setUseHttpOnlyCookie(false);   //当为false时， 脚本文件（js文件）就可以获取到cookie数据
        return cookie;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver(){
        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
        resolver.setCookieSerializer(cookieSerializer());
        return resolver;
    }
}
