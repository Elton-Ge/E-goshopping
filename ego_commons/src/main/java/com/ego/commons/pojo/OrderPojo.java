package com.ego.commons.pojo;

import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.commons.pojo
 * @version: 1.0
 */
public class OrderPojo implements Serializable {
    public static final long serialVersionUID=1L;

    private String paymentType;
    private String payment;

    private TbOrderShipping orderShipping;

    private List<TbOrderItem> orderItems;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    @Override
    public String toString() {
        return "OrderPojo{" +
                "paymentType='" + paymentType + '\'' +
                ", payment='" + payment + '\'' +
                ", orderShipping=" + orderShipping +
                ", orderItems=" + orderItems +
                '}';
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
