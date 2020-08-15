package com.ego.service;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbContent;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.service
 * @version: 1.0
 */
public interface TbContentService {
    
    EazyUIDatagrid showTbCotent(long categoryId, int page, int rows);

    EgoResults insert(TbContent tbContent);

    EgoResults update(TbContent tbContent);

    EgoResults delete(long [] ids);
}
