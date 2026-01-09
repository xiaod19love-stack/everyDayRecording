# 每日打卡 App (Daily Habit Tracker) 接口文档

**版本**: v1.0.0
**状态**: Draft
**基础 URL**: `https://api.yourdomain.com/v1` (示例)
## 1. 通用说明

### 1.1 认证方式 (Authentication)

所有接口（除登录/注册外）均需要在 Request Header 中携带 Token。
- Header: `Authorization: Bearer <token>`

### 1.2 统一响应格式 (Response Structure)

后端返回的 JSON 数据应遵循统一结构：
```
{
  "code": 200,          // 业务状态码：200 成功，非 200 失败
  "message": "success", // 提示信息
  "data": { ... }       // 具体业务数据
}
```
## 2. 习惯管理模块 (Habits)

### 2.1 获取习惯列表

用于应用首页初始化时加载所有习惯。
- **接口地址**: `/habits`
- **请求方式**: `GET`
- **请求参数**: 无
- **响应示例**:

```
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "晨间阅读",
      "type": "countdown",
      "duration": 1500,
      "icon": "📖",
      "subtitle": "每天进步一点点",
      "colorKey": "blue"
    },
    {
      "id": 3,
      "title": "上下班打卡",
      "type": "punch",
      "duration": 0,
      "icon": "💼",
      "subtitle": "努力工作",
      "colorKey": "green"
    }
  ]
}
```
**###** 2.2 创建新习惯

对应前端 `AddHabitModal` 组件的保存操作。
- **接口地址**: `/habits`
- **请求方式**: `POST`
- **请求体 (Request Body)**:


| | | | |
|:-:|:-:|:-:|:-:|
|**字段名**|**类型**|**必填**|**说明**|
|title|string|是|习惯名称|
|type|string|是|模式: `punch`(打卡), `stopwatch`(计时), `countdown`(倒数)|
|duration|number|否|目标时长(秒)，仅 `countdown` 模式有效，其他传 0|
|icon|string|是|图标 Emoji 或 URL|
|colorKey|string|是|颜色主题: `blue`, `orange`, `green`, `purple`|
- **请求示例**:

```
{
  "title": "喝水",
  "type": "punch",
  "duration": 0,
  "icon": "💧",
  "colorKey": "purple"
}
```
- **响应示例**:

```{
  "code": 200,
  "message": "创建成功",
  "data": { "id": 5 } // 返回新生成的 ID
}
```
## 3. 打卡记录模块 (Logs)

### 3.1 获取打卡记录

用于渲染首页简略统计、日历视图 (`StatsView`) 以及详情弹窗 (`DayDetailModal`)。 前端通常需要按月获取数据以渲染日历。
- **接口地址**: `/logs`
- **请求方式**: `GET`
- **请求参数 (Query Params)**:


| | | | |
|:-:|:-:|:-:|:-:|
|**参数名**|**类型**|**必填**|**说明**|
|startDate|string|否|开始日期 (YYYY-MM-DD)|
|endDate|string|否|结束日期 (YYYY-MM-DD)|
注：如果不传日期范围，默认返回当月数据或最近N条数据，视后端策略而定。
- **响应示例**:

```
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 101,
      "habitId": 1,
      "date": "2023-10-01",
      "timestamp": "08:30",
      "duration": 1500
    },
    {
      "id": 102,
      "habitId": 3,
      "date": "2023-10-02",
      "timestamp": "09:00",
      "duration": 0
    }
  ]
}
```
### 3.2 提交打卡/完成计时

对应前端 `handleComplete` 方法。当用户完成一次打卡、计时结束或倒计时结束时调用。
- **接口地址**: `/logs`
- **请求方式**: `POST`
- **请求体 (Request Body)**:


| | | | |
|:-:|:-:|:-:|:-:|
|**字段名**|**类型**|**必填**|**说明**|
|habitId|number|是|关联的习惯 ID|
|date|string|是|打卡日期 (YYYY-MM-DD)，通常为客户端当前日期|
|timestamp|string|是|打卡具体时间 (HH:mm)，用于时间轴排序|
|duration|number|是|专注时长(秒)。`punch` 类型传 0|
- **请求示例**:

```
{
  "habitId": 1,
  "date": "2023-10-28",
  "timestamp": "22:00",
  "duration": 3000
}
```
- **响应示例**:

```
{
  "code": 200,
  "message": "打卡成功",
  "data": { "id": 205 }
}
```
## 4. 统计模块 (Stats - 可选)

注：前端目前是根据 `/logs` 接口返回的原始数据在本地计算今日专注时长。为了提高性能，后端可以提供聚合接口。
### 4.1 获取今日概览

用于首页顶部卡片展示 "今日专注" 和 "今日打卡数"。
- **接口地址**: `/stats/daily-summary`
- **请求方式**: `GET`
- **请求参数**:
	- `date`: string (YYYY-MM-DD)
- **响应示例**:

```
{
  "code": 200,
  "data": {
    "totalFocusSeconds": 2700, // 今日总专注秒数 (前端换算为 45min)
    "completedCount": 3        // 今日完成任务数
  }
}
```

## 5. 数据字典与枚举

### 5.1 习惯类型 (Habit Type)


| | |
|:-:|:-:|
|**值**|**说明**|
|`punch`|一键打卡 (Check-in)|
|`stopwatch`|正向计时 (Timer)|
|`countdown`|倒计时 (Countdown)|
### 5.2 颜色 Key (Color Key)

对应前端马卡龙配色方案。
| 值 | 说明 |
| :--- | :--- |
| blue | 浅蓝主题 |
| orange | 浅橙主题 |
| green | 浅绿主题 |
| purple | 浅紫主题 |