package com.cherish.health.service;

import com.cherish.health.pojo.User;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/29
 */
public interface UserService {
    /**
     * 根据用户名查询用户角色、权限信息
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
