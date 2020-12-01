package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.service.MemberService;
import com.cherish.health.service.ReportService;
import com.cherish.health.service.SetmealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/30
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    /**
     * 获取会员数据统计
     *
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        /**
         * 获取前一年的时间
         */
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -12);
        // 日期解析器
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        /**
         * 解析一年的时间 封装到集合
         */
        List<String> dateList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            // 月份依次增加
            calendar.add(Calendar.MONTH, 1);
            // 获取当前日期对象
            Date date = calendar.getTime();
            // 解析日期对象
            String date_ = dateFormat.format(date);
            // 添加到集合
            dateList.add(date_);
        }
        // 调用业务查询一年的会员的数量集合
        List<Integer> members = memberService.findMembersByMonth(dateList);

        Map map = new HashMap<String, Object>();
        map.put("months", dateList);
        map.put("memberCount", members);
        return new Result(true, "会员报表数据查询成功", map);
    }

    /**
     * 获取套餐圆饼图统计
     *
     * @return
     */
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {
        // 查询套餐的名字以及对应的订单数量
        List<Map<String, Integer>> setmealCount = setmealService.findOrdersBySetmeal();

        // 从  setmealCount 获取套餐名字集合1
        List<String> setmealNames = new ArrayList<>();
        if (setmealCount != null) setmealCount.forEach(map -> setmealNames.addAll(map.keySet()));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", setmealCount);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
    }

    /**
     * 获取运营数据统计
     *
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> resultMap = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, resultMap);
    }

    /**
     * 导出execl
     *
     * @param req
     * @param res
     */
    @GetMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest req, HttpServletResponse res) {
        // 获取模板的路径, getRealPath("/") 相当于到webapp目录下
        String template = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        // 创建工作簿(模板路径)
        try (// 写在try()里的对象，必须实现closable接口，try()cathc()中的finally
             OutputStream os = res.getOutputStream();
             XSSFWorkbook wk = new XSSFWorkbook(template);) {
            // 获取工作表
            XSSFSheet sht = wk.getSheetAt(0);
            // 获取运营统计数据
            Map<String, Object> reportData = reportService.getBusinessReportData();
            // 日期 坐标 2,5
            sht.getRow(2).getCell(5).setCellValue(reportData.get("reportDate").toString());
            //======================== 会员 ===========================
            // 新增会员数 4,5
            sht.getRow(4).getCell(5).setCellValue((Integer) reportData.get("todayNewMember"));
            // 总会员数 4,7
            sht.getRow(4).getCell(7).setCellValue((Integer) reportData.get("totalMember"));
            // 本周新增会员数5,5
            sht.getRow(5).getCell(5).setCellValue((Integer) reportData.get("thisWeekNewMember"));
            // 本月新增会员数 5,7
            sht.getRow(5).getCell(7).setCellValue((Integer) reportData.get("thisMonthNewMember"));

            //=================== 预约 ============================
            sht.getRow(7).getCell(5).setCellValue((Integer) reportData.get("todayOrderNumber"));
            sht.getRow(7).getCell(7).setCellValue((Integer) reportData.get("todayVisitsNumber"));
            sht.getRow(8).getCell(5).setCellValue((Integer) reportData.get("thisWeekOrderNumber"));
            sht.getRow(8).getCell(7).setCellValue((Integer) reportData.get("thisWeekVisitsNumber"));
            sht.getRow(9).getCell(5).setCellValue((Integer) reportData.get("thisMonthOrderNumber"));
            sht.getRow(9).getCell(7).setCellValue((Integer) reportData.get("thisMonthVisitsNumber"));

            // 热门套餐
            List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) reportData.get("hotSetmeal");
            int row = 12;
            for (Map<String, Object> setmealMap : hotSetmeal) {
                sht.getRow(row).getCell(4).setCellValue((String) setmealMap.get("name"));
                sht.getRow(row).getCell(5).setCellValue((Long) setmealMap.get("setmeal_count"));
                BigDecimal proportion = (BigDecimal) setmealMap.get("proportion");
                sht.getRow(row).getCell(6).setCellValue(proportion.doubleValue());
                sht.getRow(row).getCell(7).setCellValue((String) setmealMap.get("remark"));
                row++;
            }

            // 工作簿写给reponse输出流
            res.setContentType("application/vnd.ms-excel");
            String filename = "运营统计数据报表.xlsx";
            // 解决下载的文件名 中文乱码
            filename = new String(filename.getBytes(), "ISO-8859-1");
            // 设置头信息，告诉浏览器，是带附件的，文件下载
            res.setHeader("Content-Disposition", "attachement;filename=" + filename);
            wk.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出PDF
     */
    @GetMapping("/exportBusinessReportPDF")
    public Result exportBusinessReportPDF(HttpServletRequest request, HttpServletResponse response) {
        // 获取PDF模板文件 所在目录路径
        String path = request.getSession().getServletContext().getRealPath("/template");
        String jrxml = path + File.separator + "health_business3.jrxml";
        String jasper = path + File.separator + "health_business3.jasper";

        try {
            // 编译模板
            JasperCompileManager.compileReportToFile(jrxml, jasper);
            // 拿到运营数据
            Map<String, Object> businessReport = reportService.getBusinessReportData();
            // 拿到热门套餐数据
            List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) businessReport.get("hotSetmeal");

            // 填充数据
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, businessReport, new JRBeanCollectionDataSource(hotSetmeal));
            // 设置响应的数据为pdf
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachement;filename=businessReport.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "导出运营数据统计PDF失败");
    }
}
