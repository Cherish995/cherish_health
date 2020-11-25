package com.cherish.health.aspect;

import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.CheckGroup;
import com.cherish.health.pojo.CheckItem;
import com.cherish.health.pojo.Setmeal;
import com.cherish.health.service.CheckGroupService;
import com.cherish.health.service.CheckItemService;
import com.cherish.health.service.SetmealService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(HealthAspect.class);

    @Autowired
    private CheckItemService checkItemService;
    @Autowired
    private CheckGroupService checkGroupService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 增强删除检查项功能
     *
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.cherish.health.service.impl.CheckItemServiceImpl.deleteById(..))")
    public Object boostDeleteCheckItem(ProceedingJoinPoint joinPoint) {
        // 拿到参数数组
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) throw new RuntimeException("严重错误!!!");
        // 拿到id
        Integer id = (Integer) args[0];
        if (id == null) throw new RuntimeException("严重错误!!!");
        // 判断是否被订单使用
        if (isCheckItemUsed(id)) throw new HealthException("已被订单使用,删除失败");
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(),throwable);
            throw new HealthException(throwable.getMessage());
        }
    }

    /**
     * 增强检查组删除功能
     *
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.cherish.health.service.impl.CheckGroupServiceImpl.deleteById(..))")
    public Object boostDeleteCheckGroup(ProceedingJoinPoint joinPoint) {
        try {
            // 拿到参数数组
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) throw new RuntimeException("严重错误!!!");
            // 拿到id
            Integer id = (Integer) args[0];
            if (id == null) throw new RuntimeException("严重错误!!!");
            // 判断是否被订单使用
            if (isCheckGroupUsed(id)) {
                throw new HealthException("已被订单使用,删除失败");
            }
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(),throwable);
            throw new HealthException(throwable.getMessage());
        }
    }

    /**
     * 增强套餐删除功能
     *
     * @param joinPoint
     * @return
     */
    @Around("execution(* com.cherish.health.service.impl.SetmealServiceImpl.delete(..)))")
    public Object boostDeleteSetmeal(ProceedingJoinPoint joinPoint) {
        try {
            // 拿到参数数组
            Object[] args = joinPoint.getArgs();
            if (args == null || args.length == 0) throw new RuntimeException("严重错误!!!");
            // 拿到id
            Integer id = (Integer) args[0];
            if (id == null) throw new RuntimeException("严重错误!!!");
            // 判断是否被订单使用
            if (isSetmealUsed(id)) {
                throw new HealthException("已被订单使用,删除失败");
            }

            return joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(),throwable);
            throw new HealthException(throwable.getMessage());
        }
    }

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
            // 获取待更新检查项的信息 参数
            CheckItem checkItem = (CheckItem) args[0];
            Integer id = checkItem.getId();
            if (id == null) throw new RuntimeException("严重错误!!!");
            // 判断检查项是否已被订单使用
            if (isCheckItemUsed(id)) {
                // 查询到数据库的信息
                CheckItem old_checkItem = checkItemService.findById(id);
                // 查询不到 抛出异常
                if (old_checkItem == null) throw new RuntimeException("有大问题!!!");
                // 并未此次更新并未修改任何修改内容 不执行目标方法(切入点)
                if (old_checkItem.equals(checkItem)) return null;
                else {
                    // 修改了不能暂时修改的数据 抛异常给你
                    if (isCheckItemUpdate(old_checkItem, checkItem)) throw new HealthException("已有订单,不能更改");
                    // 可以修改 执行目标方法
                    return joinPoint.proceed();
                }

            } else {
                // 可以修改 执行目标方法
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            // 将异常自定义处理
            log.error(throwable.getMessage(),throwable);
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
                /**
                 * 【检查组已被订单使用】
                 */
                // 得到所有旧的信息
                CheckGroup old_checkGroup = checkGroupService.findById(id);
                List<Integer> old_checkitemIds = checkGroupService.findCheckitemIds(id);
                // 判断检查项是否要被修改
                if (checkitemIds.equals(old_checkitemIds)) {
                    // 啥也不改 你想干啥 就不给你更新的机会了
                    if (checkGroup.equals(old_checkGroup)) return null;
                } else {
                    // 检查项是不能修改的
                    throw new HealthException("已有订单使用,修改失败");
                }
                // 判断更新的内容是否是不能被修改的
                if (isCheckGroupUpdate(old_checkGroup, checkGroup)) {
                    // 不能修改顾客至上
                    throw new HealthException("已有订单使用,修改失败");
                }
                // 你更新吧
                return joinPoint.proceed();

            } else {
                // 你更新吧
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            // 自己处理异常
            log.error(throwable.getMessage(),throwable);
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
            // 拿到检查组id数组(可能为null 无需判断)
            Integer[] ids = (Integer[]) args[1];
            List<Integer> checkgroupIds = Arrays.asList(ids);

            // 判断套餐是否已被订单使用
            Integer id = setmeal.getId();
            if (isSetmealUsed(id)) {
                /**
                 * 【套餐已被订单使用】
                 */
                // 得到所有旧的信息
                Setmeal old_setmeal = setmealService.findById(id);
                List<Integer> old_checkGroupIds = setmealService.findCheckGroupIds(id);
                // 判断检查组是否要被修改
                if (checkgroupIds.equals(old_checkGroupIds)) {
                    // 啥也不改 你想干啥 就不给你更新的机会了
                    if (setmeal.equals(old_setmeal)) return null;
                } else {
                    // 检查组是不能修改的
                    throw new HealthException("已有订单使用,修改失败");
                }
                // 判断更新的内容是否是不能被修改的
                if (isSetmealUpdate(old_setmeal, setmeal)) {
                    // 不能修改顾客至上
                    throw new HealthException("已有订单使用,修改失败");
                }
                // 你更新吧
                return joinPoint.proceed();
            } else {
                // 你更新吧
                return joinPoint.proceed();
            }
        } catch (Throwable throwable) {
            // 自己处理异常
            log.error(throwable.getMessage(),throwable);
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

        List<Integer> groupIds = findCheckGroupIdByCheckItemId(id);
        if (groupIds == null || groupIds.size() == 0) return false;
        for (Integer groupId : groupIds) {
            List<Integer> setmealIds = findSetmealIdByCheckGroupId(groupId);
            if (setmealIds == null || setmealIds.size() == 0) return false;
            for (Integer setmealId : setmealIds) {
                List<Integer> orderIds = findOrderIdBySetmealId(setmealId);
                if (orderIds == null || orderIds.size() == 0) return false;
            }
        }
        return true;
    }

    /**
     * 判断检查组是否已被订单使用
     *
     * @param id
     * @return
     */
    private Boolean isCheckGroupUsed(Integer id) {
        List<Integer> setmealIds = findSetmealIdByCheckGroupId(id);
        if (setmealIds == null || setmealIds.size() == 0) return false;
        for (Integer setmealId : setmealIds) {
            List<Integer> orderIds = findOrderIdBySetmealId(setmealId);
            if (orderIds == null || orderIds.size() == 0) return false;
        }
        return true;
    }

    /**
     * 判断套餐是否已被订单使用
     */
    private Boolean isSetmealUsed(Integer id) {
        List<Integer> orderIds = findOrderIdBySetmealId(id);
        if (orderIds == null || orderIds.size() == 0) return false;
        return true;
    }

    /**
     * 根据检查项id查询对应的检查组id
     *
     * @param id
     * @return
     */
    private List<Integer> findCheckGroupIdByCheckItemId(Integer id) {
        return checkItemService.findCheckGroupId(id);
    }

    /**
     * 根据检查组id查询套餐id
     *
     * @param id
     * @return
     */
    private List<Integer> findSetmealIdByCheckGroupId(Integer id) {
        return checkGroupService.findSetmealId(id);
    }

    /**
     * 根据套餐id查询对应的订单ID
     *
     * @param id
     * @return
     */
    private List<Integer> findOrderIdBySetmealId(Integer id) {
        return setmealService.findOrderId(id);
    }

    /**
     * 判断是否要修改检查项表中 目前不能修改的数据
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
     * 判断是否要修改检查项表中 目前不能修改的数据
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
     * 判断是否要修改套餐表中 目前不能修改的数据
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
