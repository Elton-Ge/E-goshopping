package com.ego.controller;

import com.ego.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.controller
 * @version: 1.0
 */
@Controller
public class PicController {

    @Autowired
    private PicService picService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map<String,Object> upload(MultipartFile uploadFile){
             return picService.upload(uploadFile);
    }
}
