package com.tracker.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建打卡记录 DTO
 */
@Data
@Schema(description = "创建打卡记录请求对象")
public class LogCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联的习惯ID
     */
    @NotNull(message = "习惯ID不能为空")
    @Schema(description = "关联的习惯ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long habitId;

    /**
     * 打卡日期 (YYYY-MM-DD)
     */
    @NotBlank(message = "打卡日期不能为空")
    @Schema(description = "打卡日期 (YYYY-MM-DD)", example = "2023-10-28", requiredMode = Schema.RequiredMode.REQUIRED)
    private String date;

    /**
     * 打卡具体时间 (HH:mm)
     */
    @NotBlank(message = "打卡时间不能为空")
    @Schema(description = "打卡具体时间 (HH:mm)", example = "22:00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String timestamp;

    /**
     * 专注时长(秒)
     */
    @NotNull(message = "专注时长不能为空")
    @Schema(description = "专注时长(秒)，punch类型传0", example = "3000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer duration;
}
