package com.edu.exception.custom;

public class RedissonLockException extends RuntimeException {

    public RedissonLockException() {
    }

    public RedissonLockException(String message) {
        super(message);
    }
}
