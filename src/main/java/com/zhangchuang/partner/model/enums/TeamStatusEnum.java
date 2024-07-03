package com.zhangchuang.partner.model.enums;

/**
 * 队伍状态枚举
 *
 * @author chuang
 */
public enum TeamStatusEnum {

    /**
     * 公开
     */
    PUBLIC(0, "公开"),

    /**
     * 私密
     */
    PRIVATE(1, "私密"),

    /**
     * 加密
     */
    SECURE(2, "加密");


    /**
     * 队伍状态值
     */
    private int value;

    /**
     * 队伍状态描述
     */
    private String text;


    public static TeamStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        TeamStatusEnum[] values = TeamStatusEnum.values();
        for (TeamStatusEnum teamStatusEnum : values) {
            if (teamStatusEnum.getValue() == value) {
                return teamStatusEnum;
            }
        }
        return null;
    }

    TeamStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
