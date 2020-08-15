package com.ego.service.impl;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.commons.exception.DaoException;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.ego.sender.Send;
import com.ego.service.TbItemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemServiceImpl implements TbItemService {
    @Reference
    private TbItemDubboService tbItemDubboService;
    @Value("${ego.rabbitmq.tbItem.queueName}")
    private String itemQueue;

    @Value("${ego.rabbitmq.tbItem.queueNameDelete}")
    private String itemQueueDelete;
    @Autowired
    private Send send;
    @Override
    public EazyUIDatagrid showItems(int page, int rows) {
        List<TbItem> list = tbItemDubboService.selectByPage(page, rows);
        long total = tbItemDubboService.countByExample();
        return new EazyUIDatagrid(list,total);
    }

    @Override
    public EgoResults updateItems(long[] ids, byte status) {
        try {
            int i = tbItemDubboService.UpdateStatusByIds(ids, status);
            if (i==1){
                if (status==1){           //1表示上架
                    //对solr就是新增
                    System.out.println(StringUtils.join(ids,','));
                    send.send(itemQueue, StringUtils.join(ids,','));
                }  else if (status==2 || status==3){ //2、3 下架和删除
                    send.send(itemQueueDelete, StringUtils.join(ids,','));
                }
                return EgoResults.ok();
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResults.error("操作失败");
    }

    @Override
    public EgoResults insert(TbItem tbItem, String desc,String itemParams) {
        Date date = new Date();
        long itemId = IDUtils.genItemId();
        tbItem.setId(itemId);
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        tbItem.setStatus((byte) 1);

        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(date);
        tbItemDesc.setUpdated(date);
        //第三表 ：tbItemParamItem表  商品规格参数表
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(IDUtils.genItemId());
        tbItemParamItem.setCreated(date);
        tbItemParamItem.setUpdated(date);
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParams);
        int index = tbItemDubboService.insert(tbItem, tbItemDesc,tbItemParamItem);
        if (index==1){
            send.send(itemQueue,itemId);  //发送了itemId
            return EgoResults.ok();
        }
        return EgoResults.error("新增商品失败");
    }

    @Override
    public EgoResults update(TbItem tbItem, String desc,long itemParamId, String itemParams) {
        Date date = new Date();
        tbItem.setUpdated(date);

        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);

        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setId(itemParamId);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(date);

        int index = tbItemDubboService.update(tbItem, tbItemDesc,tbItemParamItem);
        if (index==1){
            send.send(itemQueue,tbItem.getId());
            return EgoResults.ok();
        }
        return EgoResults.error("修改商品失败");
    }


}
