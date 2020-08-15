package com.ego.controller;

import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbItemParamItem;
import com.ego.service.TbItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbItemParamItemController {

    /**
     * 根据商品id查询规格参数
     */
    @Autowired
    private TbItemParamItemService tbItemParamItemService;

    @RequestMapping("/rest/item/param/item/query/{itemId}")
    public EgoResults showParamItem(@PathVariable long itemId){
        return  tbItemParamItemService.showParaItem(itemId);
    }


}
