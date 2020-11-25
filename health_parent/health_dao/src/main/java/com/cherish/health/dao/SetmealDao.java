package com.cherish.health.dao;

import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/23
 */
public interface SetmealDao {
    /**
     * 查询套餐数据总条数
     *
     * @param queryPageBean
     * @return
     */
    Long findCount(QueryPageBean queryPageBean);

    /**
     * 分页查询套餐数据列表
     *
     * @param queryPageBean
     * @return
     */
    List<Setmeal> page(QueryPageBean queryPageBean);

    /**
     * 添加套餐数据
     *
     * @param setmeal
     */
    void addSetmeal(Setmeal setmeal);

    /**
     * 添加套餐与检查组关联数据
     *
     * @param id
     * @param checkgroupId
     */
    void addSetmealAndCheckGroup(@Param("sid") Integer id, @Param("cid") Integer checkgroupId);

    /**
     * 根据id查询套餐数据
     *
     * @param id
     * @return
     */
    Setmeal findById(Integer id);

    /**
     * 根据id 查询套餐对应的检查组id集合
     *
     * @param id
     * @return
     */
    List<Integer> findCheckGroupIds(Integer id);

    /**
     * 更新套餐数据
     *
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 根据id删除套餐与检查组关联数据
     *
     * @param id
     */
    void deleteCheckGroupIdsBySetmealId(Integer id);

    /**
     * 查询与套餐有关的订单数量
     *
     * @param id
     * @return
     */
    Long findOrders(Integer id);

    /**
     * 根据id删除套餐数据
     *
     * @param id
     */
    void deleteSetmealById(Integer id);

    List<Integer> findOrderId(Integer id);

    List<String> findAllImgs();

}
