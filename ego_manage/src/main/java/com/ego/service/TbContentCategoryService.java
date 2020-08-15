package com.ego.service;

import com.ego.commons.pojo.EazyUITree;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbContentCategory;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbContentCategoryService {
    /**
     * 根据父id 查询内容分类列表
     * @param pid
     * @return 返回的是eazyuiTree
     */
    List<EazyUITree> showContentCat(long pid);

    /**
     * 新增
     * @param tbContentCategory
     * @return EgoResult 里面包含data.id
     */

    EgoResults insert(TbContentCategory tbContentCategory);


    EgoResults update(TbContentCategory tbContentCategory);

    /**
     * 删除
     * @param id
     * @return
     */
    EgoResults delete(long id);
}
