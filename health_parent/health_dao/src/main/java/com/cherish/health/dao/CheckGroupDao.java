package com.cherish.health.dao;

import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/22
 * <p>
 * 针对检查组数据处理接口
 * </p>
 */
public interface CheckGroupDao {
    /**
     * 添加检查组信息
     *
     * @param checkGroup
     */
    void addCheckGroup(CheckGroup checkGroup);

    /**
     * 添加检查组与检查项关联信息
     *
     * @param checkGroupId
     * @param checkitemId
     */
    void addCGCI(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    /**
     * 根据id 查询检查项信息
     *
     * @param id
     * @return
     */
    CheckGroup findById(Integer id);

    /**
     * 根据检查组id 查询对应的检查项id集合
     *
     * @param id
     * @return
     */
    List<Integer> findCheckitemIds(Integer id);

    /**
     * 根据查询条件查询满足的总记录数
     *
     * @param queryPageBean
     * @return
     */
    Long findCount(QueryPageBean queryPageBean);

    /**
     * 查询当前页面需要展示的数据信息集合
     *
     * @param queryPageBean
     * @return
     */
    List<CheckGroup> findList(QueryPageBean queryPageBean);

    /**
     * 根据检查组id删除检查组与检查项关联表的记录
     *
     * @param id
     */
    void deleteAllById(Integer id);

    /**
     * 修改对应的检查组的信息
     *
     * @param checkGroup
     */
    void updateCheckGroup(CheckGroup checkGroup);

    /**
     * 修改对应的检查组与检查项关联表的记录
     *
     * @param id
     * @param checkitemId
     */
    void updateCheckGroupAndCheckItem(@Param("cgId") Integer id, @Param("ciId") Integer checkitemId);

    /**
     * 根据检查组id查询关联套餐数量
     *
     * @param id
     * @return
     */
    Long findSetmeal(Integer id);

    /**
     * 根据检查组id删除检查组信息
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据id 删除检查组与套餐关联表记录
     *
     * @param id
     */
    void deleteCheckGroupAndCheckItemById(Integer id);

    /**
     * 查询检查组列表集合
     *
     * @return
     */
    List<CheckGroup> findAll();

    Integer findSetmealId(Integer id);
}
