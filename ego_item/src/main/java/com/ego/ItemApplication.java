package com.ego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego
 * @version: 1.0
 */
@SpringBootApplication
@EnableCaching
public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }
}
