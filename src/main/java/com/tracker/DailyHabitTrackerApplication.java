package com.tracker;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 每日打卡 App 启动类
 */
@SpringBootApplication
@MapperScan("com.tracker.mapper")
public class DailyHabitTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DailyHabitTrackerApplication.class, args);
    }
}
