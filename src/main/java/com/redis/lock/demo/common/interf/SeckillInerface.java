package com.redis.lock.demo.common.interf;

import com.redis.lock.demo.common.annotation.CacheLock;
import com.redis.lock.demo.common.annotation.LockedObject;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
public interface SeckillInerface {

    /**
     *  cacheLock注解可能产生并发的方法
     *  cacheLock注解可能产生并发的方法
     *  最简单的秒杀方法，参数是用户ID和商品ID。可能有多个线程争抢一个商品，所以商品ID加上LockedObject注解
     * @param userId
     * @param commidityId
     */
    @CacheLock(lockedPrefix="TEST_PREFIX")
    void secKill(Long userId,@LockedObject Long commidityId);
}
