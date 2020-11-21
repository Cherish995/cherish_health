package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.CheckItemDao;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.pojo.CheckItem;
import com.cherish.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 */
@Service
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    /**
     * 查询所有检查项信息
     *
     * @return 查询的结果
     */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
     * 添加检查项信息
     *
     * @param checkItem 检查项信息
     */
//    @Transactional
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
     * 分页查询检查项信息
     *
     * @param queryPageBean 分页查询条件
     * @return 分页查询结果
     */
    @Override
    public PageResult findByPage(QueryPageBean queryPageBean) {
        List<CheckItem> checkItemList = checkItemDao.findByPage(queryPageBean);
        Long total = checkItemDao.findTotal();
        PageResult<CheckItem> result = new PageResult<>(total, checkItemList);
        return result;
    }
}
