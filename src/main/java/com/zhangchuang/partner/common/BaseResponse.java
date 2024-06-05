package com.zhangchuang.partner.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * <p>
 * Created Zhangchuang on 2024/6/1 下午3:22
 */
@Data
public class BaseResponse<T> implements Serializable {

    private Integer code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(Integer code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(Integer code, T data, String message) {
        this(code, data, message, "");

    }

    public BaseResponse(Integer code, T data) {
        this(code, data, "", "");

    }


    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
