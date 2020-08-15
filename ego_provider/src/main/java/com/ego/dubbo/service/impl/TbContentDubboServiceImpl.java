package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbContentDubboService;
import com.ego.mapper.TbContentMapper;
import com.ego.pojo.TbContent;
import com.ego.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 4/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbContentDubboServiceImpl implements TbContentDubboService {
    @Autowired
    private TbContentMapper tbContentMapper;
    @Override
    public List<TbContent> selectByPage(long categoryId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TbContentExample tbContentExample = new TbContentExample();
        if (categoryId!=0){
            tbContentExample.createCriteria().andCategoryIdEqualTo(categoryId);
        }
        List<TbContent> list = tbContentMapper.selectByExample(tbContentExample);
        PageInfo<TbContent> pi = new PageInfo<>(list);

        return pi.getList();
    }

    @Override
    public long CountbByCategoryId(long categoryId) {
        TbContentExample tbContentExample = new TbContentExample();
        if (categoryId!=0){
            tbContentExample.createCriteria().andCategoryIdEqualTo(categoryId);
        }
       return tbContentMapper.countByExample(tbContentExample);
    }

    @Override
    public int insertTbContent(TbContent tbContent) {
        System.out.println("执行了insert大广告");
       return tbContentMapper.insert(tbContent);
    }

    @Override
    public int updateTbContent(TbContent tbContent) {
        return tbContentMapper.updateByPrimaryKeySelective(tbContent);
    }

    @Override
    @Transactional
    public int deleteTbContent(long[] ids)  throws DaoException{
        int i=0;
        for (long id : ids) {
            i+= tbContentMapper.deleteByPrimaryKey(id);
        }
        if (i== ids.length){
            return 1;
        }
         throw new DaoException("批量删除失败");
    }

    @Override
    public List<TbContent> selectByCategoryId(long categoryId) {
        TbContentExample tbContentExample = new TbContentExample();
        tbContentExample.createCriteria().andCategoryIdEqualTo(categoryId);
        tbContentExample.setOrderByClause("updated desc");
        return tbContentMapper.selectByExampleWithBLOBs(tbContentExample);
    }

    @Override
    public TbContent selectByid(long id) {
        return tbContentMapper.selectByPrimaryKey(id);
    }
}
