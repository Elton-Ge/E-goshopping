package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbOrderDubboService;
import com.ego.mapper.TbOrderItemMapper;
import com.ego.mapper.TbOrderMapper;
import com.ego.mapper.TbOrderShippingMapper;
import com.ego.pojo.TbOrder;
import com.ego.pojo.TbOrderItem;
import com.ego.pojo.TbOrderShipping;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 10/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbOrderDubboServiceImpl implements TbOrderDubboService {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;
    @Override
    @Transactional
    public int insert(TbOrder tbOrder, List<TbOrderItem> tbOrderItemList, TbOrderShipping tbOrderShipping) throws DaoException{
        int index = tbOrderMapper.insertSelective(tbOrder);
        if (index==1){
            int index2=0;
            for (TbOrderItem tbOrderItem : tbOrderItemList) {
                 index2+= tbOrderItemMapper.insertSelective(tbOrderItem);
            }
            if (index2==tbOrderItemList.size()) {
                int index3 = tbOrderShippingMapper.insertSelective(tbOrderShipping);
                if (index3==1){
                    return 1;   //1代表成功
                }
            }
        }
       throw  new DaoException("新增失败");
    }
}
