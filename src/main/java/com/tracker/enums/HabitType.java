package com.tracker.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 习惯类型枚举
 */
@Getter
@AllArgsConstructor
public enum HabitType {

    /**
     * 一键打卡
     */
    PUNCH("punch", "一键打卡"),

    /**
     * 正向计时
     */
    STOPWATCH("stopwatch", "正向计时"),

    /**
     * 倒计时
     */
    COUNTDOWN("countdown", "倒计时");

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
    public static HabitType fromValue(String value) {
        for (HabitType type : HabitType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("无效的习惯类型: " + value);
    }
}
