package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbItemParam;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbItemParamDubboService {

    /**
     * 操作数据库
     * @param pageNumber 第几页
     * @param pageSize 每页多少数据
     * @return     显示的数据
     */
    List<TbItemParam> selectByPage(int pageNumber, int pageSize);

    /**
     *
     * @return 查询总数据
     */
    long countTotal();

    /**
     * 根据cat主键查询param表信息
     * @param catId
     * @return
     */
    TbItemParam selectByCatid(long catId);

    /**
     * 新增TbItemParam
     * @param tbItemParam
     * @return
     */
    int insert(TbItemParam tbItemParam);

    /**
     * 根据主键，批量删除数据
     * @param ids
     * @return
     */
    int delete(long [] ids) throws DaoException;
}
