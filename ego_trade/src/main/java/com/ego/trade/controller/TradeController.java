package com.ego.trade.controller;

import com.ego.commons.pojo.OrderPojo;
import com.ego.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.trade.controller
 * @version: 1.0
 */
@Controller
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @RequestMapping("/order/create.html")
    public String createOrder(OrderPojo orderPojo, Model model) {
//        System.out.println(orderPojo);
        Map<String, Object> map = tradeService.createOrder(orderPojo);
        if (map != null) {
            model.addAllAttributes(map);
            return "success";
        }
        model.addAttribute("message", "订单创建失败");
        return "error/exception";
    }
}
