package com.ego;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego
 * @version: 1.0
 */
@SpringBootApplication
//@EnableDubbo    //consumer可以省略
public class ManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class,args);
    }
}
