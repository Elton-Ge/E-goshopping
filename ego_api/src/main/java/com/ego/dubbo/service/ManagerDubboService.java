package com.ego.dubbo.service;

import com.ego.pojo.Manager;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 * manage表操作
 */
public interface ManagerDubboService {
    /**
     * 根据用户名查询manager信息
     */
    Manager selectManagerByUsername(String  username);
}
