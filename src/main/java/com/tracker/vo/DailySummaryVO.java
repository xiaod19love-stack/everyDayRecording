package com.tracker.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 今日概览 VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "今日概览信息")
public class DailySummaryVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 今日总专注秒数
     */
    @Schema(description = "今日总专注秒数", example = "2700")
    private Integer totalFocusSeconds;

    /**
     * 今日完成任务数
     */
    @Schema(description = "今日完成任务数", example = "3")
    private Integer completedCount;
}
