package com.ego.service;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbItemParam;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbItemParamService {

    //返回前台页面要的数据

    EazyUIDatagrid selectParam(int page, int rows);

    EgoResults showCatItem(long cid);

    EgoResults showCatItemInsert(TbItemParam tbItemParam);

    EgoResults showDelete(long [] ids);
}
