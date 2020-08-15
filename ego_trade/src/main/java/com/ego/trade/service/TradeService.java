package com.ego.trade.service;

import com.ego.commons.pojo.OrderPojo;

import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.trade.service
 * @version: 1.0
 */
public interface TradeService {
    /**
     * 前台要多个作用域，所以这里用map， 可以满足前台多个作用域取值，具体就是通过多个put（）来操作
     * @param orderPojo
     * @return
     */
    Map<String,Object> createOrder(OrderPojo orderPojo);
}
