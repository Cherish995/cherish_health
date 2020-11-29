package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.UserDao;
import com.cherish.health.pojo.User;
import com.cherish.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/29
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查询用户角色、权限信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
