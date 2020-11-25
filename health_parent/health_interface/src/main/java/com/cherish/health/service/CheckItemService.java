package com.cherish.health.service;

import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.CheckItem;

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
    void add(CheckItem checkItem) throws HealthException;

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
    void deleteById(Integer id) throws HealthException;

    /**
     * 修改检查项信息
     *
     * @param checkItem 更新的检查项信息
     */
    void update(CheckItem checkItem) throws HealthException;

    CheckItem findById(Integer id);

    List<Integer> findCheckGroupId(Integer id);
}
