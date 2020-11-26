package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.dao.CheckItemDao;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.CheckItem;
import com.cherish.health.pojo.Order;
import com.cherish.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 */
@Service(interfaceClass = CheckItemService.class)
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
    @Transactional
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
        Long total = checkItemDao.findTotal(queryPageBean);
        List<CheckItem> checkItemList = null;
        if (total > 0) {
            checkItemList = checkItemDao.findByPage(queryPageBean);
        }
        PageResult<CheckItem> result = new PageResult<>(total, checkItemList);
        return result;
    }

    /**
     * 根据检查项id删除检查项信息
     *
     * @param id 对应检查项的id
     */
    @Transactional
    @Override
    public void deleteById(Integer id) {
        // 先判断检查项有没有对应的检查组
        int total = checkItemDao.findByCheckItemIdAndCheckGroupCount(id);
        if (total > 0) {
            // 不可以删除
            throw new HealthException(MessageConstant.DELETE_CHECKITEM_FAIL);
        } else {
            // 可以删除
            checkItemDao.deleteById(id);
        }
    }

    /**
     * 修改检查项信息
     *
     * @param checkItem 更新的检查项信息
     */
    @Transactional
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    /**
     * 根据id查询检查项信息
     *
     * @param id
     * @return
     */
    @Override
    public CheckItem findById(Integer id) {
        return checkItemDao.findById(id);
    }


    /**
     * 根据检查项id查询订单信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Order> findOrderByCheckItemId(Integer id) {
        return checkItemDao.findOrderByCheckItemId(id);
    }
}
