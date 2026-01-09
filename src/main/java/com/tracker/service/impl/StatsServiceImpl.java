package com.tracker.service.impl;

import com.tracker.mapper.LogMapper;
import com.tracker.service.StatsService;
import com.tracker.vo.DailySummaryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 统计服务实现类
 */
@Slf4j
@Service
public class StatsServiceImpl implements StatsService {

    private final LogMapper logMapper;

    public StatsServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    /**
     * 获取每日概览统计
     * 包含今日总专注时长（秒）和今日完成任务数
     */
    @Override
    public DailySummaryVO getDailySummary(LocalDate date) {
        // 如果没有传入日期，默认使用今天
        if (date == null) {
            date = LocalDate.now();
        }

        log.info("获取每日概览统计，日期: {}", date);

        // 1. 查询指定日期的总专注时长（秒）
        Integer totalFocusSeconds = logMapper.sumDurationByDate(date);
        if (totalFocusSeconds == null) {
            totalFocusSeconds = 0;
        }

        // 2. 查询指定日期的完成任务数
        Integer completedCount = logMapper.countByDate(date);
        if (completedCount == null) {
            completedCount = 0;
        }

        log.info("统计结果: 总专注时长={}秒 ({}分钟), 完成任务数={}",
                totalFocusSeconds, totalFocusSeconds / 60, completedCount);

        // 3. 封装返回结果
        DailySummaryVO summary = new DailySummaryVO(totalFocusSeconds, completedCount);
        return summary;
    }
}
