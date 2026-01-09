package com.tracker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracker.entity.Habit;
import org.apache.ibatis.annotations.Mapper;

/**
 * 习惯 Mapper 接口
 */
@Mapper
public interface HabitMapper extends BaseMapper<Habit> {

    // MyBatis Plus 已提供基础 CRUD 方法：
    // - selectById(id) - 根据ID查询
    // - selectList(queryWrapper) - 条件查询
    // - insert(entity) - 插入
    // - updateById(entity) - 根据ID更新
    // - deleteById(id) - 根据ID删除（逻辑删除）

    // 如需自定义 SQL，可在此添加方法并在 mapper XML 文件中实现
}
