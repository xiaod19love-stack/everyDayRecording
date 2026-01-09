package com.tracker.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ID 返回 VO（用于创建操作）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "ID返回对象")
public class IdVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 实体ID
     */
    @Schema(description = "实体ID", example = "5")
    private Long id;
}
