package com.zhangchuang.partner.exception;

import com.zhangchuang.partner.common.ErrorCode;

/**
 * 业务异常
 * <p>
 * Created Zhangchuang on 2024/6/1 下午5:55
 */
public class BusinessException extends RuntimeException {

    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
