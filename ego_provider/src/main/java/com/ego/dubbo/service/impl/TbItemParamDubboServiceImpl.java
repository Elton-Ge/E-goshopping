package com.ego.dubbo.service.impl;

import com.ego.commons.exception.DaoException;
import com.ego.dubbo.service.TbItemParamDubboService;
import com.ego.mapper.TbItemParamMapper;
import com.ego.pojo.TbItemParam;
import com.ego.pojo.TbItemParamExample;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 3/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbItemParamDubboServiceImpl implements TbItemParamDubboService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;
    @Override
    public List<TbItemParam> selectByPage(int pageNumber, int pageSize) {
        PageHelper.startPage(pageNumber, pageSize);
        //投影查询时，如果查询结果包含text类型列，一定要使用withBLOBs。 此外null指的是没有where条件。
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(null);
        //一定不要忘记把list放入PageINfo里
        PageInfo<TbItemParam> pi = new PageInfo<>(list);
        return pi.getList();
    }

    @Override
    public long countTotal() {
        long l = tbItemParamMapper.countByExample(null);
        return l;
    }

    @Override
    public TbItemParam selectByCatid(long catId) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        tbItemParamExample.createCriteria().andItemCatIdEqualTo(catId);

        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        if (list!=null&&list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbItemParam tbItemParam) {
        return tbItemParamMapper.insert(tbItemParam);
    }

    @Override
    @Transactional
    public int delete(long[] ids) throws DaoException{
        int i=0;
        for (long id : ids) {
            i+= tbItemParamMapper.deleteByPrimaryKey(id);
        }
        if (i==ids.length){
            return 1;
        }
        throw  new DaoException("删除失败");
    }
}
