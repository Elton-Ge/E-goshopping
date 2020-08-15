package com.ego.sender.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: Elton Ge
 * @Date: 5/8/20
 * @Description: com.ego.sender.config
 * @version: 1.0
 */
@Configuration
public class SendConfig {
    @Value("${ego.rabbitmq.tbContent.queueName}")
    private String contentQueue;

    @Value("${ego.rabbitmq.tbItem.queueName}")
    private String itemQueue;

    @Value("${ego.rabbitmq.tbItem.queueNameDelete}")
    private String itemQueueDelete;

    @Value("${ego.rabbitmq.tbOrder.createOrder}")
    private String createOrderQueue;

    @Value("${ego.rabbitmq.tbOrder.deleteCart}")
    private String  deleteCart;
    @Value("${ego.rabbitmq.mail}")
    private String mail;
    /**
     * 如果没有队列，则创建一个单例队列
     * @return
     */
    @Bean
    public Queue contentQueue(){
        return  new Queue(contentQueue);
    }

    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange("newDirect");
    }

    @Bean
    public Binding contentBinding(Queue contentQueue, DirectExchange directExchange ){
       return BindingBuilder.bind(contentQueue).to(directExchange).withQueueName();
    }

    /**
     * 新new的queue
     * @return
     */
    @Bean
    public Queue itemQueue(){
        return  new Queue(itemQueue);
    }

    @Bean
    public Binding itemBinding(Queue itemQueue, DirectExchange directExchange ){
        return BindingBuilder.bind(itemQueue).to(directExchange).withQueueName();
    }

    /**
     * 新new的queue
     * @return
     */
    @Bean
    public Queue itemQueueDelete(){
        return  new Queue(itemQueueDelete);
    }

    @Bean
    public Binding itemBindingDelete(Queue itemQueueDelete, DirectExchange directExchange ){
        return BindingBuilder.bind(itemQueueDelete).to(directExchange).withQueueName();
    }

    /**
     * 创建订单队列
     * @return
     */
    @Bean
    public Queue createOrderQueue(){
        return  new Queue(createOrderQueue);
    }

    @Bean
    public Binding orderBinding(Queue createOrderQueue, DirectExchange directExchange ){
        return BindingBuilder.bind(createOrderQueue).to(directExchange).withQueueName();
    }

    /**
     * 删除用户购物车的队列
     * @return
     */
    @Bean
    public Queue deleteCart(){
        return  new Queue(deleteCart);
    }

    @Bean
    public Binding orderBindingDelete(Queue deleteCart, DirectExchange directExchange ){
        return BindingBuilder.bind(deleteCart).to(directExchange).withQueueName();
    }

    /**
     * 发送邮件的队列
     * @return
     */
    @Bean
    public Queue mail(){
        return  new Queue(mail);
    }

    @Bean
    public Binding mailBinding(Queue mail, DirectExchange directExchange ){
        return BindingBuilder.bind(mail).to(directExchange).withQueueName();
    }

}
