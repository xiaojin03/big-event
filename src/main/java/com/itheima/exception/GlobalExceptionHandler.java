package com.itheima.exception;

import com.itheima.pojo.Result;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: GlobalExceptionHandler
 * Package: com.itheima.exception
 * Description: 全局异常处理器，处理参数校验失败的异常，返回符合响应接口的信息
 *
 * @Author JinJin
 * @Create 2024/5/13 22:25
 * @Version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 要处理的异常类型：Exception.class，即全部异常
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "操作失败");
    }
}
