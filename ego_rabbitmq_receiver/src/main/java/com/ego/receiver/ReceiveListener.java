package com.ego.receiver;

import com.ego.commons.pojo.BigAd;
import com.ego.commons.pojo.OrderPojo;
import com.ego.commons.pojo.TbItemDetails;
import com.ego.commons.utils.HttpClientUtil;
import com.ego.commons.utils.IDUtils;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mail.MyMailSender;
import com.ego.pojo.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @Auther: Elton Ge
 * @Date: 5/8/20
 * @Description: com.ego.receiver
 * @version: 1.0
 */
@Component
//@RabbitListener(queues = {"content"})
public class ReceiveListener {
    @Value("${ego.bigad.categoryId}")
    private Long bigad;

    @Value("${ego.rabbitmq.tbContent.queueName}")
    private String contentQueue;

    @Value("${ego.rabbitmq.tbItem.queueName}")
    private String itemQueue;

    @Value("${ego.search.insertItemUrl}")
    private String insertItemUrl;

    @Value("${ego.search.deleteItemUrl}")
    private String deleteItemUrl;

    @Reference
    private TbContentDubboService tbContentDubboService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Reference
    private TbItemDubboService tbItemDubboService;

    @Value("${ego.rabbitmq.tbOrder.createOrder}")
    private String createOrderQueue;

    @Reference  //把dubbo的远程服务对象当作spring容器中的对象一样注入，而@Autowired是本地对象注入spring容器
    private TbOrderDubboService tbOrderDubboService;

    @Value("${ego.rabbitmq.tbOrder.deleteCart}")
    private String deleteCart;

    @Value("${ego.rabbitmq.mail}")
    private String mail;
    @Autowired
    private MyMailSender myMailSender;

    //    @RabbitListener(queues = {"content"}) //最简单的写法，但是当没有创建队列时，先启动此监听器时就会报错
    //@RabbitListener这个注解可以实现在receiver端启动时，仍然会自动创建队列

    /**
     * 用于监听更新redis的异步消息
     *
     * @param obj
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue("${ego.rabbitmq.tbContent.queueName}"), key = {"${ego.rabbitmq.tbContent.queueName}"}, exchange = @Exchange(name = "newDirect"))})
    public void receive(Object obj) {   //针对Object对象类型，接收的content-type： text/plain  utf-8
        System.out.println("接收到的消息是" + obj);
        /**
         * redis数据同步
         * 1。 从mysql中取出大广告数据
         * 这里需要把此项目改造城dubbo的consumer，这样才能操作数据库。
         *
         *
         * 2把数据缓存到redis中
         */
//        HttpClientUtil.doGet("http://localhost:8082/bigAd2"); //第一种方式
          /*
          第二种方式
           */
        List<TbContent> list = tbContentDubboService.selectByCategoryId(bigad);
        List<BigAd> bigAdList = new ArrayList<>();
        for (TbContent tbContent : list) {
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getUrl());
            bigAd.setSrc(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);

            bigAdList.add(bigAd);
        }
        redisTemplate.opsForValue().set("com.ego.portal::bigad", JsonUtils.objectToJson(bigAdList));
        //JsonUtils.objectToJson(bigAdList) 作用是把对象或者集合转成json字符串
    }

    /**
     * 商品新增、修改、上架时，同步solr数据中，用于监听solr-search的异步消息
     * 同时可以同步到redis中。提升访问查询效率
     *
     * @param id
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue("${ego.rabbitmq.tbItem.queueName}"), key = {"${ego.rabbitmq.tbItem.queueName}"}, exchange = @Exchange(name = "newDirect"))})
    public void receiveItem(String id) {
        System.out.println("商品的id：" + id);
        /*
         *  实现商品数据的solr同步
         */
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpClientUtil.doGet(insertItemUrl, map);  //软编码
        /*
         * redis同步，以后都用这种方式去写，因为此种方式对ego_item没有压力，不走他的控制器。
         * */
        String[] split = id.split(",");
        for (String str : split) {
            System.out.println(str);
            //value
            TbItem tbItem = tbItemDubboService.selectById(Long.parseLong(str));
            TbItemDetails tbItemDetails = new TbItemDetails();
            tbItemDetails.setId(Long.parseLong(str));
            tbItemDetails.setPrice(tbItem.getPrice());
            tbItemDetails.setSellPoint(tbItem.getSellPoint());
            tbItemDetails.setTitle(tbItem.getTitle());
            String image = tbItem.getImage();
            tbItemDetails.setImages(image != null && !image.equals("") ? image.split(",") : new String[1]);
            //key
            String key = "com.ego.item::showDetails" + str;
            redisTemplate.opsForValue().set(key, tbItemDetails);
        }
    }

    /**
     * 删除和下架商品时
     *
     * @param id
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue("${ego.rabbitmq.tbItem.queueNameDelete}"), key = {"${ego.rabbitmq.tbItem.queueNameDelete}"}, exchange = @Exchange(name = "newDirect"))})
    public void receiveItemDelete(String id) {
        System.out.println("商品的id：" + id);
        /*
        同步solr
         */
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpClientUtil.doGet(deleteItemUrl, map);  //软编码
        /*
        同步到redis,当删除和下架商品数据时，就同步删除redis的数据，因为redis的数据没有存在的意义了
         */
        String[] split = id.split(",");
        for (String str : split) {
            String key = "com.ego.item::showDetails" + str;
            redisTemplate.delete(key);
        }
    }

    /**
     * 这是创建订单的队列 ，  在队列中解决了并发问题，避免了库存数量成为负数的可能性。
     * 当接收的消息是对象时 ，要用Message 来接收
     * 当这里receiver有boolean等返回值时，send那必须要有接收此返回值的方法
     *  返回值boolean改为了String
     * @param orderPojo   这如果是换成Message message对象，接收的content-type为application/x-java-serialized-object
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue("${ego.rabbitmq.tbOrder.createOrder}"), key = {"${ego.rabbitmq.tbOrder.createOrder}"}, exchange = @Exchange(name = "newDirect"))})
    public String receiveCreateOrder(OrderPojo orderPojo) {
//        System.out.println(orderPojo);
        //判断用户打算购买的商品数量> 库存商品数量
        List<TbOrderItem> orderItems = orderPojo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            TbItem tbItem = tbItemDubboService.selectById(Long.parseLong(orderItem.getItemId()));
            if (orderItem.getNum() > tbItem.getNum()) {//用户打算购买的商品数量> 库存商品数量
                return null;
            }
        }
//        判断用户打算购买的商品数量 <=库存商品数量, 创建订单，向数据库表新增数据
        //tborder
        TbOrder tbOrder = new TbOrder();
        tbOrder.setPayment(orderPojo.getPayment());
        tbOrder.setPaymentType(Integer.valueOf(orderPojo.getPaymentType()));
        String orderId = IDUtils.genItemId() + "";
        tbOrder.setOrderId(orderId);    //设置orderid
        Date date = new Date();
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        //tborderItem
        List<TbOrderItem> tbOrderItemList = orderPojo.getOrderItems();
        for (TbOrderItem tbOrderItem : tbOrderItemList) {
            tbOrderItem.setId(IDUtils.genItemId() + "");
            tbOrderItem.setOrderId(orderId);
        }
        //tbordershiping
        TbOrderShipping orderShipping = orderPojo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);

        int insert = tbOrderDubboService.insert(tbOrder, tbOrderItemList, orderShipping);
        if (insert == 1) {
            //修改库存
            List<TbOrderItem> tbOrderItems = orderPojo.getOrderItems();
            for (TbOrderItem toi : tbOrderItems) {
                TbItem tbItem = tbItemDubboService.selectById(Long.parseLong(toi.getItemId()));
                tbItem.setNum(tbItem.getNum() - toi.getNum());     //库存减去购买的数量
                tbItem.setUpdated(date);
                tbItemDubboService.updateTbItem(tbItem);
            }
            return orderId;
        }
        return null;
    }

    /**
     * 删除用户购物车 监听器
     *
     * @param map
     */
    @RabbitListener(bindings = {@QueueBinding(value = @Queue("${ego.rabbitmq.tbOrder.deleteCart}"), key = {"${ego.rabbitmq.tbOrder.deleteCart}"}, exchange = @Exchange(name = "newDirect"))})
    public void receiveDeleteCart(Map map) {  //针对消息对象，接收的content-type为application/x-java-serialized-object
//        System.out.println(map);
        HttpClientUtil.doPost("http://localhost:8085/cart/delete", map);
    }

    /**
     * 发送邮件的监听器
     * @param orderId
     */

    @RabbitListener(bindings = {@QueueBinding(value = @Queue("${ego.rabbitmq.mail}"), key = {"${ego.rabbitmq.mail}"}, exchange = @Exchange(name = "newDirect"))})
    public void receiveMail(String orderId) {  //针对消息对象，接收的content-type为application/x-java-serialized-object
        System.out.println(orderId);
        myMailSender.sendMail("gexin7648@hotmail.com",orderId);
    }
}













