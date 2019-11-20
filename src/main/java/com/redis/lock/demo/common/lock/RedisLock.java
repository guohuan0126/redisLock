package com.redis.lock.demo.common.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Random;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
@Slf4j
public class RedisLock {

    private static final Random RANDOM = new Random();
    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    private ValueOperations<String, String> stringValueOperations;
    private String key;
    private String LOCKED = "lock";
    private boolean lock;

    @PostConstruct
    private void init() {
        stringValueOperations = stringRedisTemplate.opsForValue();
    }

    public RedisLock(String key, String prifix) {
        this.key = prifix +"-"+ key;
    }

    private static final long MILLI_NANO_TIME = 0;

    public boolean lock(long timeout, int expire){
        long nanoTime = System.nanoTime();
        timeout *= MILLI_NANO_TIME;
        try {


            while (System.nanoTime() - nanoTime < timeout) {
                if (this.stringValueOperations.setIfAbsent(this.key, LOCKED, Duration.ofSeconds(expire))) {
                    this.lock = true;
                    return true;
                } else {
                    log.info("出现锁等待 key {}", key);
                    Thread.sleep(3, RANDOM.nextInt(30));
                }
            }
        }catch (Exception e){
            throw new RuntimeException("locking error",e);
        }
        return false;
    }

    public void unlock(){
        try {
            if (this.lock){
                stringValueOperations.decrement(key);
            }
        } catch (Throwable e){

        }
    }
}
