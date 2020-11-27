package com.cherish.health.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/26
 * <p>
 * 预约信息中转
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo implements Serializable {
    private Integer orderId;// 预约 id
    private Integer memberId; // 会员ID
    private Integer setmealId; // 套餐id
    private String name; // 姓名
    private Integer sex; // 1男 2女
    private String telephone; // 电话号码
    private String validateCode; // 验证码
    private String idCard; // 身份证号
    private Date orderDate;// 预约体检日期
    private String orderType; // 预约类型
    private Date registerDate; // 注册日期
    private String orderStatus; // 预约状态
    private String setmeal; // 套餐信息
}
