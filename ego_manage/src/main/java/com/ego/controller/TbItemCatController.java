package com.ego.controller;

import com.ego.commons.pojo.EazyUITree;
import com.ego.service.TbItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class TbItemCatController {

    @Autowired
    private TbItemCatService tbItemCatService;

    /**
     *
     * @param id eazyUI底层的参数名，固定叫id， 表示是父id。
     * @return
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EazyUITree> showTree(@RequestParam(defaultValue = "0") long id){  // "0"即默认pid=0，
        return  tbItemCatService.showTree(id);

    }
}
