package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.constant.RedisMessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.exception.MyException;
import com.cherish.health.pojo.*;
import com.cherish.health.service.OrderService;
import com.cherish.health.service.SetmealService;
import com.cherish.health.utils.DateUtils;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Date;
import java.util.Map;

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

    @Reference
    private SetmealService setmealService;

    /**
     * 预约确认
     *
     * @param orderInfo
     * @return
     */
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody OrderInfo orderInfo) {

        if (orderInfo != null) {
            Date orderDate = orderInfo.getOrderDate();
            if (orderDate == null) throw new MyException("日期为空");
            try {
                DateUtils.parseDate2String(orderDate);
            } catch (Exception e) {
                throw new MyException("日期格式错误");
            }
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
        Map<String, Object> orderInfo = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, orderInfo);
    }

    @GetMapping("/exportSetmealInfo")
    public void exportSetmealInfo(Integer id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 根据id查询预约订单信息
        Map<String, Object> map = orderService.findById(id);
        // 获取套餐id
        Integer setmeal_id = (Integer) map.get("setmeal_id");
        String name = (String) map.get("name");
        // 根据套餐id查询套餐详情
        Setmeal setmeal = setmealService.findByDetailId(setmeal_id);

        // 下载导出
        // 设置头信息
        response.setContentType("application/pdf");
        String filename = name + ":预约订单信息.pdf"; // 文件名
        // 解决下载的文件名 中文乱码
        filename = new String(filename.getBytes(), "ISO-8859-1");

        // 设置以附件的形式导出
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);

        // 生成PDF文件
        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        // 设置表格字体
        BaseFont cn = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font font = new Font(cn, 10, Font.NORMAL, Color.BLUE);

        // 写出PDF数据
        document.add(new Paragraph("体检人：" + (String) map.get("name"), font));
        document.add(new Paragraph("体检套餐：" + (String) map.get("setmeal"), font));
        document.add(new Paragraph("体检日期：" + (String) map.get("orderDate"), font));
        document.add(new Paragraph("预约类型：" + (String) map.get("orderType"), font));

        //  生成3列表格
        Table table = new Table(3);//创建3列的表格
        // 设置样式
        setTableFont(table);
        // 设置表头信息
        table.addCell(buildCell("项目名称", font));
        table.addCell(buildCell("项目内容", font));
        table.addCell(buildCell("项目解读", font));
        // 写数据
        for (CheckGroup checkGroup : setmeal.getCheckGroups()) {
            table.addCell(buildCell(checkGroup.getName(), font));
            // 组织检查项集合
            StringBuffer checkItems = new StringBuffer();
            for (CheckItem checkItem : checkGroup.getCheckItems()) {
                checkItems.append(checkItem.getName() + "  ");
            }
            //  去掉检查项首尾空格
            table.addCell(buildCell(checkItems.toString().trim(), font));
            table.addCell(buildCell(checkGroup.getRemark(), font));
        }
        // 将表格加入文档
        document.add(table);
        document.close();

    }

    // 传递内容和字体样式，生成单元格
    private Cell buildCell(String content, Font font) throws BadElementException {
        Phrase phrase = new Phrase(content, font);
        return new Cell(phrase);
    }

    /**
     * 设置表格样式
     *
     * @param table
     */
    private void setTableFont(Table table) {
        table.setWidth(80); // 宽度
        table.setBorder(1); // 边框
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER); //水平对齐方式
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP); // 垂直对齐方式
        /*设置表格属性*/
        table.setBorderColor(new Color(0, 0, 255)); //将边框的颜色设置为蓝色
        table.setPadding(5);//设置表格与字体间的间距
        //table.setSpacing(5);//设置表格上下的间距
        table.setAlignment(Element.ALIGN_CENTER);//设置字体显示居中样式
    }

}
