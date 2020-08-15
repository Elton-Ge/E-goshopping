package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego
 * @version: 1.0
 */
@SpringBootApplication
@EnableDubbo
@MapperScan("com.ego.mapper")    //扫描mapper
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
