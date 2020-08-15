package com.ego.controller;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.pojo.TbContent;
import com.ego.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@RestController
public class TbContentController {
    @Autowired
    private TbContentService tbContentService;

    @RequestMapping("/content/query/list")
    public EazyUIDatagrid showTbContent(long categoryId, int page ,int rows){
        return  tbContentService.showTbCotent(categoryId, page, rows);
    }

    @RequestMapping("/content/save")
    public EgoResults showInsert(TbContent tbContent){
        return tbContentService.insert(tbContent);
    }


    @RequestMapping("/rest/content/edit")
    public EgoResults showUpdate(TbContent tbContent){
        return tbContentService.update(tbContent);
    }
    @RequestMapping("/content/delete")
    public EgoResults showDelete(long [] ids){
        return tbContentService.delete(ids);
    }

}
