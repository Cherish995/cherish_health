package com.cherish.health.exception;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 *
 * <p>
 * 自定义异常
 * </p>
 */
public class MyException extends RuntimeException {
    public MyException(String msg) {
        super(msg);
    }
}
