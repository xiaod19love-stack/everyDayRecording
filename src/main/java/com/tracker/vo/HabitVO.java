package com.tracker.vo;

import com.tracker.enums.ColorKey;
import com.tracker.enums.HabitType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 习惯 VO（返回给前端）
 */
@Data
@Schema(description = "习惯信息")
public class HabitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 习惯ID
     */
    @Schema(description = "习惯ID", example = "1")
    private Long id;

    /**
     * 习惯名称
     */
    @Schema(description = "习惯名称", example = "晨间阅读")
    private String title;

    /**
     * 习惯类型
     */
    @Schema(description = "习惯类型", example = "countdown")
    private HabitType type;

    /**
     * 目标时长(秒)
     */
    @Schema(description = "目标时长(秒)", example = "1500")
    private Integer duration;

    /**
     * 图标
     */
    @Schema(description = "图标", example = "📖")
    private String icon;

    /**
     * 副标题
     */
    @Schema(description = "副标题", example = "每天进步一点点")
    private String subtitle;

    /**
     * 颜色主题
     */
    @Schema(description = "颜色主题", example = "blue")
    private ColorKey colorKey;
}
