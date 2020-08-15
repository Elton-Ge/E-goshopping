package com.ego.controller;

import com.ego.commons.pojo.EgoResults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class PageController {

    @RequestMapping("/")
    public String login(){
         return "login";
    }

    @RequestMapping("/loginSuccess")
    @ResponseBody
    public EgoResults loginSuccess(){
        return EgoResults.ok();
    }

    @RequestMapping("/main")
    public String  index(){
        return "index";
    }

    @RequestMapping("/{page}")
    public  String showPage(@PathVariable String page){
        return page;
    }

    @RequestMapping("/rest/page/item-edit")
    public String showEdit(){
        return "item-edit";
    }
}
