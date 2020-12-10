package com.cherish.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.dao.CheckGroupDao;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.pojo.CheckGroup;
import com.cherish.health.pojo.Order;
import com.cherish.health.service.CheckGroupService;
import com.cherish.health.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/22
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
     * 添加检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 添加检查组
        checkGroupDao.addCheckGroup(checkGroup);
        // 获取检查组的id
        Integer checkGroupId = checkGroup.getId();
        // 遍历检查项id, 添加检查组与检查项的关系
        if (null != checkitemIds) {
            // 有钩选
            for (Integer checkitemId : checkitemIds) {
                //添加检查组与检查项的关系
                checkGroupDao.addCGCI(checkGroupId, checkitemId);
            }
        }
    }

    /**
     * 根据id查询检查组信息
     *
     * @param id
     * @return
     */
    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    /**
     * 根据检查组id查询对应的检查项id集合
     *
     * @param id
     * @return
     */
    @Override
    public List<Integer> findCheckitemIds(Integer id) {
        return checkGroupDao.findCheckitemIds(id);
    }

    /**
     * 分页查询检查组数据
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult page(QueryPageBean queryPageBean) {
        /*String queryString = queryPageBean.getQueryString();
        if (queryString != null && Integer.parseInt(queryString) == 0) {
            queryPageBean.setQueryString(queryString + "");
        }*/
        // 查询总记录数
        Long total = checkGroupDao.findCount(queryPageBean);
        List<CheckGroup> rows = null;
        if (total > 0) {
            // 查询所有记录
            rows = checkGroupDao.findList(queryPageBean);
        }
        return new PageResult(total, rows);
    }

    /**
     * 修改、更新检查组数据
     *
     * @param checkGroup
     * @param checkitemIds
     */
    @Transactional
    @Override
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        // 清空检查组与检查组的关联
        checkGroupDao.deleteAllById(checkGroup.getId());
        // 修改检查组信息
        checkGroupDao.updateCheckGroup(checkGroup);
        // 修改检查项与检查组关联信息
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.updateCheckGroupAndCheckItem(checkGroup.getId(), checkitemId);
            }
        }
    }

    /**
     * 根据id删除检查组数据
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteById(Integer id) {
        // 判断该id对应检查组对应的套餐是否存在 存在不能删除 不存在随便删
        Long total = checkGroupDao.findSetmeal(id);
        if (total > 0) {
            // 亲 不能删除哦
            throw new MyException(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        // 删除啦
        checkGroupDao.deleteCheckGroupAndCheckItemById(id);
        checkGroupDao.deleteById(id);
    }

    /**
     * 查询检查组列表集合
     *
     * @return
     */
    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }


    /**
     * 根据检查组id查询对应订单信息
     * @param id
     * @return
     */
    @Override
    public List<Order> findOrderByCheckGroupId(Integer id) {
        return checkGroupDao.findOrderByCheckGroupId(id);
    }
}
