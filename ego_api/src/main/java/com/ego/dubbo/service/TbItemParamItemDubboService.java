package com.ego.dubbo.service;

import com.ego.pojo.TbItemParamItem;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemParamItemDubboService {
    /**
     * 根据商品id查询规格参数信息
     * @param itemId
     * @return
     */
    TbItemParamItem selectByItemId(long itemId);


}
