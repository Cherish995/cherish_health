package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.OrderSetting;
import com.cherish.health.service.OrderSettingService;
import com.cherish.health.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 处理用户上传的文件的请求 并处理数据
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile file) {
        try {
            // 解析excl文件内容
            List<String[]> strings = POIUtils.readExcel(file);
            // 设置解析器指定解析格式
            final SimpleDateFormat dateFormat = new SimpleDateFormat(POIUtils.DATE_FORMAT);
            // 将解析excl的内容 转换成映射的OrderSetting 的集合
            List<OrderSetting> orderSettingList = strings.stream().map(stringArr -> {
                // 新建一个ordersetting 对象 来存储转换后的数据
                OrderSetting orderSetting = new OrderSetting();
                // 待解析的日期数据
                String date = stringArr[0];
                // 存储转换后的数据
                try {
                    // 解析并存储
                    orderSetting.setOrderDate(dateFormat.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                orderSetting.setNumber(Integer.valueOf(stringArr[1]));
                // 返回存储数据转换后的对象
                return orderSetting;
            }).collect(Collectors.toList());

            // 导入预约的设置
            orderSettingService.add(orderSettingList);
        } catch (IOException e) {
            // 导入失败
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        // 导入成功
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    /**
     * 查询预约设置集合
     *
     * @param date
     * @return
     */
    @GetMapping("/findDataByDate")
    public Result findDataByDate(String date) {
        // 调业务
        List<Map<String, Integer>> dataList = orderSettingService.findDataByDate(date);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, dataList);
    }

    /**
     * 更新预约设置
     *
     * @param orderSetting
     * @return
     */
    @PostMapping("update")
    public Result update(@RequestBody OrderSetting orderSetting) {
        orderSettingService.update(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
