package com.ego.passport.service;

import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbUser;

/**
 * @Auther: Elton Ge
 * @Date: 8/8/20
 * @Description: com.ego.passport.service
 * @version: 1.0
 */
public interface PassportService {
    /**
     * 检查用户是否存在
     * @param tbUser
     * @return
     */
    EgoResults check(TbUser tbUser);

    EgoResults finishRegister(TbUser tbUser);

    /**
     * 根据用户名和密码实现登录
     * @param tbUser
     * @return
     */
    EgoResults login(TbUser tbUser);
}
