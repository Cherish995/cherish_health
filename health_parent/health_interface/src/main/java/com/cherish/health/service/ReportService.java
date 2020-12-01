package com.cherish.health.service;

import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/30
 */
public interface ReportService {


    /**
     * 获取运营数据统计
     * @return
     */
    Map<String, Object> getBusinessReportData();

}
