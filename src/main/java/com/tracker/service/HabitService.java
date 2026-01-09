package com.tracker.service;

import com.tracker.dto.HabitCreateDTO;
import com.tracker.vo.HabitVO;

import java.util.List;

/**
 * 习惯管理服务接口
 */
public interface HabitService {

    /**
     * 获取所有习惯列表
     * @return 习惯列表
     */
    List<HabitVO> getHabits();

    /**
     * 创建新习惯
     * @param dto 创建习惯请求对象
     * @return 新创建的习惯ID
     */
    Long createHabit(HabitCreateDTO dto);

    /**
     * 根据ID获取习惯详情
     * @param id 习惯ID
     * @return 习惯详情
     */
    HabitVO getHabitById(Long id);
}
