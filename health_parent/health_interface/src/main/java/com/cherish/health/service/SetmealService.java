package com.cherish.health.service;

import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.Setmeal;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/23
 */
public interface SetmealService {
    /**
     * 分页查询套餐数据列表
     *
     * @param queryPageBean
     * @return
     */
    PageResult<Setmeal> page(QueryPageBean queryPageBean);

    /**
     * 添加套餐数据
     *
     * @param setmeal
     * @param checkgroupIds
     * @throws HealthException
     */
    void add(Setmeal setmeal, Integer[] checkgroupIds) throws HealthException;

    /**
     * 根据id查询套餐数据
     *
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 根据套餐id查询对应的检查组id集合
     *
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIds(Integer id);

    /**
     * 修改套餐相关信息
     *
     * @param setmeal
     * @param checkgroupIds
     * @throws HealthException
     */
    void update(Setmeal setmeal, Integer[] checkgroupIds) throws HealthException;

    /**
     * 根据id 删除套餐信息
     *
     * @param id
     * @throws HealthException
     */
    void delete(Integer id) throws HealthException;

    /**
     * 查询所有的图片名称
     *
     * @return
     */
    List<String> findImgs();

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询更全面的套餐信息
     *
     * @param id
     * @return
     */
    Setmeal findByDetailId(Integer id);


    /**
     * 根据套餐id查询对应订单信息
     *
     * @param id
     * @return
     */
    List<Order> findOrderBySetmealId(Integer id);
}
