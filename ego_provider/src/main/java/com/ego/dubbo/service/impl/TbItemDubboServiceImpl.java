package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemDubboService;
import com.ego.mapper.TbItemDescMapper;
import com.ego.mapper.TbItemMapper;
import com.ego.mapper.TbItemParamItemMapper;
import com.ego.pojo.TbItem;
import com.ego.pojo.TbItemDesc;
import com.ego.pojo.TbItemParamItem;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 1/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 * 服务层只进行与数据库相关操作
 *
 */
@Service          // 这个注解表示把当前类实现的接口发布到zookeeper中 端口20880
public class TbItemDubboServiceImpl implements TbItemDubboService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public List<TbItem> selectByPage(int pageNumber, int pageSize) {
        // 分页插件要写在查询上面。否则插件无效。一般都写在第一行
        PageHelper.startPage(pageNumber,pageSize);
        // Example相当于sql中where,没有where时参数为null即可
        // select * from tb_item limit ?,?
        List<TbItem> list = tbItemMapper.selectByExample(null);

        // 千万不要忘记构造方法参数。
        PageInfo<TbItem> tbItemPageInfo = new PageInfo<>(list);
        return tbItemPageInfo.getList();
    }

    @Override
    public long countByExample() {
        //SELECT COUNT(*) FROM tb_item;
        return tbItemMapper.countByExample(null);
    }

    @Override
    @Transactional //监听到异常，进行事务回滚，声明式事务
    public int UpdateStatusByIds(long[] ids, byte status) throws DaoException {
        int i=0;
        for (long id : ids) {
            TbItem tbItem = new TbItem();
            tbItem.setId(id);
            tbItem.setStatus(status);
            tbItem.setUpdated(new Date());
            i+= tbItemMapper.updateByPrimaryKeySelective(tbItem);
        }
        if (i==ids.length){
            return 1;
        }

        throw new DaoException("批量修改失败");
    }

    @Override
    @Transactional /*(rollbackFor = Exception.class)*/ //如果是继续的Exception异常，这里要写上rollbackfor ，而若是继承的RuntimeException，则不要写rollbackfor
    public int insert(TbItem tbItem, TbItemDesc tbItemDesc, TbItemParamItem tbItemParamItem) throws DaoException{
        int index = tbItemMapper.insert(tbItem);
        if (index==1){
            int index2 = tbItemDescMapper.insert(tbItemDesc);
            if (index2==1){
                int index3 = tbItemParamItemMapper.insert(tbItemParamItem);
                if (index3==1){

                    return 1;
                }
            }
        }
        throw new DaoException("新增商品失败");
    }

    @Override
    @Transactional
    public int update(TbItem tbItem, TbItemDesc tbItemDesc,TbItemParamItem tbItemParamItem) throws DaoException {
        int index = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if (index==1){
            int index2 = tbItemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
            if (index2==1){
                int index3 = tbItemParamItemMapper.updateByPrimaryKeySelective(tbItemParamItem);
                if (index3==1){
                    return 1;
                }

            }
        }
        throw  new DaoException("修改商品失败");
    }

    @Override
    public TbItem selectById(long id) {
       return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateTbItem(TbItem tbItem) {
        int i = tbItemMapper.updateByPrimaryKeySelective(tbItem);
        if (i==1){
            return 1;
        }
        return 0;
    }
}
