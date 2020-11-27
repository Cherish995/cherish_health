package com.cherish.health.dao;

import com.cherish.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
public interface OrderSettingDao {
    /**
     * 根据日期查预约设置
     *
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderDate(Date orderDate);

    /**
     * 更新预约设置
     *
     * @param orderSetting
     */
    void update(OrderSetting orderSetting);

    /**
     * 添加预约设置
     *
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 查询所有预约设置
     *
     * @param date
     * @return
     */
    List<Map<String, Integer>> findDataByDate(String date);

    /**
     * 修改已预约数
     *
     * @param orderDate
     */
    void updateByOrderDate(Date orderDate);
}
