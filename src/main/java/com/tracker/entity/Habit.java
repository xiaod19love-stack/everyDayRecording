package com.tracker.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.tracker.enums.ColorKey;
import com.tracker.enums.HabitType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 习惯实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("habits")
public class Habit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 习惯名称
     */
    @TableField("title")
    private String title;

    /**
     * 习惯类型: punch(打卡), stopwatch(计时), countdown(倒计时)
     */
    @TableField("type")
    private HabitType type;

    /**
     * 目标时长(秒)，仅countdown模式有效
     */
    @TableField("duration")
    private Integer duration;

    /**
     * 图标Emoji或URL
     */
    @TableField("icon")
    private String icon;

    /**
     * 副标题/描述
     */
    @TableField("subtitle")
    private String subtitle;

    /**
     * 颜色主题: blue, orange, green, purple
     */
    @TableField("color_key")
    private ColorKey colorKey;

    /**
     * 逻辑删除标识: 0-未删除, 1-已删除
     */
    @TableLogic
    @TableField("deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
