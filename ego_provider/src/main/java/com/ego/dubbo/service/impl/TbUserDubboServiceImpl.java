package com.ego.dubbo.service.impl;

import com.ego.commons.utils.IDUtils;
import com.ego.dubbo.service.TbUserDubboService;
import com.ego.mapper.TbUserMapper;
import com.ego.pojo.TbUser;
import com.ego.pojo.TbUserExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 8/8/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service
public class TbUserDubboServiceImpl implements TbUserDubboService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectByUser(TbUser user) {
        TbUserExample tbUserExample = new TbUserExample();
        if (user.getUsername() != null) {         //1
            tbUserExample.createCriteria().andUsernameEqualTo(user.getUsername());
        } else if (user.getPhone() != null) {       //2
            tbUserExample.createCriteria().andPhoneEqualTo(user.getPhone());
        } else if (user.getEmail() != null) {       //3
            tbUserExample.createCriteria().andEmailEqualTo(user.getEmail());
        }
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public int insert(TbUser tbUser) {
        return tbUserMapper.insert(tbUser);  
    }

    @Override
    public TbUser selectByUsernamePwd(TbUser tbUser) {
        TbUserExample tbUserExample = new TbUserExample();
        tbUserExample.createCriteria().andUsernameEqualTo(tbUser.getUsername()).andPasswordEqualTo(tbUser.getPassword());
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        if (list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
