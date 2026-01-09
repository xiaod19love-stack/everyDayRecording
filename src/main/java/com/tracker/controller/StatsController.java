package com.tracker.controller;

import com.tracker.common.Result;
import com.tracker.service.StatsService;
import com.tracker.vo.DailySummaryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 统计控制器
 */
@Slf4j
@RestController
@RequestMapping("/stats")
@Tag(name = "统计模块", description = "数据统计相关接口")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    /**
     * 获取今日概览
     * GET /stats/daily-summary
     */
    @GetMapping("/daily-summary")
    @Operation(summary = "获取今日概览",
               description = "用于首页顶部卡片展示今日专注和今日打卡数")
    public Result<DailySummaryVO> getDailySummary(
            @Parameter(description = "日期 (YYYY-MM-DD)，不传则默认今天", example = "2023-10-28")
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date) {

        log.info("接收到获取每日概览请求，日期: {}", date != null ? date : "今天");

        DailySummaryVO summary = statsService.getDailySummary(date);

        log.info("返回每日概览: 总专注{}秒({}分钟), 完成任务{}个",
                summary.getTotalFocusSeconds(),
                summary.getTotalFocusSeconds() / 60,
                summary.getCompletedCount());

        return Result.success(summary);
    }
}
