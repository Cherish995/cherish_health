package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.OrderSettingDao;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.OrderSetting;
import com.cherish.health.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 处理上传文件中的数据(更新或者添加预约设置)
     *
     * @param orderSettingList
     */
    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null) {
            // 遍历
            for (OrderSetting orderSetting : orderSettingList) {
                // 判断数据 做出对应操作(记录不存在于数据库则添加,存在则再判断是否能更新)
                OrderSetting os = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
                if (os != null) {
                    // 存在 判断是否能更新
                    if (os.getReservations() > orderSetting.getNumber())
                        // 不可更新
                        throw new HealthException("可预约数不能小已预约数");
                    // 可以更新
                    orderSettingDao.update(orderSetting);
                } else
                    // 不存在 直接添加
                    orderSettingDao.add(orderSetting);
            }
        }
    }

    /**
     * 查询所有预约设置
     *
     * @param date
     * @return
     */
    @Override
    public List<Map<String, Integer>> findDataByDate(String date) {
        date += "%";
        return orderSettingDao.findDataByDate(date);
    }

    /**
     * 更新预约设置
     *
     * @param orderSetting
     */
    @Override
    @Transactional
    public void update(OrderSetting orderSetting) {
        // 通过日期查询旧预约设置信息
        OrderSetting old_orderSetting = orderSettingDao.findByOrderDate(orderSetting.getOrderDate());
        // 判断旧预约设置是否存在
        if (old_orderSetting != null) {
            // 判断更新后的最大预约数是否大等于已预约人数
            if (orderSetting.getNumber() < old_orderSetting.getReservations()) {
                // 报错 已预约数超过最大预约数，接口异常声明
                throw new HealthException("可预约数不能小于已预约数】");
            }
            // 可以更新最大预约数
            orderSettingDao.update(orderSetting);
        } else {
            // 添加预约设置
            orderSettingDao.add(orderSetting);
        }
    }
}
