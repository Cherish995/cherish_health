package com.cherish.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.entity.PageResult;
import com.cherish.health.entity.QueryPageBean;
import com.cherish.health.entity.Result;
import com.cherish.health.pojo.Setmeal;
import com.cherish.health.service.SetmealService;
import com.cherish.health.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/23
 * <p>
 * 处理操作套餐请求
 * </p>
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;
    /**
     * 分页查询套餐列表数据
     *
     * @return 套餐列表数据
     */
    @PostMapping("/page")
    public Result page(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> setmealPageResult = setmealService.page(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, setmealPageResult);
    }

    /**
     * 套餐上传图片
     *
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        // 获取图片的后缀名
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 生成唯一的文件名
        String imgName = UUID.randomUUID().toString() + extension;
        try {
            QiNiuUtils.uploadViaByte(multipartFile.getBytes(), imgName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

        Map<String, String> map = new HashMap<>();
        map.put("imgName", imgName);
        map.put("domain", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
    }

    /**
     * 添加套餐数据
     *
     * @param setmeal       套餐列表数据
     * @param checkgroupIds 套餐关联数据
     * @return 结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
        // 获取redis连接对象
        Jedis jedis = jedisPool.getResource();
        // redis中set集合中保存的元素格式为: 套餐id|操作类型|时间戳
        jedis.sadd("setmeal:static:html",setmeal.getId() + "|1|" + System.currentTimeMillis());
        // 还回连接池
        jedis.close();
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    /**
     * 根据id查询套餐信息
     *
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id) {
        Setmeal setmeal = setmealService.findById(id);
        String imgUrl = QiNiuUtils.DOMAIN + setmeal.getImg();
        Map map = new HashMap();
        map.put("setmeal", setmeal);
        map.put("imgUrl", imgUrl);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, map);
    }

    /**
     * 根据套餐id查询对应的检查组id集合
     *
     * @param id
     * @return
     */
    @GetMapping("/findCheckGroupIds")
    public Result findCheckGroupIds(Integer id) {
        List<Integer> ids = setmealService.findCheckGroupIds(id);
        return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL, ids);
    }

    /**
     * 更新套餐信息
     *
     * @param setmeal
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Setmeal setmeal, @RequestParam("checkgroupIds") Integer[] checkgroupIds) {
        setmealService.update(setmeal, checkgroupIds);
        Jedis jedis = jedisPool.getResource();
        jedis.sadd("setmeal:static:html",setmeal.getId() + "|1|" + System.currentTimeMillis());
        jedis.close();
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    /**
     * 根据id删除套餐数据
     *
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public Result delete(Integer id) {
        setmealService.delete(id);
        Jedis jedis = jedisPool.getResource();
        // 操作符0代表删除
        jedis.sadd("setmeal:static:html",id + "|0|" + System.currentTimeMillis());
        jedis.close();
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
}
