package com.cherish.health.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装查询条件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryPageBean implements Serializable {
    private Integer currentPage;//页码
    private Integer pageSize;//每页记录数
    private String queryString;//查询条件

    private Integer offset; // 分页查询，开始记录下标

    /**
     * 获取分页起始记录位置
     * 根据分页页数，计算limit其实记录
     *
     * @return
     */
    public Integer getOffset() {
        return (currentPage - 1) * pageSize;
    }
}