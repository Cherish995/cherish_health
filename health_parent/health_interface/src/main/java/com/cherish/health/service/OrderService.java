package com.cherish.health.service;

import com.cherish.health.exception.MyException;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.OrderInfo;

import java.util.Map;

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
    Order submitOrder(OrderInfo orderInfo) throws MyException;

    /**
     * 预约信息显示
     *
     * @param id
     * @return
     */
    Map<String, Object> findById(Integer id);
}
