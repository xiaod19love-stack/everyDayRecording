package com.tracker.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 打卡记录实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("logs")
public class Log implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联的习惯ID
     */
    @TableField("habit_id")
    private Long habitId;

    /**
     * 打卡日期 (YYYY-MM-DD)
     */
    @TableField("date")
    private LocalDate date;

    /**
     * 打卡具体时间 (HH:mm:ss)
     */
    @TableField("timestamp")
    private LocalTime timestamp;

    /**
     * 专注时长(秒)，punch类型为0
     */
    @TableField("duration")
    private Integer duration;

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
