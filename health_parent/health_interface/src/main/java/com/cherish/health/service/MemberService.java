package com.cherish.health.service;

import com.cherish.health.pojo.Member;

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

    void register(Member member);
}
