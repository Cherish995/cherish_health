package com.cherish.health.dao;

import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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
    Integer addSetmeal(Setmeal setmeal);

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

    /**
     * 查询所有图片名称
     *
     * @return
     */
    List<String> findAllImgs();

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    List<Setmeal> findAll();

    /**
     * 查询套餐详情页信息
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

    /**
     * 查询所有套餐名字以及对应的订单数量
     *
     * @return
     */
    List<Map<String, Integer>> findOrdersBySetmeal();

    /**
     * 热门套餐信息
     *
     * @return
     */
    List<Map<String, Object>> findHotSetmeal();

}
