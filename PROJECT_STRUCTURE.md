# 项目结构说明

## 📂 完整目录树

```
everyDayRecording/
├── src/
│   └── main/
│       ├── java/com/tracker/
│       │   ├── common/                          # 通用类
│       │   │   └── Result.java                  # ✅ 统一响应结果类
│       │   │
│       │   ├── config/                          # 配置类
│       │   │   └── MybatisPlusConfig.java       # ✅ MyBatis Plus 配置
│       │   │
│       │   ├── controller/                      # 控制器层（待实现）
│       │   │   ├── HabitController.java         # ⏳ 习惯管理接口
│       │   │   ├── LogController.java           # ⏳ 打卡记录接口
│       │   │   └── StatsController.java         # ⏳ 统计接口
│       │   │
│       │   ├── service/                         # 业务逻辑层（待实现）
│       │   │   ├── HabitService.java            # ⏳ 习惯业务逻辑
│       │   │   ├── LogService.java              # ⏳ 打卡记录业务逻辑
│       │   │   └── StatsService.java            # ⏳ 统计业务逻辑
│       │   │
│       │   ├── mapper/                          # 数据访问层
│       │   │   ├── HabitMapper.java             # ✅ 习惯 Mapper
│       │   │   └── LogMapper.java               # ✅ 打卡记录 Mapper
│       │   │
│       │   ├── entity/                          # 实体类
│       │   │   ├── Habit.java                   # ✅ 习惯实体
│       │   │   └── Log.java                     # ✅ 打卡记录实体
│       │   │
│       │   ├── dto/                             # 数据传输对象
│       │   │   ├── HabitCreateDTO.java          # ✅ 创建习惯请求
│       │   │   └── LogCreateDTO.java            # ✅ 创建打卡记录请求
│       │   │
│       │   ├── vo/                              # 视图对象
│       │   │   ├── HabitVO.java                 # ✅ 习惯返回对象
│       │   │   ├── LogVO.java                   # ✅ 打卡记录返回对象
│       │   │   ├── DailySummaryVO.java          # ✅ 今日概览返回对象
│       │   │   └── IdVO.java                    # ✅ ID返回对象
│       │   │
│       │   ├── enums/                           # 枚举类
│       │   │   ├── HabitType.java               # ✅ 习惯类型枚举
│       │   │   └── ColorKey.java                # ✅ 颜色主题枚举
│       │   │
│       │   ├── exception/                       # 异常处理
│       │   │   ├── BusinessException.java       # ✅ 业务异常类
│       │   │   └── GlobalExceptionHandler.java  # ✅ 全局异常处理器
│       │   │
│       │   └── DailyHabitTrackerApplication.java # ✅ 启动类
│       │
│       └── resources/
│           ├── application.yml                  # ✅ 应用配置文件
│           └── mapper/                          # MyBatis XML 映射文件（可选）
│
├── schema.sql                                   # ✅ 数据库建表脚本
├── pom.xml                                      # ✅ Maven 配置文件
├── README.md                                    # ✅ 项目文档
├── PERSISTENCE_LAYER.md                         # ✅ 持久层完成文档
├── PROJECT_STRUCTURE.md                         # ✅ 本文档
└── .gitignore                                   # ✅ Git 忽略配置
```

---

## 📊 开发进度总览

| 模块 | 状态 | 文件数 | 完成度 |
|------|------|--------|--------|
| **基础设施** | ✅ 完成 | 6 | 100% |
| **枚举类** | ✅ 完成 | 2 | 100% |
| **实体类** | ✅ 完成 | 2 | 100% |
| **Mapper层** | ✅ 完成 | 2 | 100% |
| **DTO对象** | ✅ 完成 | 2 | 100% |
| **VO对象** | ✅ 完成 | 4 | 100% |
| **Service层** | ⏳ 待实现 | 0 | 0% |
| **Controller层** | ⏳ 待实现 | 0 | 0% |

**总体完成度**: 约 **60%**（基础设施和持久层已完成）

---

## 🎯 各层职责说明

### 1. Controller 层（控制器层）
**职责**: 接收 HTTP 请求，调用 Service 处理业务，返回响应
- 接收前端请求参数
- 参数校验（通过 @Valid 注解）
- 调用 Service 层处理业务
- 封装统一响应格式 Result<T>
- Swagger 接口文档

**示例**:
```java
@RestController
@RequestMapping("/habits")
public class HabitController {
    
    @Autowired
    private HabitService habitService;
    
    @GetMapping
    public Result<List<HabitVO>> getHabits() {
        List<HabitVO> habits = habitService.getHabits();
        return Result.success(habits);
    }
}
```

---

### 2. Service 层（业务逻辑层）
**职责**: 处理业务逻辑，调用 Mapper 访问数据库
- 实现业务规则
- 数据转换（Entity ↔ VO/DTO）
- 事务管理
- 调用 Mapper 进行数据操作

**示例**:
```java
@Service
public class HabitService {
    
    @Autowired
    private HabitMapper habitMapper;
    
    public List<HabitVO> getHabits() {
        List<Habit> habits = habitMapper.selectList(null);
        // 转换 Entity -> VO
        return convertToVO(habits);
    }
}
```

---

### 3. Mapper 层（数据访问层）
**职责**: 与数据库交互，执行 SQL 操作
- 继承 MyBatis Plus 的 BaseMapper
- 自动拥有 CRUD 方法
- 可自定义 SQL 查询

**已实现**: ✅
- HabitMapper - 习惯表操作
- LogMapper - 打卡记录表操作

---

### 4. Entity 层（实体类）
**职责**: 映射数据库表结构
- 使用 MyBatis Plus 注解
- 与数据库表一一对应
- 包含所有字段（包括系统字段）

**已实现**: ✅
- Habit - 习惯表
- Log - 打卡记录表

---

### 5. DTO 层（数据传输对象）
**职责**: 接收前端请求参数
- 用于接收客户端数据
- 包含参数校验注解
- 只包含业务需要的字段

**已实现**: ✅
- HabitCreateDTO - 创建习惯
- LogCreateDTO - 创建打卡记录

---

### 6. VO 层（视图对象）
**职责**: 返回给前端的数据
- 用于响应客户端请求
- 只包含前端需要的字段
- 可能包含多表关联数据

**已实现**: ✅
- HabitVO - 习惯信息
- LogVO - 打卡记录信息
- DailySummaryVO - 今日概览
- IdVO - ID返回对象

---

## 🔄 数据流转示意图

```
┌──────────┐
│  前端    │
└────┬─────┘
     │ HTTP Request (JSON)
     ▼
┌──────────────────┐
│  Controller      │ ← 接收请求，参数校验
└────┬─────────────┘
     │ DTO
     ▼
┌──────────────────┐
│  Service         │ ← 业务逻辑，数据转换
└────┬─────────────┘
     │ Entity
     ▼
┌──────────────────┐
│  Mapper          │ ← 数据库操作
└────┬─────────────┘
     │ SQL
     ▼
┌──────────────────┐
│  MySQL Database  │
└──────────────────┘
     │ Entity
     ▼
┌──────────────────┐
│  Service         │ ← Entity → VO 转换
└────┬─────────────┘
     │ VO
     ▼
┌──────────────────┐
│  Controller      │ ← 封装 Result<VO>
└────┬─────────────┘
     │ Result<VO> (JSON)
     ▼
┌──────────────────┐
│  前端            │
└──────────────────┘
```

---

## 📦 依赖关系

```
Controller  →  Service  →  Mapper  →  Database
    ↑             ↑          ↑
    │             │          │
  依赖          依赖       依赖
    │             │          │
   DTO           VO       Entity
```

---

## 🛠️ 技术选型

| 层次 | 技术/框架 | 版本 |
|------|----------|------|
| 核心框架 | Spring Boot | 3.2.0 |
| JDK | Java | 17 |
| 持久层 | MyBatis Plus | 3.5.5 |
| 数据库 | MySQL | 8.0 |
| 代码简化 | Lombok | - |
| 对象转换 | MapStruct | 1.5.5 |
| 参数校验 | Spring Validation | - |
| API 文档 | SpringDoc OpenAPI | 2.3.0 |

---

## 📝 编码规范

### 命名规范
- **类名**: 大驼峰 `HabitService`
- **方法名**: 小驼峰 `getHabits()`
- **变量名**: 小驼峰 `habitId`
- **常量**: 全大写下划线 `MAX_COUNT`
- **数据库字段**: 小写下划线 `habit_id`

### 包命名规范
- `controller`: 控制器类以 `Controller` 结尾
- `service`: 服务类以 `Service` 结尾
- `mapper`: 映射接口以 `Mapper` 结尾
- `entity`: 实体类，与表名对应
- `dto`: 数据传输对象以 `DTO` 结尾
- `vo`: 视图对象以 `VO` 结尾

---

## 🚀 快速开始

### 1. 下载依赖
```bash
cd e:\Users\admin\Documents\GitHub\everyDayRecording
mvn clean install
```

### 2. 初始化数据库
```bash
mysql -u root -p < schema.sql
```

### 3. 修改配置
编辑 `src/main/resources/application.yml`，修改数据库连接信息

### 4. 启动项目
```bash
mvn spring-boot:run
```

### 5. 访问 Swagger
```
http://localhost:8080/v1/swagger-ui.html
```

---

**更新时间**: 2026-01-09
**版本**: v1.0.0
