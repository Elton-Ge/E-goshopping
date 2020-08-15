package com.ego.cart.service.impl;

import com.ego.cart.pojo.OrderCartPojo;
import com.ego.cart.service.CartService;
import com.ego.commons.pojo.CartPojo;
import com.ego.commons.pojo.EgoResults;
import com.ego.commons.pojo.TbItemDetails;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 9/8/20
 * @Description: com.ego.cart.service.impl
 * @version: 1.0
 */
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${ego.item.details.redisKey}")
    private String redisKey;       //商品详情的redisKey，前半部分
    @Value("${ego.cart.tempCart}")
    private String tempCart;  //临时购物车的cookie key；
    @Value("${ego.cart.userCart}")
    private String userCart;      //用户购物车的redis key,前半部分；
    @Reference
    private TbItemDubboService tbItemDubboService;

    @Override
    public void addCart(long id, int num) { //id是商品id
        /*用户登录了，用redis来保存用户购物车数据，redis的key为用户id，value为list大容器，list大容器存放的是<商品详情cartpojo>*/
        TbUser loginUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        if (loginUser != null) { //用户存在
            //userRedisKey 是 "userCart: 用户id"
            String userRedisKey = userCart + loginUser.getId();
            List<CartPojo> cartPojoList = new ArrayList<>();
            if (redisTemplate.hasKey(userRedisKey)) {
                cartPojoList = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
                //判断当前此商品是否存在在购物车中
                //如果存在 ，修改数量 
                for (CartPojo cartPojo : cartPojoList) {
                    if (cartPojo.getId().equals(id)) {
                        cartPojo.setNum(cartPojo.getNum() + num);
                        redisTemplate.opsForValue().set(userRedisKey, cartPojoList);
                        return;
                    }
                }
            }
            //如果不存在此商品，或者任何商品都没有，则创建cartpojo购物车商品
            // 通过redistemplate取出redis中的商品详情数据
            TbItemDetails tbItemDetails = (TbItemDetails) redisTemplate.opsForValue().get(redisKey + id);
            //封装到cartpojo

            CartPojo cartPojo = new CartPojo();
            cartPojo.setId(tbItemDetails.getId());
            cartPojo.setPrice(tbItemDetails.getPrice());
            cartPojo.setTitle(tbItemDetails.getTitle());
            cartPojo.setImages(tbItemDetails.getImages());
            cartPojo.setNum(num);
            cartPojoList.add(cartPojo);
            //用redis来保存用户购物车数据，redis的key为用户id，value为list大容器
            redisTemplate.opsForValue().set(userRedisKey, cartPojoList);
            return;
        }


        /*用户没有登录时，关于临时购物车的操作,临时购物车通过cookie来保存map大容器，map大容器装着<key商品id,value商品详情cartpojo>
         *因为cookie可以临时保存客户端数据，所以可以设计成临时购物车 */
        Map<Long, CartPojo> tempMap = new HashMap<>();    //此处用map作容器不需遍历，如果用list则需要遍历。
        //1. 第一次操作：判断cookie之前是否有任何数据，
        String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCart);
        if (cookieValue != null && !cookieValue.isEmpty()) {//如果不是空，则已经存在临时购物车商品
            tempMap = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
            if (tempMap.containsKey(id)) {
                ////1.1判断是否已经存在当前这个同样的商品 .如果存在，只需要修改商品数量集合
                CartPojo cartPojo = tempMap.get(id);
                cartPojo.setNum(cartPojo.getNum() + num);
//                tempMap.put(id, cartPojo);  //这里value-cartPojo由于是引用数据类型，所有修改cartPojo后，不用再放回map中
                CookieUtils.doSetCookieBase64(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCart, JsonUtils.objectToJson(tempMap), 2592000);
                return;
            }
        }
        //2. 如果不存在此商品，或者任何商品都没有，则创建cartpojo购物车商品
        // 通过redistemplate取出redis中的商品详情数据
        TbItemDetails tbItemDetails = (TbItemDetails) redisTemplate.opsForValue().get(redisKey + id);
        //封装到cartpojo
        CartPojo cartPojo = new CartPojo();
        cartPojo.setId(tbItemDetails.getId());
        cartPojo.setPrice(tbItemDetails.getPrice());
        cartPojo.setTitle(tbItemDetails.getTitle());
        cartPojo.setImages(tbItemDetails.getImages());
        cartPojo.setNum(num);
        //3放入到大map中
        tempMap.put(id, cartPojo);

        //4 放入到cookie中，cookie就是临时购物车
        CookieUtils.doSetCookieBase64(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCart, JsonUtils.objectToJson(tempMap), 2592000);
    }

    @Override
    public List<CartPojo> showCart() {
        List<CartPojo> list = new ArrayList<>();
        /*针对用户购物车查看*/
        TbUser loginUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        if (loginUser != null) {
            //userRedisKey 是 "userCart: 用户id"
            String userRedisKey = userCart + loginUser.getId();
            list = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
            return list;
        }
        /*针对临时购物车查看显示*/
        //判断cookie之前是否有任何数据
        String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCart);
        System.out.println(cookieValue);
        if (Strings.isNotEmpty(cookieValue)) {//如果不是空，则已经存在临时购物车商品
            Map<Long, CartPojo> map = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
            for (Long id : map.keySet()) {
                list.add(map.get(id));
            }
        }
        return list;
    }

    @Override
    public EgoResults updateCart(long id, int num) {
        List<CartPojo> list = new ArrayList<>();
        /*针对用户购物车修改商品数量*/
        TbUser loginUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        if (loginUser != null) {
            //userRedisKey 是 "userCart: 用户id"
            String userRedisKey = userCart + loginUser.getId();
            list = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
            for (CartPojo cartPojo : list) {
                if (cartPojo.getId().equals(id)) {
                    cartPojo.setNum(num);
                    redisTemplate.opsForValue().set(userRedisKey, list);
                    return EgoResults.ok();
                }
            }
        }
        /*针对临时购物车修改商品数量*/
        String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCart);
        System.out.println(cookieValue);
        Map<Long, CartPojo> map = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
        map.get(id).setNum(num);
        CookieUtils.doSetCookieBase64(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCart, JsonUtils.objectToJson(map), 2592000);
        return EgoResults.ok();
    }

    @Override
    public EgoResults deleteCart(long id) { //商品id
        List<CartPojo> list = new ArrayList<>();
        /*针对用户购物车删除商品详情*/
        TbUser loginUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        if (loginUser != null) {
            //userRedisKey 是 "userCart: 用户id"
            String userRedisKey = userCart + loginUser.getId();
            list = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
            for (CartPojo cartPojo : list) {
                if (cartPojo.getId().equals(id)) {
                    list.remove(cartPojo);
                    redisTemplate.opsForValue().set(userRedisKey, list);
                    return EgoResults.ok();
                }
            }
        }
        /*针对临时购物车删除商品详情*/
        String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCart);
        Map<Long, CartPojo> map = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
        map.remove(id);
        CookieUtils.doSetCookieBase64(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCart, JsonUtils.objectToJson(map), 2592000);
        return EgoResults.ok();
    }

    @Override
    public List<OrderCartPojo> showOrderCart(List<Long> ids) {
        TbUser loginUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
        String userRedisKey = userCart + loginUser.getId();
        List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
        List<OrderCartPojo> resultList = new ArrayList<>();
        for (Long id : ids) {
            for (CartPojo cartPojo : list) {
                if (cartPojo.getId().equals(id)) {
                    OrderCartPojo orderCartPojo = new OrderCartPojo();
                    BeanUtils.copyProperties(cartPojo, orderCartPojo);   //  copy
                    TbItem tbItem = tbItemDubboService.selectById(id);
                    if (cartPojo.getNum() <= tbItem.getNum()) {
                        orderCartPojo.setEnough(true);
                    } else {
                        orderCartPojo.setEnough(false);
                    }
                    resultList.add(orderCartPojo);
                    break;
                }
            }
        }
        return resultList;
    }

    @Override
    public int deleteByUserIdItemId(long userId, long [] itemId) {
        try {
            String userRedisKey = userCart + userId;
            List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
            for (Long id: itemId){
                for (CartPojo cartPojo : list) {
                    if (cartPojo.getId().equals(id)) {
                        list.remove(cartPojo);
                        break;
                    }
                }
            }
            redisTemplate.opsForValue().set(userRedisKey, list);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}
