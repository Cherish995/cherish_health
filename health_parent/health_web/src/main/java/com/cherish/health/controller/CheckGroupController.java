package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.CheckGroup;
import com.cherish.health.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/22
 */
@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    /**
     * 添加检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") Integer[] checkitemIds) {

        checkGroupService.add(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);

    }

    /**
     * 根据id查询检查组信息
     *
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {

        CheckGroup checkGroup = checkGroupService.findById(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);

    }

    /**
     * 根据检查组id查询对应检查项id集
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckitemIds")
    public Result findCheckitemIds(Integer id) {

        List<Integer> list = checkGroupService.findCheckitemIds(id);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, list);
    }

    /**
     * 分页查询检查组数据
     *
     * @param queryPageBean
     * @return
     */
    @PostMapping("/page")
    public Result page(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkGroupService.page(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);

    }

    /**
     * 修改更新检查组信息
     *
     * @param checkGroup
     * @param checkitemIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckGroup checkGroup, @RequestParam("checkitemIds") Integer[] checkitemIds) {

        checkGroupService.update(checkGroup, checkitemIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    /**
     * 根据id删除检查组信息
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        checkGroupService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }

    /**
     * 查询检查组列表集合
     *
     * @return
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckGroup> checkGroupList = checkGroupService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupList);
    }
}
