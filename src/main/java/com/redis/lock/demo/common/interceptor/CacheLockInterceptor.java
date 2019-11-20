package com.redis.lock.demo.common.interceptor;

import com.redis.lock.demo.common.annotation.CacheLock;
import com.redis.lock.demo.common.annotation.LockedComplexObject;
import com.redis.lock.demo.common.annotation.LockedObject;
import com.redis.lock.demo.common.exception.CacheLockException;
import com.redis.lock.demo.common.lock.RedisLock;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
@Slf4j
public class CacheLockInterceptor implements InvocationHandler {

    public static int ERROR_COUNT = 0;
    private Object proxied;

    public CacheLockInterceptor(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        CacheLock cacheLock = method.getAnnotation(CacheLock.class);
        if (null == cacheLock){
            log.info("no cacheLock annotation");
            return method.invoke(proxied, args);
        }
        //获取方法中参数的注解
        Annotation[][] annotations = method.getParameterAnnotations();
        Object lockedObject = getLockedObject(annotations, args);
        String objectValue = lockedObject.toString();

        RedisLock redisLock = new RedisLock(cacheLock.lockedPrefix(), objectValue);
        boolean result = redisLock.lock(cacheLock.timeOut(), cacheLock.expireTime());
        if (!result){
            ERROR_COUNT += 1;
            throw new CacheLockException("get lock fail");
        }
        try{
            //加锁成功，执行方法
            return method.invoke(proxied, args);
        }finally{
            redisLock.unlock();//释放锁
        }
    }

    private Object getLockedObject(Annotation[][] annotations, Object[] args){
        if (null==args || args.length == 0){
            throw new CacheLockException("方法参数为空，没有被锁定的对象");
        }
        if (null == annotations || annotations.length == 0){
            throw new CacheLockException("没有被注解的参数");
        }
        int index = -1;
        for (int i = 0; i < annotations.length; i++) {
            for (int j = 0; j < annotations[i].length; j++) {
                Annotation current = annotations[i][j];
                if (current instanceof LockedComplexObject){
                    index = 1;
                    try {
                        return args[i].getClass().getField(((LockedComplexObject)current).field());
                    } catch (NoSuchFieldException e) {
                        throw new CacheLockException("注解对象中没有该属性" + ((LockedComplexObject)current).field());
                    }
                }
                if (current instanceof LockedObject){
                    index = 1;
                    break;
                }
            }

            if (index != -1){
                break;
            }
        }
        if (index == -1){
            throw new CacheLockException("请指定被锁定参数");
        }
        return args[index];
    }
}
