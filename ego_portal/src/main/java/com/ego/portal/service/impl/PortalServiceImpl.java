package com.ego.portal.service.impl;

import com.ego.commons.pojo.BigAd;
import com.ego.commons.utils.JsonUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.portal.service.PortalService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 5/8/20
 * @Description: com.ego.portal.controller.service.impl
 * @version: 1.0
 */
@Service
public class PortalServiceImpl implements PortalService {

    @Reference
    private TbContentDubboService tbContentDubboService;
    @Value("${ego.bigad.categoryId}")
    private long categoryId;

    @Override
    @Cacheable(cacheNames = "com.ego.portal",key = "'bigad'")
    public String showBigAd() {
        List<TbContent> list = tbContentDubboService.selectByCategoryId(categoryId);
        List<BigAd> bigAdList = new ArrayList<>();
        for (TbContent tbContent : list) {
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getUrl());
            bigAd.setSrc(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);

            bigAdList.add(bigAd);
        }

       return   JsonUtils.objectToJson(bigAdList);
    }

    /**
     * 为了配合第一种方式，实现同步缓存到redis
     * @return
     */
    @Override
    @CachePut(cacheNames = "com.ego.portal",key = "'bigad'")
    public String showBigAd2() {
        List<TbContent> list = tbContentDubboService.selectByCategoryId(categoryId);
        List<BigAd> bigAdList = new ArrayList<>();
        for (TbContent tbContent : list) {
            BigAd bigAd = new BigAd();
            bigAd.setAlt("");
            bigAd.setHeight(240);
            bigAd.setHeightB(240);
            bigAd.setHref(tbContent.getUrl());
            bigAd.setSrc(tbContent.getPic());
            bigAd.setSrcB(tbContent.getPic2());
            bigAd.setWidth(670);
            bigAd.setWidthB(550);

            bigAdList.add(bigAd);
        }

        return   JsonUtils.objectToJson(bigAdList);
    }
}
