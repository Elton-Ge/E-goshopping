package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItemParamItem;
import com.ego.pojo.TbItemParamItemExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemDubboServiceImpl implements TbItemParamItemDubboService {

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;
    @Override
    public TbItemParamItem selectByItemId(long itemId) {
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        tbItemParamItemExample.createCriteria().andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        if (list!=null){
            return list.get(0);
        }
        return null;
    }


}
