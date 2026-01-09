package com.tracker.service;

import com.tracker.dto.LogCreateDTO;
import com.tracker.vo.LogVO;

import java.time.LocalDate;
import java.util.List;

/**
 * 打卡记录管理服务接口
 */
public interface LogService {

    /**
     * 获取打卡记录列表
     * @param startDate 开始日期（可选）
     * @param endDate 结束日期（可选）
     * @return 打卡记录列表
     */
    List<LogVO> getLogs(LocalDate startDate, LocalDate endDate);

    /**
     * 提交打卡记录
     * @param dto 创建打卡记录请求对象
     * @return 新创建的记录ID
     */
    Long createLog(LogCreateDTO dto);

    /**
     * 根据习惯ID获取打卡记录
     * @param habitId 习惯ID
     * @return 打卡记录列表
     */
    List<LogVO> getLogsByHabitId(Long habitId);

    /**
     * 根据ID获取打卡记录详情
     * @param id 记录ID
     * @return 打卡记录详情
     */
    LogVO getLogById(Long id);
}
