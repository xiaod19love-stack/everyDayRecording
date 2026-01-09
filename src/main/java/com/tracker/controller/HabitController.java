package com.tracker.controller;

import com.tracker.common.Result;
import com.tracker.dto.HabitCreateDTO;
import com.tracker.service.HabitService;
import com.tracker.vo.HabitVO;
import com.tracker.vo.IdVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 习惯管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/habits")
@Tag(name = "习惯管理", description = "习惯管理相关接口")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    /**
     * 获取习惯列表
     * GET /habits
     */
    @GetMapping
    @Operation(summary = "获取习惯列表", description = "用于应用首页初始化时加载所有习惯")
    public Result<List<HabitVO>> getHabits() {
        log.info("接收到获取习惯列表请求");

        List<HabitVO> habits = habitService.getHabits();

        log.info("返回习惯列表，共 {} 条", habits.size());
        return Result.success(habits);
    }

    /**
     * 创建新习惯
     * POST /habits
     */
    @PostMapping
    @Operation(summary = "创建新习惯", description = "对应前端 AddHabitModal 组件的保存操作")
    public Result<IdVO> createHabit(@Valid @RequestBody HabitCreateDTO dto) {
        log.info("接收到创建习惯请求: {}", dto.getTitle());

        // @Valid 注解会自动校验 DTO 中的必填项
        // 如果校验失败，会抛出 MethodArgumentNotValidException
        // 全局异常处理器会捕获并返回 400 错误

        Long id = habitService.createHabit(dto);

        log.info("创建习惯成功，ID: {}", id);
        return Result.success("创建成功", new IdVO(id));
    }

    /**
     * 根据ID获取习惯详情
     * GET /habits/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取习惯详情", description = "根据ID获取习惯详细信息")
    public Result<HabitVO> getHabitById(@PathVariable Long id) {
        log.info("接收到获取习惯详情请求，ID: {}", id);

        HabitVO habit = habitService.getHabitById(id);

        log.info("返回习惯详情: {}", habit.getTitle());
        return Result.success(habit);
    }
}
