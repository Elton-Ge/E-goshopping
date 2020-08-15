package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbOrderDubboService {

    int insert (TbOrder tbOrder, List<TbOrderItem> tbOrderItemList, TbOrderShipping tbOrderShipping) throws DaoException;

}
