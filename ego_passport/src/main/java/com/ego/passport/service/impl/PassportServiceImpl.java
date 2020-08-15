package com.ego.passport.service.impl;

import com.ego.commons.pojo.CartPojo;
import com.ego.commons.pojo.EgoResults;
import com.ego.commons.utils.CookieUtils;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.commons.utils.ServletUtil;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.passport.service.PassportService;
import com.ego.pojo.TbUser;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 8/8/20
 * @Description: com.ego.passport.service.impl
 * @version: 1.0
 */
@Service
public class PassportServiceImpl implements PassportService {
    @Reference
    private TbUserDubboService tbUserDubboService;
    @Value("${ego.cart.tempCart}")
    private String tempCart;  //临时购物车的cookie key；
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${ego.cart.userCart}")
    private String userCart;      //用户购物车的redis key,前半部分；

    @Override
    public EgoResults check(TbUser tbUser) {
        TbUser user = tbUserDubboService.selectByUser(tbUser);
        if (user == null) {
            return EgoResults.ok();
        }
        return EgoResults.error("用户已存在");
    }

    @Override
    public EgoResults finishRegister(TbUser tbUser) {
        //这里正常应该有正则验证表单数据
        Date date = new Date();
        tbUser.setId(IDUtils.genItemId());
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        //通过spring framework提供的工具类进行加密
        String md5Password = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(md5Password);
        int index = tbUserDubboService.insert(tbUser);
        if (index == 1) {
            return EgoResults.ok();
        }
        return EgoResults.error("注册失败");
    }

    @Override
    public EgoResults login(TbUser tbUser) {
        String s = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        tbUser.setPassword(s);
        TbUser user = tbUserDubboService.selectByUsernamePwd(tbUser);
        if (user != null) {
            //把user放到egoresult中 ,因为控制器需要，session作用域
            // 对购物车进行合并
            // 思路：1. 把cookie数据删除 2. 把临时购物车数据添加到用户购物车中。
            String cookieValue = CookieUtils.getCookieValueBase64(ServletUtil.getRequest(), tempCart);
            if (Strings.isNotEmpty(cookieValue)) {
                Map<Long, CartPojo> cartPojoMap = JsonUtils.jsonToMap(cookieValue, Long.class, CartPojo.class);
                //把临时购物车数据放到redis中
                String userRedisKey = userCart + user.getId();
                List<CartPojo> list = (List<CartPojo>) redisTemplate.opsForValue().get(userRedisKey);
                for (Long itemid : cartPojoMap.keySet()) {
                    boolean isExists=false;
                    for (CartPojo cartPojo:list){ //遍历用户购物车，查看是否有和临时购物车一样商品id的物品，如有则修改数量
                        if (cartPojo.getId().equals(itemid)){
                            cartPojo.setNum(cartPojo.getNum()+cartPojoMap.get(itemid).getNum());
                            isExists=true;
                            break;
                        }
                    }
                    if (!isExists){   //如果不存在一样商品id的数据
                        list.add(cartPojoMap.get(itemid));
                    }
                }
                //合并成功
                redisTemplate.opsForValue().set(userRedisKey, list);
                //删除临时购物车
                CookieUtils.deleteCookie(ServletUtil.getRequest(), ServletUtil.getResponse(), tempCart);

            }
            return EgoResults.ok(user);

        }
        return EgoResults.error("用户名或密码不正确");
    }
}
