package com.cherish.health.service;

import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.MyException;
import com.cherish.health.pojo.CheckItem;
import com.cherish.health.pojo.Order;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 * <p>
 * 针对检查项业务功能接口
 * </p>
 */
public interface CheckItemService {
    /**
     * 查询所有检查项信息
     *
     * @return 查询结果
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项信息
     *
     * @param checkItem 检查项信息
     */
    void add(CheckItem checkItem) throws MyException;

    /**
     * 分页查询检查项信息
     *
     * @param queryPageBean 分页查询条件
     * @return 分页查询结果
     */
    PageResult findByPage(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项信息
     *
     * @param id 对应检查项的id
     */
    void deleteById(Integer id) throws MyException;

    /**
     * 修改检查项信息
     *
     * @param checkItem 更新的检查项信息
     */
    void update(CheckItem checkItem) throws MyException;

    /**
     * 根据id查询检查项信息
     *
     * @param id
     * @return
     */
    CheckItem findById(Integer id);

    /**
     * 根据检查项id查询订单信息
     *
     * @param id
     * @return
     */
    List<Order> findOrderByCheckItemId(Integer id);
}
