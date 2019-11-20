package com.redis.lock.demo.common.annotation;

import java.lang.annotation.*;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheLock {
    /**
     *
     * redis 锁前缀
     */
    String lockedPrefix() default "";

    /**
     *
     * 锁 轮询时间
     */
    long timeOut() default 2000;

    /**
     *
     * 过期时间
     */
    int expireTime() default 1000;
}
