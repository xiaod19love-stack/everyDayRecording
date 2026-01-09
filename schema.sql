-- ===================================
-- 每日打卡 App (Daily Habit Tracker)
-- 数据库表结构设计
-- MySQL 8.0
-- ===================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS daily_habit_tracker
    DEFAULT CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE daily_habit_tracker;

-- ===================================
-- 1. 习惯表 (habits)
-- ===================================
CREATE TABLE `habits` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `title` VARCHAR(100) NOT NULL COMMENT '习惯名称',
    `type` VARCHAR(20) NOT NULL COMMENT '习惯类型: punch(打卡), stopwatch(计时), countdown(倒计时)',
    `duration` INT NOT NULL DEFAULT 0 COMMENT '目标时长(秒)，仅countdown模式有效',
    `icon` VARCHAR(50) NOT NULL COMMENT '图标Emoji或URL',
    `subtitle` VARCHAR(200) DEFAULT NULL COMMENT '副标题/描述',
    `color_key` VARCHAR(20) NOT NULL COMMENT '颜色主题: blue, orange, green, purple',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识: 0-未删除, 1-已删除',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_deleted` (`deleted`),
    INDEX `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='习惯表';

-- ===================================
-- 2. 打卡记录表 (logs)
-- ===================================
CREATE TABLE `logs` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `habit_id` BIGINT UNSIGNED NOT NULL COMMENT '关联的习惯ID',
    `date` DATE NOT NULL COMMENT '打卡日期 (YYYY-MM-DD)',
    `timestamp` TIME NOT NULL COMMENT '打卡具体时间 (HH:mm:ss)',
    `duration` INT NOT NULL DEFAULT 0 COMMENT '专注时长(秒)，punch类型为0',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标识: 0-未删除, 1-已删除',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_habit_id` (`habit_id`),
    INDEX `idx_date` (`date`),
    INDEX `idx_deleted` (`deleted`),
    INDEX `idx_habit_date` (`habit_id`, `date`),
    CONSTRAINT `fk_logs_habit_id` FOREIGN KEY (`habit_id`) REFERENCES `habits` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='打卡记录表';

-- ===================================
-- 3. 插入测试数据 (可选)
-- ===================================

-- 插入示例习惯
INSERT INTO `habits` (`title`, `type`, `duration`, `icon`, `subtitle`, `color_key`) VALUES
('晨间阅读', 'countdown', 1500, '📖', '每天进步一点点', 'blue'),
('上下班打卡', 'punch', 0, '💼', '努力工作', 'green'),
('运动打卡', 'stopwatch', 0, '🏃', '保持健康', 'orange'),
('喝水', 'punch', 0, '💧', '每天8杯水', 'purple');

-- 插入示例打卡记录
INSERT INTO `logs` (`habit_id`, `date`, `timestamp`, `duration`) VALUES
(1, '2023-10-01', '08:30:00', 1500),
(2, '2023-10-01', '09:00:00', 0),
(3, '2023-10-01', '18:00:00', 2400),
(1, '2023-10-02', '08:00:00', 1500),
(2, '2023-10-02', '09:15:00', 0);
