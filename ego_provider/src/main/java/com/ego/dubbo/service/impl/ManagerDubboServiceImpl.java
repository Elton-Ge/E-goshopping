package com.ego.dubbo.service.impl;

import com.ego.dubbo.service.ManagerDubboService;
import com.ego.mapper.ManagerMapper;
import com.ego.pojo.Manager;
import com.ego.pojo.ManagerExample;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego.dubbo.service.impl
 * @version: 1.0
 */
@Service     //为dubbo的service
public class ManagerDubboServiceImpl implements ManagerDubboService {
    @Autowired
    private ManagerMapper managerMapper;
    @Override
    public Manager selectManagerByUsername(String username) {
        ManagerExample managerExample = new ManagerExample();
        managerExample.createCriteria().andUsernameEqualTo(username);
        List<Manager> managers = managerMapper.selectByExample(managerExample);
        if (managers!=null&&managers.size()>0){
            return managers.get(0);
        }
        return null;
    }
}
