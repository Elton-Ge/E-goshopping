package com.ego.search.controller;

import com.ego.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 7/8/20
 * @Description: com.ego.search.controller
 * @version: 1.0
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @RequestMapping("/search.html")
    public String doSearch(String q,  @RequestParam(defaultValue = "1")int page,  @RequestParam(defaultValue = "12")int size, Model model){
        //注意这里必须用Model， 因为分布式项目用不了普通的Httpsession，处理session问题需要使用spring session
        model.addAllAttributes(searchService.search(q,page,size));

        return "search";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public int insert(long [] id){
        return searchService.insert(id);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public int delete(String  [] id){
        return searchService.delete(id);
    }
}
