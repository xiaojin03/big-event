package com.itheima;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

/**
 * ClassName: JwtTest
 * Package: com.itheima
 * Description: 对Jwt令牌的生成和解析进行测试
 *
 * @Author JinJin
 * @Create 2024/5/16 10:46
 * @Version 1.0
 */
public class JwtTest {

    // 生成Jwt令牌的测试
    @Test
    public void testGen() {
        // 新建载荷，放入数据信息（非敏感）
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "张三");

        // 生成jwt的代码
        String token = JWT.create()
                .withClaim("user", claims)// 添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000*60*60*12))// 添加过期时间
                .sign(Algorithm.HMAC256("jinjin"));// 指定算法，配置密钥

        System.out.println(token);
    }

    // 解析Jwt令牌的测试
    @Test
    public void testParse() {
        // 定义字符串，模拟用户传递过来的token
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3MTU4NzE1ODZ9" +
                ".EFDu2a-DLUZrAIQzH9IYmtj9JRG39TK7wTqqz6clnt0";

        // 根据算法和密钥构建解析器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("jinjin")).build();
        // 验证token，生成一个解析后的JWT对象
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        // 获取JWT对象中的载荷
        Claim user = decodedJWT.getClaim("user");
        System.out.println(user);
    }
}
