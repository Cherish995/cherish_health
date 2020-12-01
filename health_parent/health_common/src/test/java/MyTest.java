import com.aliyuncs.exceptions.ClientException;
import com.cherish.health.utils.SMSUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/26
 */
public class MyTest {
    @Test
    public void test() throws ClientException {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, "13277312137", "666666");
    }

    @Test
    public void test2() throws Exception {
        String jrxml = "E:\\cherish_health\\cherish_health\\health_parent\\health_common\\src\\main\\resources\\demo.jrxml";
        // 设置编译后的路径
        String jasper = "E:\\cherish_health\\cherish_health\\health_parent\\health_common\\src\\main\\resources\\demo.jasper";
        // 第一个参数: jrxml, 第二个参为编译后的文件 jasper
        JasperCompileManager.compileReportToFile(jrxml, jasper);
        // 构建数据
        //构造数据
        Map paramters = new HashMap();
        paramters.put("reportDate", "2019-10-10");
        paramters.put("company", "itcast");

        List<Map> list = new ArrayList();
        Map map1 = new HashMap();
        map1.put("name", "卢本伟牛逼");
        map1.put("address", "beijing");
        map1.put("email", "xiaoming@itcast.cn");
        Map map2 = new HashMap();
        map2.put("name", "xiaoli");
        map2.put("address", "nanjing");
        map2.put("email", "xiaoli@itcast.cn");
        list.add(map1);
        list.add(map2);

        // 填充数据 JRBeanCollectionDataSource自定数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, paramters, new JRBeanCollectionDataSource(list));
        // 导出保存
        String pdfPath = "D:\\test.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint, pdfPath);
    }

    @Test
    public void test3() throws Exception{
        String jrxml = "E:\\cherish_health\\cherish_health\\health_parent\\health_common\\src\\main\\resources\\demo2.jrxml";
        // 设置编译后的路径
        String jasper = "E:\\cherish_health\\cherish_health\\health_parent\\health_common\\src\\main\\resources\\demo2.jasper";
        // 编译模板文件
        // 第一个参数: jrxml, 第二个参为编译后的文件 jasper
        JasperCompileManager.compileReportToFile(jrxml, jasper);
        // 构建数据
        //构造数据
        Map paramters = new HashMap();
        paramters.put("company","传智播客");

        List<Map> list = new ArrayList();
        Map map1 = new HashMap();
        map1.put("tName","入职体检套餐");
        map1.put("tCode","RZTJ");
        map1.put("tAge","18-60");
        map1.put("tPrice",500d);

        Map map2 = new HashMap();
        map2.put("tName","阳光爸妈老年健康体检");
        map2.put("tCode","YGBM");
        map2.put("tAge","55-60");
        map2.put("tPrice",500d);
        list.add(map1);
        list.add(map2);

        // 填充数据 JRBeanCollectionDataSource自定数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, paramters, new JRBeanCollectionDataSource(list));
        // 导出保存
        String pdfPath = "D:\\testJavaBean.pdf";
        JasperExportManager.exportReportToPdfFile(jasperPrint,pdfPath);
    }
}
