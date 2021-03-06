package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @GetMapping("/getUsername")
    public Result getLoginUserName() {
        // 获取用户的认证信息
        User loginUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 获取用户名
        String username = loginUser.getUsername();
        // 响应用户名
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, username);
    }

    @RequestMapping("/loginSuccess")
    public Result loginSuccess() {
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }

    @RequestMapping("/loginFail")
    public Result loginFail() {
        return new Result(false, MessageConstant.LOGIN_FAIL);
    }

}
