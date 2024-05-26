package com.itheima.anno;

import com.itheima.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * ClassName: State
 * Package: com.itheima.anno
 * Description: 自定义注解State
 *
 * @Author JinJin
 * @Create 2024/5/16 18:07
 * @Version 1.0
 */
@Documented// 元注解
@Target(ElementType.FIELD)// 元注解，决定注解在什么类型上使用
@Retention(RetentionPolicy.RUNTIME)// 元注解，决定注解在什么阶段生效
@Constraint(validatedBy = { StateValidation.class })// 指定提供校验规则的类
public @interface State {
    // 提供校验失败后的提示信息
    String message() default "state参数的值只能是“已发布”或“草稿”";
    // 指定分组
    Class<?>[] groups() default { };
    // 负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default { };
}
