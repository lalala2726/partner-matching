package com.zhangchuang.partner.common;

/**
 * 范湖工具类
 * <p>
 * Created Zhangchuang on 2024/6/1 下午3:28
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, data, "操作成功");
    }

    /**
     * 错误
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 错误
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }


    /**
     * 错误
     *
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse<>(errorCode.getCode(), errorCode.getMessage(), description);
    }

}
