package com.tracker.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tracker.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

/**
 * 打卡记录 Mapper 接口
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {

    // MyBatis Plus 已提供基础 CRUD 方法

    /**
     * 查询指定日期的总专注时长（秒）
     * @param date 日期
     * @return 总秒数
     */
    @Select("SELECT COALESCE(SUM(duration), 0) FROM logs WHERE date = #{date} AND deleted = 0")
    Integer sumDurationByDate(@Param("date") LocalDate date);

    /**
     * 查询指定日期的完成任务数
     * @param date 日期
     * @return 任务数
     */
    @Select("SELECT COUNT(*) FROM logs WHERE date = #{date} AND deleted = 0")
    Integer countByDate(@Param("date") LocalDate date);
}
