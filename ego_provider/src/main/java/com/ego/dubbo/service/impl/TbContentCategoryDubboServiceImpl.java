package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbContentCategoryDubboService;
import com.ego.mapper.TbContentCategoryMapper;
import com.ego.pojo.TbContentCategory;
import com.ego.pojo.TbContentCategoryExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbContentCategoryDubboServiceImpl implements TbContentCategoryDubboService {
    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;
    @Override
    public List<TbContentCategory> selectContentCat(long pid) {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andStatusEqualTo(1).andParentIdEqualTo(pid);
        tbContentCategoryExample.setOrderByClause("sort_order asc");
        return tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
    }

    @Override
    @Transactional
    public int insertContentCat(TbContentCategory tbContentCategory) throws DaoException {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andNameEqualTo(tbContentCategory.getName()).andStatusEqualTo(1);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //说明没有重复
        if (list!=null&&list.size()==0){
            int index = tbContentCategoryMapper.insert(tbContentCategory);
            if (index==1){
                //判断父类目is parent是否为false， 如果是false，则修改父类目
                TbContentCategory parentCategory = tbContentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
                if (!parentCategory.getIsParent()){
                    TbContentCategory parentUpdated = new TbContentCategory();
                    parentUpdated.setId(parentCategory.getId());
                    parentUpdated.setIsParent(true);
                    parentUpdated.setUpdated(tbContentCategory.getCreated());
                    int i = tbContentCategoryMapper.updateByPrimaryKeySelective(parentUpdated);
                    if (i!=1){
                       throw new DaoException("新增失败1");
                    }
                }
                return 1;
            }
        }
       throw new DaoException("新增失败2");
    }

    /*
    如果方法中出现多条DML操作，需要进行事务控制。添加@Transactional，抛出异常等。
    如果方法中只有一条DML，认为只要不出现异常同时返回结果正确就可以。
     */
    @Override
    public int updateContentCat(TbContentCategory tbContentCategory) throws DaoException {
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andNameEqualTo(tbContentCategory.getName()).andStatusEqualTo(1);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        //说明没有重复
        if (list!=null&&list.size()==0){
            return tbContentCategoryMapper.updateByPrimaryKeySelective(tbContentCategory);
        }
        return 0;
    }

    @Override
    @Transactional
    public int deleteContentCat(long id) throws DaoException{
        TbContentCategory category = new TbContentCategory();
        category.setId(id);
        Date date = new Date();
        category.setUpdated(date);
        category.setStatus(2);
        // 当前分类逻辑删除功能
        int index = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
        if(index==1){
            // 判断当前节点的父节点是否还有其他正常状态的子节点。

            // 查询当前节点，能查询出当前节点的父节点是谁
            TbContentCategory currContentCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
            TbContentCategoryExample example = new TbContentCategoryExample();
            example.createCriteria().andParentIdEqualTo(currContentCategory.getParentId()).andStatusEqualTo(1);
            List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
            // 父分类已经没有正常状态的子节点，修改is_parent为false
            if(list!=null&&list.size()==0){
                TbContentCategory parent = new TbContentCategory();
                parent.setIsParent(false);
                parent.setId(currContentCategory.getParentId());
                parent.setUpdated(date);
                int indexParent = tbContentCategoryMapper.updateByPrimaryKeySelective(parent);
                if(indexParent!=1){
                    throw new DaoException("删除分类 - 修改父分类失败");
                }
            }
            if(currContentCategory.getIsParent()){
                deleteChildrenById(id,date);
            }
            return 1;
        }
        throw new DaoException("删除分类 - 失败");
    }

    /**
     * 递归
     * @param id 父id
     * @param date     更新时间
     * @throws DaoException
     */
    private void deleteChildrenById(long id, Date date) throws DaoException{
        TbContentCategoryExample tbContentCategoryExample = new TbContentCategoryExample();
        tbContentCategoryExample.createCriteria().andParentIdEqualTo(id);
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(tbContentCategoryExample);
        for (TbContentCategory category : list) {
            category.setStatus(2);
            category.setUpdated(date);
            int i = tbContentCategoryMapper.updateByPrimaryKeySelective(category);
            if (i==1){
                deleteChildrenById(category.getId(),date);
            }
            else {
                throw new DaoException("删除分类 失败");
            }
        }

    }
}
