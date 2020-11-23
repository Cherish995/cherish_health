package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.CheckItem;
import com.cherish.health.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/21
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference
    private CheckItemService checkItemService;

    /**
     * 查询所有检查项信息
     *
     * @return 查询的结果
     */
    @GetMapping("/findAll")
    public Result findAll() {
        List<CheckItem> checkItemList = checkItemService.findAll();
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItemList);
    }

    /**
     * 添加检查项信息
     *
     * @param checkItem 检查项信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {

        checkItemService.add(checkItem);
        return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    /**
     * 分页查询检查项信息
     *
     * @param queryPageBean 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/page")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {

        PageResult result = checkItemService.findByPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, result);

    }

    /**
     * 删除检查项信息
     *
     * @param id 对应检查项id
     * @return 结果
     */
    @GetMapping("/delete")
    public Result deleteById(@RequestParam("id") Integer id) {

        checkItemService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);

    }

    /**
     * 修改检查项信息
     *
     * @param checkItem 更新的检查项信息
     * @return 结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody CheckItem checkItem) {
        checkItemService.update(checkItem);
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
}
