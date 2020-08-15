package com.ego.cart.controller;

import com.ego.cart.service.CartService;
import com.ego.commons.pojo.EgoResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 9/8/20
 * @Description: com.ego.cart.controller
 * @version: 1.0
 */
@Controller
public class CartController {
    @Autowired
    private CartService cartService;
    @RequestMapping("/cart/add/{id}.html")
    public String addCart(@PathVariable long id,int num){
        cartService.addCart(id, num);
        return "cartSuccess";
    }
    @RequestMapping("/cart/cart.html")
    public String  showCart(Model model){
        model.addAttribute("cartList",cartService.showCart());
        return "cart";
    }
    @RequestMapping(path = {"/cart/update/num/{id}/{num}.action","/service/cart/update/num/{id}/{num}"})
    @ResponseBody
    public EgoResults updateCart(@PathVariable long id, @PathVariable int num){
        return cartService.updateCart(id, num);
    }
    @RequestMapping("/cart/delete/{id}.action")
    @ResponseBody
    public EgoResults deleteCart(@PathVariable long id){
        return  cartService.deleteCart(id);
    }

    @RequestMapping("/cart/order-cart.html")
    public String toPay(@RequestParam("id")List<Long> id, Model model){ //用集合去接收前台参数
        model.addAttribute("cartList",cartService.showOrderCart(id));
//        System.out.println(id);
        return  "order-cart";
    }
    @RequestMapping("/cart/delete")
    @ResponseBody
    public int deleteByUserIdItemId(long userId,long[] itemId ){
        return cartService.deleteByUserIdItemId(userId, itemId);
    }
}
