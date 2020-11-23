package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.dao.SetmealDao;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.Setmeal;
import com.cherish.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/23
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
     * 分页查询套餐列表数据
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Setmeal> page(QueryPageBean queryPageBean) {
        Long total = setmealDao.findCount(queryPageBean);
        List<Setmeal> setmealList = null;
        if (total > 0) {
            setmealList = setmealDao.page(queryPageBean);
        }
        return new PageResult<>(total, setmealList);
    }

    /**
     * 添加套餐数据
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Transactional
    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐信息到套餐表
        setmealDao.addSetmeal(setmeal);
        // 添加套餐关联检查组信息到关联表
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealAndCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 根据id查询套餐数据
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    /**
     * 根据套餐id查询对应检查组id集合
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckGroupIds(Integer id) {
        return setmealDao.findCheckGroupIds(id);
    }

    /**
     * 修改套餐数据
     *
     * @param setmeal
     * @param checkgroupIds
     */
    @Transactional
    @Override
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 更新套餐表信息
        setmealDao.update(setmeal);
        // 删除套餐与检查组关联的旧信息
        setmealDao.deleteCheckGroupIdsBySetmealId(setmeal.getId());
        // 添加套餐与检查组关联新信息
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealAndCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
    }

    /**
     * 根据id删除套餐数据
     *
     * @param id
     */
    @Override
    public void delete(Integer id) {
        // 判断改套餐是否有对应的订单
        Long total = setmealDao.findOrders(id);
        if (total > 0) {
            throw new HealthException("【套餐已有订单】删除失败");
        }
        // 删除套餐关联检查组信息
        setmealDao.deleteCheckGroupIdsBySetmealId(id);
        // 删除套餐表改套餐信息
        setmealDao.deleteSetmealById(id);
    }
}