package com.ego.controller;

import com.ego.commons.pojo.EazyUITree;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbContentCategoryController {
    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    @RequestMapping("/content/category/list")
    public List<EazyUITree> showContentCat(@RequestParam(defaultValue = "0") long id){
        return tbContentCategoryService.showContentCat(id);
    }

    /**
     * 显示内容分类
     * @param tbContentCategory
     * @return
     */

    @RequestMapping("/content/category/create")
    public EgoResults insert(TbContentCategory tbContentCategory){
        return tbContentCategoryService.insert(tbContentCategory);

    }

    /**
     * 重命名
     * @param tbContentCategory
     * @return
     */
    @RequestMapping("/content/category/update")
    public EgoResults update(TbContentCategory tbContentCategory){
        return tbContentCategoryService.update(tbContentCategory);
    }


    @RequestMapping("/content/category/delete/")
    public EgoResults delete(long id){
        return tbContentCategoryService.delete(id);
    }
}
