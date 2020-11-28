package com.cherish.health.dao;

import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.OrderInfo;

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
}
