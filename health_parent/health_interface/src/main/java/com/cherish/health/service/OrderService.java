package com.cherish.health.service;

import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.OrderInfo;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
public interface OrderService {
    /**
     * 预约确认
     *
     * @param orderInfo
     * @return
     */
    Order submitOrder(OrderInfo orderInfo) throws HealthException;

    /**
     * 预约信息显示
     *
     * @param id
     * @return
     */
    OrderInfo findById(Integer id);
}
