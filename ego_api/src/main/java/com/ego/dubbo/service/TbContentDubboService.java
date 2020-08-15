package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbContent;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbContentDubboService {

    List<TbContent> selectByPage(long categoryId, int pageNum, int pageSize);

    long CountbByCategoryId(long categoryId);

    int insertTbContent(TbContent tbContent);

    int updateTbContent(TbContent tbContent);

    int deleteTbContent(long [] ids) throws DaoException;

    /**
     * 根据 categoryId查询大广告信息 catid：89 ，采用软编码
     * @param categoryId
     * @return
     */
    List<TbContent> selectByCategoryId(long categoryId);

    TbContent selectByid(long id);
}
