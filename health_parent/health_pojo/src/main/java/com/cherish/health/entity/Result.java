package com.cherish.health.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    private boolean flag;//执行结果，true为执行成功 false为执行失败
    private String message;//返回结果信息
    private Object data;//返回数据

    public Result(boolean flag, String message) { // 保存、修改保存
        super();
        this.flag = flag;
        this.message = message;
    }
}