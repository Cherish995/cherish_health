package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.constant.RedisMessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.OrderInfo;
import com.cherish.health.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/26
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    /**
     * 预约确认
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody OrderInfo orderInfo) throws Exception {

        if (orderInfo != null) {
            // 获取真实发送到用户手机验证码
            Jedis jedis = jedisPool.getResource();
            String code = jedis.get(RedisMessageConstant.SENDTYPE_ORDER + "-" + orderInfo.getTelephone());
            // 判断验证码是否失效
            if (code == null) return new Result(false, "验证码已失效,请重新获取验证码");
            // 获取用户输入的验证码
            String usrCode = orderInfo.getValidateCode();
            // 判断验证码是否正确
            if (!code.equals(usrCode)) return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            // 销毁验证码
            jedis.del(RedisMessageConstant.SENDTYPE_ORDER + "-" + orderInfo.getTelephone());
            // 预约信息录入
            // 设置预约信息
            orderInfo.setOrderType(Order.ORDERTYPE_WEIXIN);
            orderInfo.setRegisterDate(new Date());
            Order order = orderService.submitOrder(orderInfo);

            return new Result(true, MessageConstant.ORDER_SUCCESS, order);
        }
        // 预约失败
        return new Result(false, "系统异常," + MessageConstant.ORDER_FAIL);
    }

    /**
     * 预约信息显示
     *
     * @param id
     * @return
     */
    @PostMapping("/findById")
    public Result findById(Integer id) {
        OrderInfo orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderInfo);
    }
}
