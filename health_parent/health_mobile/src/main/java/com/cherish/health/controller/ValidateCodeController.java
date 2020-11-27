package com.cherish.health.controller;

import com.aliyuncs.exceptions.ClientException;
import com.cherish.health.constant.MessageConstant;
import com.cherish.health.constant.RedisMessageConstant;
import com.cherish.health.entity.Result;
import com.cherish.health.utils.SMSUtils;
import com.cherish.health.utils.ValidateCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Cherish
 * @version 1.8.0_121
 * @date 2020/11/26
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    private static final Logger log = LoggerFactory.getLogger(ValidateCodeController.class);

    @Autowired
    private JedisPool jedisPool;

    /**
     * 发送预约验证码
     *
     * @param telephone
     * @return
     */
    @PostMapping("/send4Order")
    public Result send4Order(String telephone) {
        Jedis jedis = jedisPool.getResource();
        // 随机生成验证码之前,先判断是否已经发送过验证码到该手机上了
        String codeKey = RedisMessageConstant.SENDTYPE_ORDER + "-" + telephone;
        String code = jedis.get(codeKey);
        if (code == null) {
            // 需生成验证码后发送
            Integer code_ = ValidateCodeUtils.generateValidateCode(6);
            try {
                // 验证码发送成功
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code_.toString());
                jedis.setex(codeKey, 5 * 60, code_ + "");
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
                log.error(e.getErrMsg());
                // 验证码发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        jedis.close();
        // 验证码已经发送出去了
        return new Result(false, MessageConstant.SENT_VALIDATECODE);
    }

    /**
     * 发送登录验证码
     *
     * @param telephone
     * @return
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone) {
        Jedis jedis = jedisPool.getResource();
        // 随机生成验证码之前,先判断是否已经发送过验证码到该手机上了
        String codeKey = RedisMessageConstant.SENDTYPE_LOGIN + "-" + telephone;
        String code = jedis.get(codeKey);
        if (code == null) {
            // 需生成验证码后发送
            Integer code_ = ValidateCodeUtils.generateValidateCode(6);
            try {
                // 验证码发送成功
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code_.toString());
                jedis.setex(codeKey, 5 * 60, code_ + "");
                return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
            } catch (ClientException e) {
                log.error(e.getErrMsg());
                // 验证码发送失败
                return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
            }
        }
        // 验证码已经发送出去了
        return new Result(false, MessageConstant.SENT_VALIDATECODE);
    }
}
