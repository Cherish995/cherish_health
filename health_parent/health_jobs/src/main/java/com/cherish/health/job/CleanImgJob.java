package com.cherish.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.service.SetmealService;
import com.cherish.health.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
@Component
public class CleanImgJob {
    @Reference
    private SetmealService setmealService;

    private static final Logger log = LoggerFactory.getLogger(CleanImgJob.class);

    @Scheduled(initialDelay = 3000, fixedDelay = 1800000)
    public void cleanImg() {
        log.info("清理任务开始执行....");
        // 拿到七牛云上的所有图片名称集合
        List<String> img7Niu = QiNiuUtils.listFile();
        log.debug("7牛上有{}张图片", null == img7Niu ? 0 : img7Niu.size());
        // 拿到数据库中图片名称的集合
        List<String> imgInDB = setmealService.findImgs();
        log.debug("数据库有{}张图片", null == imgInDB ? 0 : imgInDB.size());
        // 删除七牛云图片名称跟数据库名称一样的数据
        img7Niu.removeAll(imgInDB);
        log.debug("要删除的图片有{}张", img7Niu.size());
        // 删除垃圾图片
        String[] need2Delete = img7Niu.toArray(new String[]{});
        QiNiuUtils.removeFiles(need2Delete);
        log.info("删除{}张图片成功", img7Niu.size());
    }

}
