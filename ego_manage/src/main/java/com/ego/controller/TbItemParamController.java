package com.ego.controller;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemParamService;
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
public class TbItemParamController {

    @Autowired
    private TbItemParamService tbItemParamService;
    @RequestMapping("/item/param/list")
    public EazyUIDatagrid showDatagrid(int page, int rows){
        return  tbItemParamService.selectParam(page, rows);

    }

    @RequestMapping("/item/param/query/itemcatid/{cid}")
    public EgoResults showCatItem(@PathVariable long cid){
        return  tbItemParamService.showCatItem(cid);
    }


    @RequestMapping("/item/param/save/{catId}")
    public EgoResults showInsert(TbItemParam tbItemParam, @PathVariable long catId){
            tbItemParam.setItemCatId(catId);
            return tbItemParamService.showCatItemInsert(tbItemParam);
    }

    @RequestMapping("item/param/delete")
    public EgoResults showDelete(long [] ids){
        return  tbItemParamService.showDelete(ids);
    }
}
