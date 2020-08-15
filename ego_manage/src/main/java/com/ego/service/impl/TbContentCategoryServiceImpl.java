package com.ego.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.commons.pojo.EazyUITree;
import com.ego.commons.pojo.EgoResults;
import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.pojo.TbContentCategory;
import com.ego.service.TbContentCategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

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
public class TbContentCategoryServiceImpl implements TbContentCategoryService {
    @Reference
    private TbContentCategoryDubboService tbContentCategoryDubboService;
    @Override
    public List<EazyUITree> showContentCat(long pid) {
        List<TbContentCategory> tbContentCategories = tbContentCategoryDubboService.selectContentCat(pid);
        List<EazyUITree> trees=new ArrayList<>();
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            EazyUITree eazyUITree = new EazyUITree();
            eazyUITree.setId(tbContentCategory.getId());
            eazyUITree.setState(tbContentCategory.getIsParent()?"closed":"open");
            eazyUITree.setText(tbContentCategory.getName());
            trees.add(eazyUITree);
        }
        return trees;
    }

    @Override
    public EgoResults insert(TbContentCategory tbContentCategory) {
        Date date = new Date();
        //页面接到2个属性，还有6个需要自己set
        tbContentCategory.setUpdated(date);
        tbContentCategory.setCreated(date);
        tbContentCategory.setIsParent(false);
        tbContentCategory.setId(IDUtils.genItemId());
        tbContentCategory.setSortOrder(1);
        tbContentCategory.setStatus(1);
        try {
            int i = tbContentCategoryDubboService.insertContentCat(tbContentCategory);
            if (i==1){
                return EgoResults.ok(tbContentCategory);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return EgoResults.error("新增失败");
    }

    @Override
    public EgoResults update(TbContentCategory tbContentCategory) {
        Date date = new Date();
        tbContentCategory.setUpdated(date);
        int i = tbContentCategoryDubboService.updateContentCat(tbContentCategory);
        if (i==1){
            return EgoResults.ok();
        }
        return EgoResults.error("修改失败");
    }

    @Override
    public EgoResults delete(long id) {
        int i = tbContentCategoryDubboService.deleteContentCat(id);
        if (i==1){
            return EgoResults.ok();
        }
        return EgoResults.error("删除分类------失败");
    }
}
