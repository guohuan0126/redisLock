package com.redis.lock.demo.controller;

import com.redis.lock.demo.common.interf.SeckillInerface;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
@RestController
public class SecKillController {

    @Resource
    SeckillInerface seckillInerface;
    @GetMapping("api/v1/kill")
    public String kill(Long userId, Long productId){
        seckillInerface.secKill(userId, productId);
        return "ok";
    }
}
