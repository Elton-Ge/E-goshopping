package com.ego.cart.pojo;

import com.ego.commons.pojo.CartPojo;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.cart.pojo
 * @version: 1.0
 */
public class OrderCartPojo extends CartPojo {
    private Boolean enough;

    public Boolean getEnough() {
        return enough;
    }

    public void setEnough(Boolean enough) {
        this.enough = enough;
    }
}
