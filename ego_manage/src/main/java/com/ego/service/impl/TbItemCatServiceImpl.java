package com.ego.service.impl;

import com.ego.commons.pojo.EazyUITree;
import com.ego.dubbo.service.TbItemCatDubboService;
import com.ego.pojo.TbItemCat;
import com.ego.service.TbItemCatService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 2/8/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service
public class TbItemCatServiceImpl implements TbItemCatService {
    @Reference
    private TbItemCatDubboService tbItemCatDubboService;
    @Override
    public List<EazyUITree> showTree(long pid) {
        List<EazyUITree> eazyUITrees = new ArrayList<>();
        List<TbItemCat> tbItemCats = tbItemCatDubboService.selectByPid(pid);
        for (TbItemCat tbItemCat : tbItemCats) {
            EazyUITree eazyUITree = new EazyUITree();
            eazyUITree.setId(tbItemCat.getId());
            eazyUITree.setText(tbItemCat.getName());
            eazyUITree.setState(tbItemCat.getIsParent()?"closed":"open");
            eazyUITrees.add(eazyUITree);
        }
        return eazyUITrees;
    }
}
