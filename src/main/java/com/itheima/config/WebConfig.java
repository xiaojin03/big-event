package com.itheima.config;

import com.itheima.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ClassName: WebConfig
 * Package: com.itheima.config
 * Description: 拦截器的配置类，注册到系统中
 *
 * @Author JinJin
 * @Create 2024/5/16 12:14
 * @Version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    // 注入自定义的登录拦截器
    @Autowired
    private LoginInterceptor loginInterceptor;

    // 注册自定义的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录接口和注册接口不拦截
        registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
    }
}
