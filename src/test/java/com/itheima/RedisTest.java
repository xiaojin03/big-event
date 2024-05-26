package com.itheima;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * ClassName: RedisTest
 * Package: com.itheima
 * Description:
 *
 * @Author JinJin
 * @Create 2024/5/26 12:29
 * @Version 1.0
 */

@SpringBootTest // 在测试类上添加这个注解，将会在单元测试方法执行前，先初始化Spring容器
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSet() {
        // 向redis中存储一个键值对
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();

        ops.set("username", "jinjin");
        ops.set("id","1",15, TimeUnit.SECONDS);
    }

    @Test
    public void testGet() {
        // 从redis中获取一个键值对
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        System.out.println(ops.get("username"));
    }
}
