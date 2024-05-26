package com.itheima.validation;

import com.itheima.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ClassName: StateValidation
 * Package: com.itheima.validation
 * Description: 自定义验证数据的类StateValidation，实现ConstraintValidator接口，泛型要求：<给哪个注解提供验证规则，校验的数据类型>
 *
 * @Author JinJin
 * @Create 2024/5/16 18:14
 * @Version 1.0
 */
public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param value 将来要检验的数据
     * @param context
     * @return 校验通过，返回true；否则返回false
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 提供校验规则
        if (value == null) {
            return false;
        }
        if (value.equals("已发布") || value.equals("草稿")) {
            return true;
        }
        return false;
    }
}
