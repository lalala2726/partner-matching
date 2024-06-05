package com.zhangchuang.partner.exception;

import com.zhangchuang.partner.common.BaseResponse;
import com.zhangchuang.partner.common.ErrorCode;
import com.zhangchuang.partner.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常捕获处理
 * <p>
 * Created Zhangchuang on 2024/6/1 下午6:24
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException exception) {
        log.error("businessException{}", exception.getMessage(), exception);
        return ResultUtils.error(exception.getCode(), exception.getMessage(), exception.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException exception) {
        log.error("runtimeException", exception);
        return ResultUtils.error(ErrorCode.SERVER_ERROR, exception.getMessage(), "");
    }
}
