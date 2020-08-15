package com.ego.cart.service;

import com.ego.cart.pojo.OrderCartPojo;
import com.ego.commons.pojo.CartPojo;
import com.ego.commons.pojo.EgoResults;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 9/8/20
 * @Description: com.ego.cart.service
 * @version: 1.0
 */
public interface CartService {
    //购物车页面要什么
    void  addCart(long id, int num);

    List<CartPojo> showCart();

    EgoResults updateCart(long id, int num);

    EgoResults deleteCart(long id);

    List<OrderCartPojo> showOrderCart(List<Long> ids);

    int deleteByUserIdItemId(long userId, long [] itemId);
}
