package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemCatMapper;
import com.ego.pojo.TbItemCat;
import com.ego.pojo.TbItemCatExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemCatDubboServiceImpl implements TbItemCatDubboService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;
    @Override
    public List<TbItemCat> selectByPid(long pId) {
        TbItemCatExample tbItemCatExample = new TbItemCatExample();
        tbItemCatExample.createCriteria().andParentIdEqualTo(pId);
        return tbItemCatMapper.selectByExample(tbItemCatExample);
    }

    @Override
    public TbItemCat selectById(long id) {
        TbItemCat tbItemCat = tbItemCatMapper.selectByPrimaryKey(id);
        return tbItemCat;
    }
}
