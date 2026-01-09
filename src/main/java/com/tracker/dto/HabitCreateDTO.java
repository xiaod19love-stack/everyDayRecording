package com.tracker.dto;

import com.tracker.enums.ColorKey;
import com.tracker.enums.HabitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 创建习惯 DTO
 */
@Data
@Schema(description = "创建习惯请求对象")
public class HabitCreateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 习惯名称
     */
    @NotBlank(message = "习惯名称不能为空")
    @Schema(description = "习惯名称", example = "晨间阅读", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    /**
     * 习惯类型
     */
    @NotNull(message = "习惯类型不能为空")
    @Schema(description = "习惯类型: punch(打卡), stopwatch(计时), countdown(倒计时)",
            example = "countdown", requiredMode = Schema.RequiredMode.REQUIRED)
    private HabitType type;

    /**
     * 目标时长(秒)
     */
    @Schema(description = "目标时长(秒)，仅countdown模式有效，其他传0", example = "1500")
    private Integer duration;

    /**
     * 图标
     */
    @NotBlank(message = "图标不能为空")
    @Schema(description = "图标Emoji或URL", example = "📖", requiredMode = Schema.RequiredMode.REQUIRED)
    private String icon;

    /**
     * 副标题
     */
    @Schema(description = "副标题/描述", example = "每天进步一点点")
    private String subtitle;

    /**
     * 颜色主题
     */
    @NotNull(message = "颜色主题不能为空")
    @Schema(description = "颜色主题: blue, orange, green, purple",
            example = "blue", requiredMode = Schema.RequiredMode.REQUIRED)
    private ColorKey colorKey;
}
