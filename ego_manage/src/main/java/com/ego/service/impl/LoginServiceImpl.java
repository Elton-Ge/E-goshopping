package com.ego.service.impl;

import com.ego.dubbo.service.ManagerDubboService;
import com.ego.pojo.Manager;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Auther: Elton Ge
 * @Date: 31/7/20
 * @Description: com.ego.service.impl
 * @version: 1.0
 */
@Service   //consumer的@service为spring的service， 而provider的为dubbo的@service
public class LoginServiceImpl implements UserDetailsService {
    @Reference    // 为dubbo的Reference
    private ManagerDubboService managerDubboService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerDubboService.selectManagerByUsername(username);
        if (manager==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        return new User(username, manager.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("不涉及权限"));
    }
}
