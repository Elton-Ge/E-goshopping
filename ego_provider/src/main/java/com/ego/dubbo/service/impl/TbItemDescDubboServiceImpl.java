package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.TbItemDescDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.pojo.TbItemDesc;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemDescDubboServiceImpl implements TbItemDescDubboService {
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Override
    public TbItemDesc selectById(long id) {
      return   tbItemDescMapper.selectByPrimaryKey(id);

    }
}
