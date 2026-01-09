package com.tracker.controller;

import com.tracker.common.Result;
import com.tracker.dto.LogCreateDTO;
import com.tracker.service.LogService;
import com.tracker.vo.IdVO;
import com.tracker.vo.LogVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 打卡记录管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/logs")
@Tag(name = "打卡记录管理", description = "打卡记录管理相关接口")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 获取打卡记录
     * GET /logs
     * 支持可选的 startDate 和 endDate 参数
     */
    @GetMapping
    @Operation(summary = "获取打卡记录",
               description = "用于渲染首页简略统计、日历视图以及详情弹窗。支持按日期范围过滤，不传参数默认返回当月数据")
    public Result<List<LogVO>> getLogs(
            @Parameter(description = "开始日期 (YYYY-MM-DD)", example = "2023-10-01")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate startDate,

            @Parameter(description = "结束日期 (YYYY-MM-DD)", example = "2023-10-31")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate endDate) {

        log.info("接收到获取打卡记录请求，startDate: {}, endDate: {}", startDate, endDate);

        List<LogVO> logs = logService.getLogs(startDate, endDate);

        log.info("返回打卡记录列表，共 {} 条", logs.size());
        return Result.success(logs);
    }

    /**
     * 提交打卡记录
     * POST /logs
     */
    @PostMapping
    @Operation(summary = "提交打卡/完成计时",
               description = "当用户完成一次打卡、计时结束或倒计时结束时调用")
    public Result<IdVO> createLog(@Valid @RequestBody LogCreateDTO dto) {
        log.info("接收到提交打卡记录请求，habitId: {}, date: {}", dto.getHabitId(), dto.getDate());

        // @Valid 注解会自动校验 DTO 中的必填项
        // 如果校验失败，会抛出 MethodArgumentNotValidException
        // 全局异常处理器会捕获并返回 400 错误

        Long id = logService.createLog(dto);

        log.info("提交打卡记录成功，ID: {}", id);
        return Result.success("打卡成功", new IdVO(id));
    }

    /**
     * 根据习惯ID获取打卡记录
     * GET /logs/habit/{habitId}
     */
    @GetMapping("/habit/{habitId}")
    @Operation(summary = "根据习惯ID获取打卡记录",
               description = "获取指定习惯的所有打卡记录")
    public Result<List<LogVO>> getLogsByHabitId(
            @Parameter(description = "习惯ID", example = "1")
            @PathVariable Long habitId) {

        log.info("接收到根据习惯ID获取打卡记录请求，habitId: {}", habitId);

        List<LogVO> logs = logService.getLogsByHabitId(habitId);

        log.info("返回打卡记录列表，共 {} 条", logs.size());
        return Result.success(logs);
    }

    /**
     * 根据ID获取打卡记录详情
     * GET /logs/{id}
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取打卡记录详情",
               description = "根据ID获取打卡记录详细信息")
    public Result<LogVO> getLogById(
            @Parameter(description = "记录ID", example = "101")
            @PathVariable Long id) {

        log.info("接收到获取打卡记录详情请求，ID: {}", id);

        LogVO logVO = logService.getLogById(id);

        log.info("返回打卡记录详情");
        return Result.success(logVO);
    }
}
