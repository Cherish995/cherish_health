package com.cherish.health.service;

import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
public interface OrderSettingService {
    /**
     * 处理上传文件中的数据(更新或者添加预约设置)
     *
     * @param orderSettingList
     */
    void add(List<OrderSetting> orderSettingList) throws HealthException;

    /**
     * 查询所有预约设置
     *
     * @param date
     * @return
     */
    List<Map<String, Integer>> findDataByDate(String date);

    /**
     * 更新预约设置
     *
     * @param orderSetting
     */
    void update(OrderSetting orderSetting) throws HealthException;
}
