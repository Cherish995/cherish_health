package com.cherish.health.dao;

import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.pojo.CheckItem;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 * <p>
 * 针对处理检查项数据接口
 * </p>
 */
public interface CheckItemDao {
    /**
     * 查询检查项所有信息
     *
     * @return 查询结果
     */
    List<CheckItem> findAll();

    /**
     * 添加检查项信息
     *
     * @param checkItem 检查项信息
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询检查项信息
     *
     * @param queryPageBean 分页查询条件
     * @return 分页查询结果
     */
    List<CheckItem> findByPage(QueryPageBean queryPageBean);

    /**
     * 查询数据总条数
     *
     * @return 分页查询结果
     */
    Long findTotal(QueryPageBean queryPageBean);

    /**
     * 根据id删除检查项信息
     *
     * @param id 对应检查项的id
     */
    void deleteById(Integer id);

    /**
     * 根据检查项id查询对应的检查组的个数
     *
     * @param id 检查项id
     * @return 检查项对应的检查组的个数
     */
    Integer findByCheckItemIdAndCheckGroupCount(Integer id);

    /**
     * 修改检查项信息
     *
     * @param checkItem 更新的检查项信息
     */
    void update(CheckItem checkItem);

    CheckItem findById(Integer id);

    Integer findCheckGroupId(Integer id);
}
