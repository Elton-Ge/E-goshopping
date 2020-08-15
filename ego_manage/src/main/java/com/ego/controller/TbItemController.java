package com.ego.controller;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class TbItemController {
    @Autowired
    private TbItemService tbItemService;

    @RequestMapping("/item/list")
    @ResponseBody
    public EazyUIDatagrid showItems(int page,int rows){
         return  tbItemService.showItems(page, rows);
    }

    @RequestMapping("/rest/item/delete")
    @ResponseBody
    public EgoResults delete(long []ids){
        return  tbItemService.updateItems(ids, (byte) 3);
    }

    @RequestMapping("/rest/item/instock")
    @ResponseBody
    public EgoResults instock(long []ids){
        return  tbItemService.updateItems(ids, (byte) 2);
    }

    @RequestMapping("/rest/item/reshelf")
    @ResponseBody
    public EgoResults reshelf(long []ids){
        return  tbItemService.updateItems(ids, (byte) 1);
    }

    /**
     * 商品新增功能
     * @param tbItem
     * @param desc
     * @return
     */
    @RequestMapping("/item/save")
    @ResponseBody
    public EgoResults insert(TbItem tbItem,String desc,String itemParams){
        return tbItemService.insert(tbItem,desc,itemParams);
    }


    @RequestMapping("/rest/item/update")
    @ResponseBody
    public EgoResults update(TbItem tbItem,String desc,long itemParamId, String itemParams){
        return tbItemService.update(tbItem,desc,itemParamId,itemParams);
    }


}
