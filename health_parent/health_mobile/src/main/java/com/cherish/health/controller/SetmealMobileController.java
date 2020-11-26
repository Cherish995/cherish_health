package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.Setmeal;
import com.cherish.health.service.SetmealService;
import com.cherish.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/25
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealMobileController {

    @Reference
    private SetmealService setmealService;

    /**
     * 查询所有套餐信息
     *
     * @return
     */
    @GetMapping("/getSetmeal")
    public Result getSetmeal() {
        List<Setmeal> setmealList = setmealService.findAll();
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealList);
    }

    @PostMapping("/findById")
    public Result findByDetailId(Integer id) {
        Setmeal setmeal = setmealService.findByDetailId(id);
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
    }
}
