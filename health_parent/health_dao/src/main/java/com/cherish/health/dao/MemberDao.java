package com.cherish.health.dao;

import com.cherish.health.pojo.Member;
import com.cherish.health.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

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


    /**
     * 根据时间查询会员数
     *
     * @param date
     * @return
     */
    Integer findMembersByMonth(String date);

    /**
     * 根据时间查会员数
     *
     * @param today
     * @return
     */
    Integer findMembersByToday(String today);

    /**
     * 总会员数
     *
     * @return
     */
    Integer findTotalMembers();

    /**
     * 获取本周新增会员数
     *
     * @param today
     * @param monday
     * @return
     */
    Integer findThisWeekMembers(@Param("today") String today, @Param("monday") String monday);

    /**
     * 本月新增会员数
     *
     * @param date
     * @return
     */
    Integer findThisMonthMembers(String date);

}
