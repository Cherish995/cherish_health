package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.MemberDao;
import com.cherish.health.pojo.Member;
import com.cherish.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 根据时间查询会员数
     *
     * @param dateList
     * @return
     */
    @Override
    public List<Integer> findMembersByMonth(List<String> dateList) {
        if (dateList == null) return null;
        List<Integer> members = new ArrayList<>();
        for (String date : dateList) {
            date += "-31";
            members.add(memberDao.findMembersByMonth(date));
        }
        return members;
    }
}
