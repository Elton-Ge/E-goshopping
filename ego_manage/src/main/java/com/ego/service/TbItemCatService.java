package com.ego.service;

import com.ego.commons.pojo.EazyUITree;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbItemCatService {
    /**
     * 返回页面要的东西：此处要的是eazyUiTree
     */

    List<EazyUITree> showTree(long pid);

}
