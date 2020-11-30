package com.cherish.health.service;

import com.cherish.health.pojo.Member;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
public interface MemberService {
    /**
     * 根据手机号查询用户信息
     *
     * @param telephone
     * @return
     */
    Member findByPhone(String telephone);

    /**
     * 注册会员
     *
     * @param member
     */
    void register(Member member);

    /**
     * 根据时间查询会员数
     *
     * @param dateList
     * @return
     */
    List<Integer> findMembersByMonth(List<String> dateList);
}
