package com.tracker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tracker.dto.LogCreateDTO;
import com.tracker.entity.Habit;
import com.tracker.entity.Log;
import com.tracker.exception.BusinessException;
import com.tracker.mapper.HabitMapper;
import com.tracker.mapper.LogMapper;
import com.tracker.service.LogService;
import com.tracker.vo.LogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 打卡记录管理服务实现类
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private final LogMapper logMapper;
    private final HabitMapper habitMapper;

    // 日期格式化器
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public LogServiceImpl(LogMapper logMapper, HabitMapper habitMapper) {
        this.logMapper = logMapper;
        this.habitMapper = habitMapper;
    }

    /**
     * 获取打卡记录列表
     * 支持可选的日期范围过滤
     */
    @Override
    public List<LogVO> getLogs(LocalDate startDate, LocalDate endDate) {
        log.debug("获取打卡记录列表，startDate: {}, endDate: {}", startDate, endDate);

        // 构建查询条件
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();

        // 如果提供了开始日期，添加 >= 条件
        if (startDate != null) {
            queryWrapper.ge("date", startDate);
        }

        // 如果提供了结束日期，添加 <= 条件
        if (endDate != null) {
            queryWrapper.le("date", endDate);
        }

        // 如果都没有提供日期参数，默认返回当月数据
        if (startDate == null && endDate == null) {
            LocalDate now = LocalDate.now();
            LocalDate firstDayOfMonth = now.withDayOfMonth(1);
            LocalDate lastDayOfMonth = now.withDayOfMonth(now.lengthOfMonth());
            queryWrapper.between("date", firstDayOfMonth, lastDayOfMonth);
            log.debug("未提供日期参数，默认查询当月: {} 至 {}", firstDayOfMonth, lastDayOfMonth);
        }

        // 按日期和时间倒序排列
        queryWrapper.orderByDesc("date", "timestamp");

        List<Log> logs = logMapper.selectList(queryWrapper);
        log.debug("查询到 {} 条打卡记录", logs.size());

        // 转换 Entity -> VO
        return logs.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 提交打卡记录
     */
    @Override
    public Long createLog(LogCreateDTO dto) {
        log.info("提交打卡记录，habitId: {}, date: {}", dto.getHabitId(), dto.getDate());

        // 1. 校验习惯是否存在
        Habit habit = habitMapper.selectById(dto.getHabitId());
        if (habit == null) {
            log.warn("习惯不存在，habitId: {}", dto.getHabitId());
            throw new BusinessException("习惯不存在");
        }

        // 2. 校验日期和时间格式
        LocalDate date;
        LocalTime time;
        try {
            date = LocalDate.parse(dto.getDate(), DATE_FORMATTER);
            time = LocalTime.parse(dto.getTimestamp(), TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            log.error("日期或时间格式错误: date={}, timestamp={}", dto.getDate(), dto.getTimestamp(), e);
            throw new BusinessException("日期或时间格式错误，正确格式：日期 YYYY-MM-DD，时间 HH:mm");
        }

        // 3. 业务校验：同一习惯同一天同一时间不能重复打卡
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("habit_id", dto.getHabitId())
                .eq("date", date)
                .eq("timestamp", time);
        Long count = logMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.warn("该时间点已有打卡记录: habitId={}, date={}, timestamp={}",
                    dto.getHabitId(), date, time);
            throw new BusinessException("该时间点已有打卡记录");
        }

        // 4. DTO -> Entity
        Log logEntity = convertToEntity(dto, date, time);

        // 5. 插入数据库（createTime 和 updateTime 会自动填充）
        int rows = logMapper.insert(logEntity);
        if (rows <= 0) {
            log.error("提交打卡记录失败: habitId={}, date={}", dto.getHabitId(), dto.getDate());
            throw new BusinessException("提交打卡记录失败");
        }

        log.info("提交打卡记录成功，ID: {}", logEntity.getId());
        return logEntity.getId();
    }

    /**
     * 根据习惯ID获取打卡记录
     */
    @Override
    public List<LogVO> getLogsByHabitId(Long habitId) {
        log.debug("根据习惯ID获取打卡记录: {}", habitId);

        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("habit_id", habitId)
                .orderByDesc("date", "timestamp");

        List<Log> logs = logMapper.selectList(queryWrapper);
        log.debug("查询到 {} 条打卡记录", logs.size());

        return logs.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 根据ID获取打卡记录详情
     */
    @Override
    public LogVO getLogById(Long id) {
        log.debug("根据ID获取打卡记录详情: {}", id);

        Log logEntity = logMapper.selectById(id);
        if (logEntity == null) {
            log.warn("打卡记录不存在，ID: {}", id);
            throw new BusinessException("打卡记录不存在");
        }

        return convertToVO(logEntity);
    }

    /**
     * Entity -> VO 转换
     */
    private LogVO convertToVO(Log logEntity) {
        LogVO vo = new LogVO();
        vo.setId(logEntity.getId());
        vo.setHabitId(logEntity.getHabitId());
        vo.setDuration(logEntity.getDuration());

        // 日期和时间转换为字符串格式
        vo.setDate(logEntity.getDate().format(DATE_FORMATTER));
        vo.setTimestamp(logEntity.getTimestamp().format(TIME_FORMATTER));

        return vo;
    }

    /**
     * DTO -> Entity 转换
     */
    private Log convertToEntity(LogCreateDTO dto, LocalDate date, LocalTime time) {
        Log logEntity = new Log();
        logEntity.setHabitId(dto.getHabitId());
        logEntity.setDate(date);
        logEntity.setTimestamp(time);
        logEntity.setDuration(dto.getDuration());
        return logEntity;
    }
}
