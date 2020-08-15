package com.ego.dubbo.service;

import com.ego.pojo.TbUser;

/**
 * @Auther: Elton Ge
 * @Date: 8/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbUserDubboService {
    /**
     * 动态sql
     * @param user
     * @return
     */
    TbUser selectByUser(TbUser user);

    /**
     * 用户注册新增到数据库
     * @param tbUser
     * @return
     */
    int insert(TbUser tbUser);

    /**
     * 根据用户名密码查询
     * @param tbUser
     * @return
     */
    TbUser selectByUsernamePwd(TbUser tbUser);
}
