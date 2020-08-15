package com.ego.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Auther: Elton Ge
 * @Date: 5/8/20
 * @Description: com.ego.sender
 * @version: 1.0
 */
//一定要保证当前项目被其他项目依赖时，可以让其他项目的启动器springbootapplication能扫描到此类。
@Component
public class Send {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String keyName,Object obj){       // 这里设置keyName==QueueName
        amqpTemplate.convertAndSend("newDirect",keyName,obj);
    }

    /**
     * 此方法的返回值就是receive方法的返回值；
     * @param keyName
     * @param obj
     * @return
     */
    public Object sendAndReceive(String keyName,Object obj) {
        return amqpTemplate.convertSendAndReceive("newDirect", keyName, obj);
    }
}
