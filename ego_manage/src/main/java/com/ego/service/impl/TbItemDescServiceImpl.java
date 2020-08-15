package com.ego.service.impl;

import com.ego.commons.pojo.EgoResults;
import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.pojo.TbItemDesc;
import com.ego.service.TbItemDescService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDescServiceImpl implements TbItemDescService {
    @Reference
    private TbItemDescDubboService tbItemDescDubboService;

    @Override
    public EgoResults selectById(long id) {
        TbItemDesc tbItemDesc = tbItemDescDubboService.selectById(id);
        return EgoResults.ok(tbItemDesc);
    }
}
