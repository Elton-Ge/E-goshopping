package com.ego.dubbo.service;

import com.ego.pojo.TbItemDesc;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemDescDubboService {
    /**
     * 根据主键查询商品描述信息
     * @param id
     * @return
     */
    TbItemDesc selectById(long id);
}
