package com.ego.item.service;

import com.ego.commons.pojo.TbItemDetails;
import com.ego.item.pojo.ItemCategoryNav;
import com.ego.pojo.TbItem;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.item.service
 * @version: 1.0
 */
public interface ItemService {

    ItemCategoryNav showItemCategoryNav();

    /**
     * 显示商品详情
     * @param id
     * @return
     */
    TbItemDetails showDetails(Long id);

    /**
     * 根据商品id查询商品描述
     * @param itemid
     * @return
     */
    String showItemDesc(Long itemid);

    String showParamItem(Long itemid);
}
