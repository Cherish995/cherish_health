package com.cherish.health.aspect;

import com.cherish.health.dao.CheckGroupDao;
import com.cherish.health.dao.CheckItemDao;
import com.cherish.health.dao.SetmealDao;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.CheckGroup;
import com.cherish.health.pojo.CheckItem;
import com.cherish.health.pojo.Setmeal;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/24
 */
@Aspect
@Component
public class HealthAspect {


    @Autowired
    private CheckItemDao checkItemDao;
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Autowired
    private SetmealDao setmealDao;

    /**
     * 增强更新检查项功能
     *
     * @param joinPoint
     */
    @Around("execution(* com.cherish.health.service.impl.CheckItemServiceImpl.update(..))")
    public Object boostUpdateCheckItem(ProceedingJoinPoint joinPoint) {
        try {
            // 拿到参数数组
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) throw new RuntimeException("严重错误!!!");
            CheckItem checkItem = (CheckItem) args[0];
            Integer id = checkItem.getId();
            if (id == null) throw new RuntimeException("严重错误!!!");
            // 判断检查项是否已被订单使用
            if (isCheckItemUsed(id)) {
                CheckItem old_checkItem = checkItemDao.findById(id);
                if (old_checkItem == null) throw new RuntimeException();
                if (old_checkItem.equals(checkItem)) return null;
                else {
                    if (isCheckItemUpdate(old_checkItem, checkItem)) throw new HealthException("已有订单,不能更改");
                    return joinPoint.proceed();
                }

            } else {
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new HealthException(throwable.getMessage());
        }
    }

    /**
     * 增强更新检查组功能
     *
     * @param joinPoint
     */
    @Around("execution(* com.cherish.health.service.impl.CheckGroupServiceImpl.update(..))")
    public Object boostUpdateCheckGroup(ProceedingJoinPoint joinPoint) {
        try {
            // 拿到参数数组
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) throw new RuntimeException("严重错误!!!");

            // 拿到检查组信息
            CheckGroup checkGroup = (CheckGroup) args[0];
            if (checkGroup == null) throw new RuntimeException("严重错误!!!");
            // 拿到检查项id数组(可能为null 无需判断)
            Integer[] ids = (Integer[]) args[1];
            List<Integer> checkitemIds = Arrays.asList(ids);

            Integer id = checkGroup.getId();
            // 判断检查组是否已被订单使用
            if (isCheckGroupUsed(id)) {
                CheckGroup old_checkGroup = checkGroupDao.findById(id);
                List<Integer> old_checkitemIds = checkGroupDao.findCheckitemIds(id);
                if (checkitemIds.equals(old_checkitemIds)) {
                    if (checkGroup.equals(old_checkGroup)) return null;
                } else {
                    throw new HealthException("已有订单使用,修改失败");
                }
                if (isCheckGroupUpdate(old_checkGroup, checkGroup)) {
                    throw new HealthException("已有订单使用,修改失败");
                }
                return joinPoint.proceed();

            } else {
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new HealthException(throwable.getMessage());
        }
    }

    /**
     * 增强更新套餐功能
     *
     * @param joinPoint
     */
    @Around("execution(* com.cherish.health.service.impl.SetmealServiceImpl.update(..))")
    public Object boostUpdateSetmeal(ProceedingJoinPoint joinPoint) {

        try {
            // 拿到参数数组
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) throw new RuntimeException("严重错误!!!");
            // 拿到检查组信息
            Setmeal setmeal = (Setmeal) args[0];
            if (setmeal == null) throw new RuntimeException("严重错误!!!");
            // 拿到检查项id数组(可能为null 无需判断)
            Integer[] ids = (Integer[]) args[1];
            List<Integer> checkgroupIds = Arrays.asList(ids);

            // 判断检查组是否已被订单使用
            Integer id = setmeal.getId();
            if (isSetmealUsed(id)) {
                Setmeal old_setmeal = setmealDao.findById(id);
                List<Integer> old_checkGroupIds = setmealDao.findCheckGroupIds(id);
                if (checkgroupIds.equals(old_checkGroupIds)) {
                    if (setmeal.equals(old_setmeal)) return null;
                } else {
                    throw new HealthException("已有订单使用,修改失败");
                }

                if (isSetmealUpdate(old_setmeal, setmeal)) {
                    throw new HealthException("已有订单使用,修改失败");
                }
                return joinPoint.proceed();
            } else {
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new HealthException(throwable.getMessage());
        }


    }

    /**
     * 判断检查项是否已被订单使用
     *
     * @param id
     * @return
     */
    private Boolean isCheckItemUsed(Integer id) {

        Integer checkGroupId = findCheckGroupIdByCheckItemId(id);
        if (checkGroupId == null) return false;
        Integer setmealId = findSetmealIdByCheckGroupId(checkGroupId);
        if (setmealId == null) return false;
        Integer orderId = findOrderIdBySetmealId(setmealId);
        if (orderId == null) return false;
        return true;
    }

    /**
     * 判断检查组是否已被订单使用
     *
     * @param id
     * @return
     */
    private Boolean isCheckGroupUsed(Integer id) {
        Integer setmealId = findSetmealIdByCheckGroupId(id);
        if (setmealId == null) return false;
        Integer orderId = findOrderIdBySetmealId(setmealId);
        if (orderId == null) return false;
        return true;
    }

    /**
     * 判断检查项是否已被订单使用
     */
    private Boolean isSetmealUsed(Integer id) {
        Integer orderId = findOrderIdBySetmealId(id);
        if (orderId == null) return false;
        return true;
    }

    /**
     * 根据检查项id查询对应的检查组id
     *
     * @param id
     * @return
     */
    private Integer findCheckGroupIdByCheckItemId(Integer id) {
        return checkItemDao.findCheckGroupId(id);
    }

    /**
     * 根据检查组id查询套餐id
     *
     * @param id
     * @return
     */
    private Integer findSetmealIdByCheckGroupId(Integer id) {
        return checkGroupDao.findSetmealId(id);
    }

    /**
     * 根据套餐id查询对应的订单ID
     *
     * @param id
     * @return
     */
    private Integer findOrderIdBySetmealId(Integer id) {
        return setmealDao.findOrderId(id);
    }

    /**
     * 判断是否修改了检查项 目前不能修改的数据
     *
     * @param old_checkItem
     * @param checkItem
     * @return
     */
    private Boolean isCheckItemUpdate(CheckItem old_checkItem, CheckItem checkItem) {
        if (!old_checkItem.getPrice().equals(checkItem.getPrice())) return true;
        if (!old_checkItem.getName().equals(checkItem.getName())) return true;
        if (!old_checkItem.getAge().equals(checkItem.getAge())) return true;
        if (!old_checkItem.getSex().equals(checkItem.getSex())) return true;
        return false;
    }

    /**
     * 判断是否修改了检查项 目前不能修改的数据
     *
     * @param old_checkGroup
     * @param checkGroup
     * @return
     */
    private Boolean isCheckGroupUpdate(CheckGroup old_checkGroup, CheckGroup checkGroup) {
        if (!checkGroup.getSex().equals(old_checkGroup.getSex())) return true;
        if (!checkGroup.getName().equals(old_checkGroup.getName())) return true;
        return false;
    }

    /**
     * 判断是否修改了套餐 目前不能修改的数据
     *
     * @param old_setmeal
     * @param setmeal
     * @return
     */
    private Boolean isSetmealUpdate(Setmeal old_setmeal, Setmeal setmeal) {
        if (!setmeal.getAge().equals(old_setmeal.getAge())) return true;
        if (!setmeal.getName().equals(old_setmeal.getName())) return true;
        if (!setmeal.getPrice().equals(old_setmeal.getPrice())) return true;
        if (!setmeal.getSex().equals(old_setmeal.getSex())) return true;

        return false;
    }
}
