package com.tracker.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tracker.dto.HabitCreateDTO;
import com.tracker.entity.Habit;
import com.tracker.exception.BusinessException;
import com.tracker.mapper.HabitMapper;
import com.tracker.service.HabitService;
import com.tracker.vo.HabitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 习惯管理服务实现类
 */
@Slf4j
@Service
public class HabitServiceImpl implements HabitService {

    private final HabitMapper habitMapper;

    public HabitServiceImpl(HabitMapper habitMapper) {
        this.habitMapper = habitMapper;
    }

    /**
     * 获取所有习惯列表
     * 自动过滤逻辑删除的记录
     */
    @Override
    public List<HabitVO> getHabits() {
        log.debug("获取所有习惯列表");

        // 查询所有未删除的习惯，按创建时间倒序排列
        QueryWrapper<Habit> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Habit> habits = habitMapper.selectList(queryWrapper);

        log.debug("查询到 {} 条习惯记录", habits.size());

        // 转换 Entity -> VO
        return habits.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    /**
     * 创建新习惯
     */
    @Override
    public Long createHabit(HabitCreateDTO dto) {
        log.info("创建新习惯: {}", dto.getTitle());

        // 参数校验（Spring Validation 已在 Controller 层处理）
        // 这里做业务校验：检查习惯名称是否已存在
        QueryWrapper<Habit> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", dto.getTitle());
        Long count = habitMapper.selectCount(queryWrapper);
        if (count > 0) {
            log.warn("习惯名称已存在: {}", dto.getTitle());
            throw new BusinessException("习惯名称已存在");
        }

        // DTO -> Entity
        Habit habit = convertToEntity(dto);

        // 插入数据库（createTime 和 updateTime 会自动填充）
        int rows = habitMapper.insert(habit);
        if (rows <= 0) {
            log.error("创建习惯失败: {}", dto.getTitle());
            throw new BusinessException("创建习惯失败");
        }

        log.info("创建习惯成功，ID: {}", habit.getId());
        return habit.getId();
    }

    /**
     * 根据ID获取习惯详情
     */
    @Override
    public HabitVO getHabitById(Long id) {
        log.debug("根据ID获取习惯详情: {}", id);

        Habit habit = habitMapper.selectById(id);
        if (habit == null) {
            log.warn("习惯不存在，ID: {}", id);
            throw new BusinessException("习惯不存在");
        }

        return convertToVO(habit);
    }

    /**
     * Entity -> VO 转换
     */
    private HabitVO convertToVO(Habit habit) {
        HabitVO vo = new HabitVO();
        BeanUtils.copyProperties(habit, vo);
        return vo;
    }

    /**
     * DTO -> Entity 转换
     */
    private Habit convertToEntity(HabitCreateDTO dto) {
        Habit habit = new Habit();
        BeanUtils.copyProperties(dto, habit);

        // 如果 duration 为 null，设置默认值 0
        if (habit.getDuration() == null) {
            habit.setDuration(0);
        }

        return habit;
    }
}
