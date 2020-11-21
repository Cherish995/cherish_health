package com.cherish.health.dao;

import com.cherish.health.pojo.CheckItem;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
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
}
