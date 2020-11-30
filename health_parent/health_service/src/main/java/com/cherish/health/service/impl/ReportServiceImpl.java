package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.MemberDao;
import com.cherish.health.dao.OrderDao;
import com.cherish.health.dao.SetmealDao;
import com.cherish.health.pojo.Order;
import com.cherish.health.service.ReportService;
import com.cherish.health.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/30
 */
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private SetmealDao setmealDao;

    /**
     * 获取运营数据统计
     *
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String, Object> map = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 指定日期解析格式
        Date date = new Date(); // 当前日期对象
        Date mondayDate = DateUtils.getFirstDayOfWeek(date); // 相对于当前日期的周一日期对象
        Date sundayDate = DateUtils.getLastDayOfWeek(date); // 相对于当前日期的周日日期对象
        String today = dateFormat.format(date); // 今日日期(xxxx-xx-xx)
        String monday = dateFormat.format(mondayDate); // 本周周一
        String sunday = dateFormat.format(sundayDate);
        String month = today.substring(0, 7) + "%"; // 本月
        // 报表统计日期
        map.put("reportDate", today);
        // 今日新增会员数
        map.put("todayNewMember", memberDao.findMembersByToday(today));
        // 总会员数
        map.put("totalMember", memberDao.findTotalMembers());
        // 本周新增会员数
        map.put("thisWeekNewMember", memberDao.findThisWeekMembers(today, monday));
        // 本月新增会员数
        map.put("thisMonthNewMember", memberDao.findThisMonthMembers(month));
        // 今日预约数
        map.put("todayOrderNumber", orderDao.findTodayOrderNumber(today));
        // 今日到诊数
        map.put("todayVisitsNumber", orderDao.findTodayVisitsNumber(today, Order.ORDERSTATUS_YES));
        // 本周预约数
        map.put("thisWeekOrderNumber", orderDao.findThisWeekOrderNumber(monday, sunday));
        // 本周到诊人数
        map.put("thisWeekVisitsNumber", orderDao.findThisWeekVisitsNumber(today, monday, Order.ORDERSTATUS_YES));
        // 本月到诊数
        map.put("thisMonthVisitsNumber", orderDao.findThisMonthVisitsNumber(month, Order.ORDERSTATUS_YES));
        // 本月预约数
        map.put("thisMonthOrderNumber", orderDao.findThisMonthOrderNumber(month));
        // 热门套餐信息
        List<Map<String, Object>> setmeal = setmealDao.findHotSetmeal();
        map.put("hotSetmeal", setmeal);
        return map;
    }

}
