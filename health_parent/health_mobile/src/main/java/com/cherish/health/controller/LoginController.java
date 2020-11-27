package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.constant.RedisMessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.Member;
import com.cherish.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @PostMapping("/check")
    public Result loginByCode(@RequestBody Map<String, String> loginInfo, HttpServletResponse response) {
        // 获取用户的登录信息
        String telephone = loginInfo.get("telephone");
        String code = loginInfo.get("validateCode");
        // 拿到发送给该用户的验证码
        String key = RedisMessageConstant.SENDTYPE_LOGIN + "-" + telephone;
        Jedis jedis = jedisPool.getResource();
        String reallyCode = jedis.get(key);
        // 判断验证码是否失效
        if (reallyCode == null) return new Result(false, "验证码已失效,请重新获取验证码");
        // 校验用户的验证码
        if (!code.equals(reallyCode)) return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        // 验证码匹配成功后 删除验证码
        jedis.del(key);

        // 判断用户是否已经注册过
        Member member = memberService.findByPhone(telephone);
        if (member == null) {
            // 注册
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            member.setRemark("手机号注册");
            memberService.register(member);
        }

        // 登录身份校验成功 保存用户的登录信息 id 到cookie中
        Cookie cookie = new Cookie("login_member_id", member.getId().toString());
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
