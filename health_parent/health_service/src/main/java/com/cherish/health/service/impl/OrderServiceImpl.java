package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.dao.MemberDao;
import com.cherish.health.dao.OrderDao;
import com.cherish.health.dao.OrderSettingDao;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.Member;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.OrderInfo;
import com.cherish.health.pojo.OrderSetting;
import com.cherish.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/27
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    /**
     * 预约确认(录入)
     *
     * @param orderInfo
     * @return
     */
    @Override
    @Transactional
    public Order submitOrder(OrderInfo orderInfo) {
        /*
         * 判断是否有预约设置信息(所选日期 用户预约 是否开放)
         */
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderInfo.getOrderDate());
        // 不存在预约设置信息 就报错
        if (orderSetting == null) throw new HealthException("很抱歉,所选日期不能预约");
        /*
         *  准备预约信息录入
         *  判断是否 预约已满
         * */
        int number = orderSetting.getNumber(); //  可预约总数
        int reservations = orderSetting.getReservations(); // 已预约数
        // 预约数已满 报错
        if (number <= reservations) throw new HealthException(MessageConstant.ORDER_FULL);

        /*
         * 判断是否为会员
         */
        Member member = memberDao.findByPhoneNumber(orderInfo.getTelephone());
        if (member == null) {
            // 不是会员 将该用户信息录入到会员中
            orderInfo.setPassword(orderInfo.getIdCard().substring(orderInfo.getIdCard().length() - 6)); // 设置初始密码
            memberDao.add(orderInfo);
        } else {
            // 是会员 获取会员 id
            orderInfo.setMemberId(member.getId());
        }
        /*
         * 判断是否重复预约
         * */
        Order order = orderDao.findOrder(orderInfo);
        // 重复预约 报错
        if (order != null) throw new HealthException(MessageConstant.HAS_ORDERED);

        orderInfo.setOrderStatus(Order.ORDERSTATUS_NO);

        /**
         * 预约之前 要修改已预约数
         */
        int update = orderSettingDao.updateByOrderDate(orderInfo.getOrderDate());
        if (update == 0) throw new HealthException(MessageConstant.ORDER_FULL);
        //  生成订单 添加订单信息
        orderDao.addOrder(orderInfo);

        /**
         * 判断是否预约成功
         */
        Order order1 = orderDao.findById(orderInfo.getOrderId());
        if (order1 == null) throw new HealthException(MessageConstant.ORDER_FAIL);

        // 预约成功
        return order1;
    }

    /**
     * 预约信息显示
     *
     * @param id
     * @return
     */
    @Override
    public Map findById(Integer id) {
        return orderDao.findOrderInfoByOrderId(id);
    }

}
