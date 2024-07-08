package com.zhangchuang.partner.common;

import lombok.Getter;

/**
 * 全局错误码
 * <p>
 * Created Zhangchuang on 2024/6/1 下午4:11
 */
@Getter
public enum ErrorCode {

    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功", "服务器正确的处理的了您的请求"),
    /**
     * 操作失败
     */
    ERROR(40000, "操作失败", "服务器无法执行本次请求"),

    /**
     * 用户端参数错误
     */
    PARAMS_ERROR(40001, "请求参数错误", "您的请求参数有误，请仔细检查"),

    /**
     * 服务端未找到指定的参数
     */
    NULL_ERROR(40002, "数据为空", "请请求的数据为空，换一个吧"),

    /**
     * 用户端未登录
     */
    NOT_LOGIN(40100, "未登录", "您未登录，请先登录再操作吧！"),

    /**
     * 无权限
     */
    NO_PERMISSIONS(40101, "无权限", "您没有此权限来执行这个操作"),
    /**
     * 服务器错误
     */
    SERVER_ERROR(50000, "服务器错误", "服务器遇到了一个错误，请联系管理员"),

    /**
     * 请求参数错误
     */
    SERVER_REQUEST_ERROR(50001, "请求体为空", "服务器未收到您的请求体");


    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
