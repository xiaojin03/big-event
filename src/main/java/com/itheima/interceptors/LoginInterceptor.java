package com.itheima.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.itheima.utils.JwtUtil;
import com.itheima.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * ClassName: LoginInterceptor
 * Package: com.itheima.interceptors
 * Description: 配置检查是否登录的拦截器
 *
 * @Author JinJin
 * @Create 2024/5/16 11:29
 * @Version 1.0
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // token从请求头中的Authorization属性获取
        String token = request.getHeader("Authorization");
        // 验证token
        try {
            // 从redis中获取相同的token
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if (redisToken == null) {
                // token已经失效
                throw new RuntimeException();
            }

            Map<String, Object> claims = JwtUtil.parseToken(token);

            // 将业务数据存储到ThreadLocal中，保障线程安全
            ThreadLocalUtil.set(claims);
            // 解析成功，拦截器放行
            return true;
        } catch (Exception e) {
            // 将响应状态码设置为401
            response.setStatus(401);
            // 拦截器不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 业务方法完成后，清空ThreadLocal中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
