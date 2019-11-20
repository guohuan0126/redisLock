package com.redis.lock.demo;

import com.redis.lock.demo.entity.User;
import com.redis.lock.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class DemoApplicationTests {

    @Resource
    UserMapper userMapper;

    @Test
    void contextLoads() {

        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setName("test"+i);
            user.setBalance(1000);
            user.setPassword("test00000"+i);
            userMapper.insertSelective(user);
        }
    }

}
