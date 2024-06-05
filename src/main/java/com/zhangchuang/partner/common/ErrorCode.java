package com.zhangchuang.partner.common;

import lombok.Getter;

/**
 * 全局错误码
 * <p>
 * Created Zhangchuang on 2024/6/1 下午4:11
 */
@Getter
public enum ErrorCode {

    /* 操作成功 */
    SUCCESS(200, "操作成功", ""),
    ERROR(40000, "操作失败", ""),

    /* 用户端参数错误 */
    PARAMS_ERROR(40001, "请求参数错误", ""),

    /* 服务端未找到指定的参数 */
    NULL_ERROR(40002, "数据为空", ""),

    /* 用户端未登录 */
    NOT_LOGIN(40100, "未登录", ""),

    /* 用户端没有权限 */
    NO_PERMISSIONS(40101, "无权限", ""),
    /* 服务器错误 */
    SERVER_ERROR(50000, "服务器错误", "");


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
