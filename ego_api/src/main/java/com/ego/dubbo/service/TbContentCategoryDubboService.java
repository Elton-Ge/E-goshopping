package com.ego.dubbo.service;

import com.ego.commons.exception.DaoException;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.dubbo.service
 * @version: 1.0
 */
public interface TbContentCategoryDubboService {

    /**
     * 根据父id查询内容分类
     * CMS
     * @param pid
     * @return
     */
    List<TbContentCategory>  selectContentCat(long pid);

    /**
     * 新增内容分类
     * @param tbContentCategory
     * @return
     */
    int insertContentCat(TbContentCategory tbContentCategory) throws DaoException;

    int updateContentCat(TbContentCategory tbContentCategory) throws DaoException;

    /**
     * 删除内容分类，递归
     * @param id
     * @return
     */
    int deleteContentCat (long id)  throws DaoException;

}
