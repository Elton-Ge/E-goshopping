package com.ego.controller;

import com.ego.item.pojo.ItemCategoryNav;
import com.ego.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/rest/itemcat/all")
    @ResponseBody
    @CrossOrigin
    public ItemCategoryNav showItemCategoryNav(){
        return itemService.showItemCategoryNav();
    }

    @RequestMapping("/item/{id}.html")
    public String showItem(@PathVariable long id, Model model){
        model.addAttribute("item",itemService.showDetails(id));
        return  "item";
    }
    @RequestMapping("/item/desc/{id}.html")
    @ResponseBody
    public String showItemDesc(@PathVariable long id){
        return itemService.showItemDesc(id);
    }

    /**
     * 显示商品规格参数信息
     * @param id
     * @return
     */
    @RequestMapping("/item/param/{id}.html")
    @ResponseBody
    public String showParamItem(@PathVariable long id){
        return itemService.showParamItem(id);
    }


}
