package com.cherish.health.controller;

import com.cherish.health.exception.HealthException;
import com.cherish.health.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

/**
 * <p>
 * before
 * after finally
 * after returning return
 * around
 * RestControllerAdvice after throwing controller抛出的异常
 * <p>
 * 日志的打印:
 * info: 打印流程性的东西
 * debug: 记录关键的数据key 订单id
 * error: 打印异常，代替e.printStackTrace, System.out.println
 * </p>
 * try{} catch(异常的类型)
 *
 * @author: Eric
 * @since: 2020/11/22
 */
@RestControllerAdvice
public class HealExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(HealExceptionAdvice.class);

    /***
     * catch(MyException)
     */
    @ExceptionHandler(HealthException.class)
    public Result handleMyException(HealthException e) {
        return new Result(false, e.getMessage());
    }

    /***
     * catch(MyException)
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        //e.printStackTrace();
        //System.out.println();
        log.error("发知未知异常", e);
        return new Result(false, "发知未知异常，请稍后重试");
    }

    /**
     * 密码错误
     *
     * @param he
     * @return
     */
    @ExceptionHandler(BadCredentialsException.class)
    public Result handBadCredentialsException(BadCredentialsException he) {
        return handleUserPassword();
    }

    /**
     * 用户名不存在
     *
     * @param he
     * @return
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public Result handInternalAuthenticationServiceException(InternalAuthenticationServiceException he) {
        return handleUserPassword();
    }

    private Result handleUserPassword() {
        return new Result(false, "用户名或密码错误");
    }

    /**
     * 权限不足
     *
     * @param he
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result handAccessDeniedException(AccessDeniedException he) {
        return new Result(false, "没有权限");
    }
}
