package com.ego.service;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbItem;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.service
 * @version: 1.0
 * consumer层只考虑显示给前台的表现逻辑
 */

public interface TbItemService {
        /*
    方法的返回值为页面要的东西
    方法的参数为页面传递过来的东西
     */

    /**
     *
     * @param page     当前页码
     * @param rows     要显示多少行数据
     * @return
     */

    EazyUIDatagrid showItems(int page, int rows);

    EgoResults updateItems(long [] ids, byte status);

    /**
     * 返回给页面想要的东西
     * 方法参数是页面url传递过来的
     */
    EgoResults insert(TbItem tbItem, String desc,String itemParams);

    EgoResults update(TbItem tbItem, String desc,long itemParamId, String itemParams);
}
