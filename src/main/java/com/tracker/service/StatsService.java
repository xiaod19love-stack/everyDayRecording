package com.tracker.service;

import com.tracker.vo.DailySummaryVO;

import java.time.LocalDate;

/**
 * 统计服务接口
 */
public interface StatsService {

    /**
     * 获取每日概览统计
     * @param date 日期（可选，默认今天）
     * @return 今日概览统计数据
     */
    DailySummaryVO getDailySummary(LocalDate date);
}
