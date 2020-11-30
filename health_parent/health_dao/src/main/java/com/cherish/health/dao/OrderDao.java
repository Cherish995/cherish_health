package com.cherish.health.dao;

import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
public interface OrderDao {
    void addOrder(OrderInfo orderInfo);

    Order findOrder(OrderInfo orderInfo);

    Order findById(Integer orderId);

    /**
     * 预约信息显示
     *
     * @param id
     * @return
     */
    Map findOrderInfoByOrderId(Integer id);

    /**
     * 获取指定日期预约数
     *
     * @param today
     * @return
     */
    Integer findTodayOrderNumber(String today);

    /**
     * 获取指定日期到诊人数
     *
     * @param today
     * @param orderstatusYes
     * @return
     */
    Object findTodayVisitsNumber(@Param("today") String today, @Param("status") String orderstatusYes);

    /**
     * 本周到诊人数
     *
     * @param today
     * @param monday
     * @param orderstatusYes
     * @return
     */
    Integer findThisWeekVisitsNumber(@Param("today") String today, @Param("monday") String monday, @Param("status") String orderstatusYes);

    /**
     * 本月到诊人数
     *
     * @param month
     * @param orderstatusYes
     * @return
     */
    Integer findThisMonthVisitsNumber(@Param("date") String month, @Param("status") String orderstatusYes);

    /**
     * 本月预约数
     *
     * @param month
     * @return
     */
    Integer findThisMonthOrderNumber(String month);

    /**
     * 本周预约数
     *
     * @param monday
     * @param sunday
     * @return
     */
    Integer findThisWeekOrderNumber(@Param("monday") String monday, @Param("sunday") String sunday);
}
