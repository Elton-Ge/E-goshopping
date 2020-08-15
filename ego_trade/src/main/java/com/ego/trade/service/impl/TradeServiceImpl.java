package com.ego.trade.service.impl;

import com.ego.commons.pojo.OrderPojo;
import com.ego.commons.utils.ServletUtil;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbUser;
import com.ego.sender.Send;
import com.ego.trade.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.trade.service.impl
 * @version: 1.0
 */
@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private Send send;
    @Value("${ego.rabbitmq.tbOrder.createOrder}")
    private String createOrderQueue;
    @Value("${ego.rabbitmq.tbOrder.deleteCart}")
    private String  deleteCart;
    @Value("${ego.rabbitmq.mail}")
    private String mail;


    /**
     * 通过rabbitmq，来帮助创建订单数据表。避免由库存数量并发修改产生的问题
     * 当队列订单创建后，要修改库存数量
     * 然后删除购物车数据
     * 发邮件
     * @param orderPojo
     * @return
     */
    @Override
    public Map<String, Object> createOrder(OrderPojo orderPojo) {
//        send.send(createOrderQueue, orderPojo);
        //此处sendAndReceive变成了同步消息， 但我们此处使用rabbitmq的目的是为了使用队列
        String result = (String) send.sendAndReceive(createOrderQueue, orderPojo);
        System.out.println("result:"+result);  //这里当result就是orderId

        /*下面用rabbitmq 的异步功能来删除用户购物车数据，然后再发送邮件*/
        if (result!=null) {
            //删除用户购物车
            //1.通过session取得userid
            TbUser loginUser = (TbUser) ServletUtil.getRequest().getSession().getAttribute("loginUser");
            //2.取得itemid，并拼接成id1，id2的字符串
            List<TbOrderItem> list = orderPojo.getOrderItems();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(list.get(i).getItemId());
                if (i<list.size()-1) {    //只要不是最后一位，就追加，
                    stringBuffer.append(",");
                }
            }
            //3。把1步和2步 放到map集合里， 以便HttpClient.doPost（url,map） 可以用
            HashMap<String , String > map = new HashMap<>();
            map.put("userId",loginUser.getId()+"");
            map.put("itemId",stringBuffer.toString());
            send.send(deleteCart,map);
        }
          //使用rabbitmq队列 来发送邮件
        if (result!=null) {
            send.send(mail, result);
        }
         /* //显示订单成功的页面，把map数据回传到前台页面。*/
        if (result!=null){
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("orderId",result);
            resultMap.put("payment",orderPojo.getPayment());

            // 当天11点之前下订单，预计当天下午送到
            // 11点到23点之前下单，预计第二天上午送到
            // 23点之后第二天下午送到。

            // 获取当前时间
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            if (hour<11) {
                calendar.set(Calendar.HOUR_OF_DAY,17);
            } else if (hour>11&&hour<23) {
                calendar.add(Calendar.DATE,1);
                calendar.set(Calendar.HOUR_OF_DAY,9);
            } else {
                calendar.add(Calendar.DATE,1);
                calendar.set(Calendar.HOUR_OF_DAY,17);
            }
            resultMap.put("date",calendar.getTime());
            return resultMap;
        }

        return null;
    }

    
}
