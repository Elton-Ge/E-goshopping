package com.ego.portal.controller;

import com.ego.portal.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.portal.controller
 * @version: 1.0
 */
@Controller
public class PortalController {
    @Autowired
    private PortalService portalService;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("ad1",portalService.showBigAd());
        return "index";
    }

    @RequestMapping("/bigAd2")
    @ResponseBody
    public String bigAd2(){
        portalService.showBigAd2();
        return "ok";
    }

}
