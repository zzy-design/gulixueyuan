package com.atguigu.msm.controller;

import cn.hutool.core.util.RandomUtil;
import com.atguigu.commonutils.R;
import com.atguigu.msm.service.MsmService;
import com.atguigu.msm.utils.RedisUtil;
import com.atguigu.msm.utils.SendTencentMsmUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/eudMsm")
//@CrossOrigin //跨域
@Slf4j
public class MsmApiController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping(value = "/send/{phone}")
    public R code(@PathVariable String phone) {
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String code = redisUtil.get(phone);
        if (!StringUtils.isEmpty(code)) return R.error().message("请稍后重试");
        code = RandomUtil.randomNumbers(4);
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        Map<String, Object> msmMap = SendTencentMsmUtil.sendTencentMsm(phone, code);
        if ("Ok".equals(msmMap.get("code"))) {
            redisUtil.setEx(phone, code, 5, TimeUnit.MINUTES);
            return R.ok().data("验证码", code);
        } else {
            return R.error().message("发送短信失败");
        }
    }
}
