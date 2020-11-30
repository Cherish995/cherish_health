package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.dao.SetmealDao;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.exception.HealthException;
import com.cherish.health.pojo.Order;
import com.cherish.health.pojo.Setmeal;
import com.cherish.health.service.SetmealService;
import com.cherish.health.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public Integer add(Setmeal setmeal, Integer[] checkgroupIds) {
        // 添加套餐信息到套餐表
        setmealDao.addSetmeal(setmeal);
        // 添加套餐关联检查组信息到关联表
        if (checkgroupIds != null) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealAndCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
        return setmeal.getId();
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
            throw new HealthException(MessageConstant.DELETE_SETMEAL_FAIL);
        }
        // 删除套餐关联检查组信息
        setmealDao.deleteCheckGroupIdsBySetmealId(id);
        // 删除套餐表改套餐信息
        setmealDao.deleteSetmealById(id);
    }

    /**
     * 查询所有的图片名称
     *
     * @return
     */
    @Override
    public List<String> findImgs() {
        return setmealDao.findAllImgs();
    }

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    @Override
    public List<Setmeal> findAll() {

        List<Setmeal> setmealList = setmealDao.findAll();
        if (setmealList != null) {
            // 拼接图片完整路径
            setmealList.forEach(setmeal -> setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg()));
        }
        return setmealList;
    }

    /**
     * 查询套餐详情页信息
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findByDetailId(Integer id) {

        return setmealDao.findByDetailId(id);
    }

    /**
     * 根据套餐id查询对应订单信息
     *
     * @param id
     * @return
     */
    @Override
    public List<Order> findOrderBySetmealId(Integer id) {
        return setmealDao.findOrderBySetmealId(id);
    }

    /**
     * 查询所有套餐名字以及对应的订单数量
     *
     * @return
     */
    @Override
    public List<Map<String, Integer>> findOrdersBySetmeal() {
        return setmealDao.findOrdersBySetmeal();
    }
}
