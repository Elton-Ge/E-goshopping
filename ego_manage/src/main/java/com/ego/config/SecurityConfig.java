package com.ego.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego.config
 * @version: 1.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public PasswordEncoder getPE(){
        return  new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")  //与页面的jsp，表单请求url一致
                .successForwardUrl("/loginSuccess");
        http.authorizeRequests()
                .antMatchers("/","/css/**","/js/**").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable();
        //不在响应头设置x-frame-option：deny
        http.headers().frameOptions().disable();

    }
}
