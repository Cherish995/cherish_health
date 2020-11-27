package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.MemberDao;
import com.cherish.health.pojo.Member;
import com.cherish.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 根据手机号查询用户信息
     *
     * @param telephone
     * @return
     */
    @Override
    public Member findByPhone(String telephone) {
        return memberDao.findByPhoneNumber(telephone);
    }

    /**
     * 注册用户信息
     *
     * @param member
     */
    @Override
    public void register(Member member) {
        memberDao.register(member);
    }
}
