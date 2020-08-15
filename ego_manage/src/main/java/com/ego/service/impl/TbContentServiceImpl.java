package com.ego.service.impl;

import com.ego.commons.pojo.EazyUIDatagrid;
import com.ego.commons.pojo.EgoResults;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.pojo.TbContent;
import com.ego.sender.Send;
import com.ego.service.TbContentService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboService;
    @Autowired
    private Send send;
    @Value("${ego.rabbitmq.tbContent.queueName}")
    private String contentQueue;
    @Value("${ego.bigad.categoryId}")
    private Long bigad;

    @Override
    public EazyUIDatagrid showTbCotent(long categoryId, int page, int rows) {
        List<TbContent> list = tbContentDubboService.selectByPage(categoryId, page, rows);
        long l = tbContentDubboService.CountbByCategoryId(categoryId);
        return new EazyUIDatagrid(list,l);
    }

    @Override
    public EgoResults insert(TbContent tbContent) {
        Date date = new Date();
        tbContent.setId(IDUtils.genItemId());
        tbContent.setCreated(date);
        tbContent.setUpdated(date);
        int i = tbContentDubboService.insertTbContent(tbContent);
        if (i==1){
            //数据库新增成功了
            //同步缓存到redis
            //向rabbitmq发送一条异步信息,这里并不耗时，耗时是在rabbitmq的receiver处理的
            if (tbContent.getCategoryId().equals(bigad)) {
                send.send(contentQueue,"insert");
            }

            return EgoResults.ok();
        }
        return EgoResults.error("新增内容失败");
    }

    @Override
    public EgoResults update(TbContent tbContent) {
        Date date = new Date();
        tbContent.setUpdated(date);
        int i = tbContentDubboService.updateTbContent(tbContent);
        if (i==1){
            //数据库新增成功了
            //同步缓存
            //向rabbitmq发送一条异步信息,这里并不耗时，耗时是在rabbitmq的receiver处理的
            if (tbContent.getCategoryId().equals(bigad)) {
                send.send(contentQueue,"update");
            }
            return EgoResults.ok();
        }
        return EgoResults.error("更新失败");
    }

    @Override
    public EgoResults delete(long[] ids) {
        boolean isDeleted =false;
        for (long id : ids) {
            TbContent tbContent = tbContentDubboService.selectByid(id);
            if (tbContent.getCategoryId().equals(bigad)) {
                        isDeleted=true;
                        break;
            }
        }
        int i = tbContentDubboService.deleteTbContent(ids);
        if (i==1){
            if (isDeleted){
                send.send(contentQueue,"delete");
            }
            return EgoResults.ok();
        }
        return EgoResults.error("批量删除失败");
    }
}
