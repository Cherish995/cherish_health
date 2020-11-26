package com.cherish.health.service;

import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.CheckGroup;
import com.cherish.health.pojo.Order;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/22
 * <p>
 * * 针对检查组的业务功能接口
 * * </p>
 */
public interface CheckGroupService {
    /**
     * 添加检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     */
    void add(CheckGroup checkGroup, Integer[] checkitemIds) throws HealthException;

    /**
     * 根据id查询检查组信息
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id查询对应的检查项id集合
     *
     * @param id
     * @return
     */
    List<Integer> findCheckitemIds(Integer id);

    /**
     * 分页查询检查组数据
     *
     * @param queryPageBean
     * @return
     */
    PageResult page(QueryPageBean queryPageBean);

    /**
     * 修改更新监察组数据
     *
     * @param checkGroup
     * @param checkitemIds
     */
    void update(CheckGroup checkGroup, Integer[] checkitemIds) throws HealthException;

    /**
     * 根据id 删除监察组数据
     *
     * @param id
     */
    void deleteById(Integer id) throws HealthException;

    /**
     * 查询所有检查组列表集合
     *
     * @return
     */
    List<CheckGroup> findAll();


    /**
     * 根据检查组id查询对应订单信息
     *
     * @param id
     * @return
     */
    List<Order> findOrderByCheckGroupId(Integer id);
}
