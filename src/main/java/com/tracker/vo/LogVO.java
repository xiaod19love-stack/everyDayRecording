package com.tracker.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 打卡记录 VO（返回给前端）
 */
@Data
@Schema(description = "打卡记录信息")
public class LogVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @Schema(description = "记录ID", example = "101")
    private Long id;

    /**
     * 关联的习惯ID
     */
    @Schema(description = "关联的习惯ID", example = "1")
    private Long habitId;

    /**
     * 打卡日期
     */
    @Schema(description = "打卡日期", example = "2023-10-01")
    private String date;

    /**
     * 打卡时间
     */
    @Schema(description = "打卡时间", example = "08:30")
    private String timestamp;

    /**
     * 专注时长(秒)
     */
    @Schema(description = "专注时长(秒)", example = "1500")
    private Integer duration;
}
