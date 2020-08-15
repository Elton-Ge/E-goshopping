package com.ego.service.impl;

import com.ego.TbItemParamChild;
import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.pojo.TbItemParam;
import com.ego.service.TbItemParamService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamServiceImpl implements TbItemParamService {

    @Reference
    private TbItemParamDubboService tbItemParamDubboService;
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Override
    public EazyUIDatagrid selectParam(int page, int rows) {
        List<TbItemParam> list = tbItemParamDubboService.selectByPage(page, rows);
        List<TbItemParamChild> childList = new ArrayList<>();
        for (TbItemParam tbItemParam : list) {
            TbItemParamChild child = new TbItemParamChild();
            BeanUtils.copyProperties(tbItemParam,child);
            child.setItemCatName(tbItemCatDubboService.selectById(tbItemParam.getItemCatId()).getName());
            childList.add(child);
        }
        long l = tbItemParamDubboService.countTotal();
        return new EazyUIDatagrid(childList,l);
    }

    @Override
    public EgoResults showCatItem(long cid) {

        TbItemParam tbItemParam = tbItemParamDubboService.selectByCatid(cid);
        if (tbItemParam!=null){
            return EgoResults.ok(tbItemParam);
        }
        return EgoResults.error("操作失败");
    }

    @Override
    public EgoResults showCatItemInsert(TbItemParam tbItemParam) {
        tbItemParam.setId(IDUtils.genItemId());
        Date date = new Date();
        tbItemParam.setCreated(date);
        tbItemParam.setUpdated(date);
        int index = tbItemParamDubboService.insert(tbItemParam);
        if (index==1){
            return EgoResults.ok();
        }
        return EgoResults.error("新增失败");
    }

    @Override
    public EgoResults showDelete(long[] ids) {
        int index = tbItemParamDubboService.delete(ids);
        if (index==1){
            return EgoResults.ok();
        }
        return EgoResults.error("批量删除失败") ;
    }
}
