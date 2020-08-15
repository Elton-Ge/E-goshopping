package com.ego.service.impl;

import com.ego.commons.pojo.EgoResults;
import com.ego.dubbo.service.TbItemParamItemDubboService;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemParamItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamItemServiceImpl implements TbItemParamItemService {
    @Reference
    private TbItemParamItemDubboService tbItemParamItemDubboService;
    @Override
    public EgoResults showParaItem(long itemId) {
        TbItemParamItem tbItemParamItem = tbItemParamItemDubboService.selectByItemId(itemId);
        if (tbItemParamItem!=null) {
            return EgoResults.ok(tbItemParamItem);
        }
        return EgoResults.error("查询错误");
    }


}
