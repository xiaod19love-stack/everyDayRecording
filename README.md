# 每日打卡 App (Daily Habit Tracker) - 后端项目

## 项目简介

这是一个基于 Spring Boot 3.2 + JDK 17 开发的每日打卡应用后端服务。支持习惯管理、打卡记录、统计分析等功能。

## 技术栈

- **核心框架**: Spring Boot 3.2.0
- **JDK**: Java 17
- **持久层**: MyBatis Plus 3.5.5
- **数据库**: MySQL 8.0
- **工具库**: Lombok, MapStruct
- **参数校验**: Spring Boot Validation
- **接口文档**: SpringDoc OpenAPI (Swagger 3)

## 项目结构

```
everyDayRecording/
├── src/
│   └── main/
│       ├── java/com/tracker/
│       │   ├── common/              # 通用类
│       │   │   └── Result.java      # 统一响应结果类
│       │   ├── config/              # 配置类
│       │   │   └── MybatisPlusConfig.java
│       │   ├── controller/          # 控制器层
│       │   ├── service/             # 业务逻辑层
│       │   ├── mapper/              # 数据访问层
│       │   ├── entity/              # 实体类
│       │   ├── dto/                 # 数据传输对象
│       │   ├── vo/                  # 视图对象
│       │   ├── enums/               # 枚举类
│       │   │   ├── HabitType.java   # 习惯类型枚举
│       │   │   └── ColorKey.java    # 颜色主题枚举
│       │   ├── exception/           # 异常处理
│       │   │   ├── BusinessException.java
│       │   │   └── GlobalExceptionHandler.java
│       │   └── DailyHabitTrackerApplication.java  # 启动类
│       └── resources/
│           ├── application.yml      # 配置文件
│           └── mapper/              # MyBatis XML 映射文件
├── schema.sql                       # 数据库建表脚本
└── pom.xml                          # Maven 配置文件
```

## 快速开始

### 1. 环境准备

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库初始化

执行 `schema.sql` 文件创建数据库和表：

```bash
mysql -u root -p < schema.sql
```

或者在 MySQL 客户端中执行该文件内容。

### 3. 修改配置

编辑 `src/main/resources/application.yml`，修改数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/daily_habit_tracker?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root        # 修改为你的数据库用户名
    password: root        # 修改为你的数据库密码
```

### 4. 导入 Maven 依赖

**重要**: 在 IDE 中打开项目后，需要先导入 Maven 依赖才能消除编译错误。

#### IDEA 用户：
1. 右键点击 `pom.xml` 文件
2. 选择 **"Maven"** → **"Reload project"**
3. 或者点击右侧 Maven 面板的刷新按钮

#### VS Code 用户：
1. 打开命令面板 (Ctrl+Shift+P)
2. 输入 "Java: Clean Java Language Server Workspace"
3. 或者在终端执行: `mvn clean install`

### 5. 运行项目

```bash
mvn spring-boot:run
```

或者在 IDE 中直接运行 `DailyHabitTrackerApplication.java`。

### 6. 访问接口文档

启动成功后，访问 Swagger UI：

```
http://localhost:8080/v1/swagger-ui.html
```

API 文档 JSON：
```
http://localhost:8080/v1/v3/api-docs
```

## API 接口

### 基础路径
```
http://localhost:8080/v1
```

### 统一响应格式

```json
{
  "code": 200,
  "message": "success",
  "data": { ... }
}
```

### 主要接口

1. **习惯管理**
   - `GET /habits` - 获取习惯列表
   - `POST /habits` - 创建新习惯

2. **打卡记录**
   - `GET /logs` - 获取打卡记录（支持日期过滤）
   - `POST /logs` - 提交打卡记录

3. **统计模块**
   - `GET /stats/daily-summary` - 获取今日概览

详细接口说明请参考 `每日打卡 App (Daily Habit Tracker) 接口文档.md`。

## 数据库表结构

### habits 表（习惯表）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| title | VARCHAR(100) | 习惯名称 |
| type | VARCHAR(20) | 习惯类型: punch/stopwatch/countdown |
| duration | INT | 目标时长(秒) |
| icon | VARCHAR(50) | 图标Emoji |
| subtitle | VARCHAR(200) | 副标题 |
| color_key | VARCHAR(20) | 颜色主题: blue/orange/green/purple |
| deleted | TINYINT(1) | 逻辑删除标识 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

### logs 表（打卡记录表）

| 字段名 | 类型 | 说明 |
|--------|------|------|
| id | BIGINT | 主键ID |
| habit_id | BIGINT | 关联的习惯ID |
| date | DATE | 打卡日期 |
| timestamp | TIME | 打卡时间 |
| duration | INT | 专注时长(秒) |
| deleted | TINYINT(1) | 逻辑删除标识 |
| create_time | DATETIME | 创建时间 |
| update_time | DATETIME | 更新时间 |

## 核心功能特性

✅ **统一响应格式** - 所有接口返回统一的 Result<T> 结构
✅ **全局异常处理** - 优雅处理业务异常和参数校验异常
✅ **逻辑删除** - MyBatis Plus 自动处理逻辑删除
✅ **自动填充** - 创建时间和更新时间自动填充
✅ **参数校验** - 使用 JSR-303 注解进行参数校验
✅ **枚举映射** - 使用 @EnumValue 和 @JsonValue 处理枚举
✅ **Swagger 文档** - 自动生成交互式 API 文档

## 开发规范

### 分层架构

- **Controller**: 负责接收请求和返回响应
- **Service**: 业务逻辑处理
- **Mapper**: 数据访问操作
- **Entity**: 数据库实体映射
- **DTO**: 接收前端请求参数
- **VO**: 返回给前端的视图对象

### 命名规范

- 类名: 大驼峰 (PascalCase)
- 方法名/变量名: 小驼峰 (camelCase)
- 常量: 全大写下划线分隔 (UPPER_CASE)
- 数据库字段: 全小写下划线分隔 (snake_case)

### 异常处理

使用 `BusinessException` 抛出业务异常：

```java
throw new BusinessException("习惯不存在");
throw new BusinessException(404, "资源未找到");
```

全局异常处理器会自动捕获并返回统一格式。

## 项目初始化完成 ✅

已完成内容：

- [x] MySQL 建表 SQL
- [x] Maven 项目配置 (pom.xml)
- [x] 基础包结构
- [x] 通用返回类 Result
- [x] 业务异常类 BusinessException
- [x] 全局异常处理器 GlobalExceptionHandler
- [x] HabitType 和 ColorKey 枚举
- [x] MyBatis Plus 配置
- [x] 应用配置文件 application.yml
- [x] 启动类 DailyHabitTrackerApplication

## 下一步开发

待实现模块：

- [ ] Entity 实体类 (Habit, Log)
- [ ] DTO 和 VO 类
- [ ] Mapper 接口
- [ ] Service 业务逻辑
- [ ] Controller 接口实现

## 常见问题

### Q: IDE 报错显示无法解析 Lombok/Spring 等依赖？

**A**: 这是因为 Maven 依赖还未下载。请执行以下操作：
1. 在项目根目录执行 `mvn clean install`
2. 或在 IDE 中刷新 Maven 项目
3. 确保 IDE 已安装 Lombok 插件（IDEA/Eclipse/VS Code）

### Q: 数据库连接失败？

**A**: 请检查：
1. MySQL 服务是否启动
2. 数据库 `daily_habit_tracker` 是否已创建
3. `application.yml` 中的用户名密码是否正确
4. 防火墙是否阻止了 3306 端口

### Q: 如何修改服务端口？

**A**: 编辑 `application.yml` 文件中的 `server.port` 配置。

## 许可证

MIT License

---

**开发时间**: 2026-01
**版本**: v1.0.0