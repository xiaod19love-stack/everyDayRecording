package com.tracker.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 颜色主题枚举
 */
@Getter
@AllArgsConstructor
public enum ColorKey {

    /**
     * 浅蓝主题
     */
    BLUE("blue", "浅蓝主题"),

    /**
     * 浅橙主题
     */
    ORANGE("orange", "浅橙主题"),

    /**
     * 浅绿主题
     */
    GREEN("green", "浅绿主题"),

    /**
     * 浅紫主题
     */
    PURPLE("purple", "浅紫主题");

    /**
     * 数据库存储值
     */
    @EnumValue
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String description;

    /**
     * 根据 value 获取枚举
     */
    public static ColorKey fromValue(String value) {
        for (ColorKey colorKey : ColorKey.values()) {
            if (colorKey.getValue().equals(value)) {
                return colorKey;
            }
        }
        throw new IllegalArgumentException("无效的颜色主题: " + value);
    }
}
