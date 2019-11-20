package com.redis.lock.demo.common.exception;

/**
 * @program: demo
 * @description:
 * @author: Irving
 * @create: 2019-11-19
 **/
public class CacheLockException extends RuntimeException {

    private String message;

    public CacheLockException(String message) {
        super(message);
        this.message = message;
    }

    /**
     * for better performance
     *
     * @return Throwable
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
