package com.ego.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.service
 * @version: 1.0
 *
 */
public interface PicService {
    /**
     * 文件上传
     * @param file Spring MVC 上传对象对象
     * @return
     */

    Map<String , Object> upload(MultipartFile file);
}
