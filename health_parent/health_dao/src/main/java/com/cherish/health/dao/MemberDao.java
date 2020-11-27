package com.cherish.health.dao;

import com.cherish.health.pojo.Member;
import com.cherish.health.pojo.OrderInfo;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
public interface MemberDao {
    /**
     * 根据手机号码查询会员信息
     *
     * @param telephone
     * @return
     */
    Member findByPhoneNumber(String telephone);

    /**
     * 录入会员信息
     *
     * @param orderInfo
     */
    void add(OrderInfo orderInfo);

    /**
     * 注册用户信息
     *
     * @param member
     */
    void register(Member member);
}
