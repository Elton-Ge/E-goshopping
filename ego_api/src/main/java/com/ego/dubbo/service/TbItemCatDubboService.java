package com.ego.dubbo.service;

import com.ego.pojo.TbItemCat;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemCatDubboService {
    /**
     * 根据父id查询所有子类目
     * @param pId
     * @return
     */
    List<TbItemCat> selectByPid(long pId);

    /**
     * 根据主键查询
     * @param id
     * @return     详细数据
     */
    TbItemCat selectById(long id);
}
