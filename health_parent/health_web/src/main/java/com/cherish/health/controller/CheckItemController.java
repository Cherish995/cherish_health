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
        try {
            List<CheckItem> checkItemList = checkItemService.findAll();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkItemList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    /**
     * 添加检查项信息
     *
     * @param checkItem 检查项信息
     */
    @PostMapping("/add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    /**
     * 分页查询检查项信息
     *
     * @param queryPageBean 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/page")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult result = checkItemService.findByPage(queryPageBean);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, result);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @GetMapping("/delete")
    public Result deleteById(@RequestParam("id") Integer id) {
        try {
            checkItemService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }
}
