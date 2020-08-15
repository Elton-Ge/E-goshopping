package com.ego.controller;

import com.ego.commons.pojo.EgoResults;
import com.ego.service.TbItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class TbItemDescController {
    @Autowired
    private TbItemDescService tbItemDescService;

    /**
     * 显示商品描述参数 即desc回显
     * @param id    这里的参数是通过restful风格url传过来的，注意必须有@PathVariable
     * @return
     */
    @RequestMapping("/rest/item/query/item/desc/{id}")
    @ResponseBody
    public EgoResults showDesc(@PathVariable long id){
        return tbItemDescService.selectById(id);
    }
}
