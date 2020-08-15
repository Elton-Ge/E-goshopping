package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 * 商品表数据库操作
 */
public interface TbItemDubboService {
    

    /**
     * 分页查询
     * @param pageNumber 当前页码
     * @param pageSize 每页多少个
     * @return
     */
    List<TbItem> selectByPage(int pageNumber, int pageSize);

    /**
     * 查询总共数据
     * @return
     */
    long countByExample();

    /**
     * 根据主键们批量修改状态
     * @param ids   主键
     * @param status 状态 1正常 2下架 3删除
     * @return     1成功 0失败
     */
    int UpdateStatusByIds(long[] ids, byte status) throws DaoException;

    /**
     * 新增商品，两表新增，分别插入
     * @param tbItem
     * @param tbItemDesc
     * @return
     */
    /**
     * 完善为三表新增
     * @param tbItem
     * @param tbItemDesc
     * @param tbItemParamItem
     * @return
     * @throws DaoException
     */

    int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException;

    /**
     * 修改商品
     * @param tbItem
     * @param tbItemDesc
     * @return
     * @throws DaoException
     */
    int update(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws DaoException;

    /**
     * 根据主键查询
     * @param id  商品id
     * @return
     */
    TbItem selectById(long id);

    /**
     * 修改TbItem表
     * @param tbItem
     * @return
     */
    int updateTbItem(TbItem tbItem);



}
