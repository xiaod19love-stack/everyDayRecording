#!/bin/bash

# =========================================================
# 源码环境专用启动脚本
# 功能: 停止旧进程 -> Maven打包 -> 启动新Jar -> 查看日志
# =========================================================

# 项目配置
APP_NAME="habit-tracker"
# 注意：这里假设你的 pom.xml 里 version 是 0.0.1-SNAPSHOT
# Maven 打包后默认会在 target 目录下生成这个名字的 jar
JAR_PATH="./target/habit-tracker-0.0.1-SNAPSHOT.jar"
DEBUG_PORT=5005

# JVM 参数
# JVM 参数详解：
# # -Xms/-Xmx: 内存设置
# # -agentlib:jdwp: 开启远程调试
# #   transport=dt_socket: 使用 Socket 连接
# #   server=y: 作为服务端等待连接
# #   suspend=n: 启动时不暂停等待调试器连接 (如果设为 y，程序会卡住直到你 IDEA 连上)
# #   address=*:5005: 监听所有网卡的 5005 端口 (JDK9+ 写法)
JAVA_OPTS="-Xms512m -Xmx1024m -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:$DEBUG_PORT"


# 1. 停止旧进程
PID=$(ps -ef | grep "$APP_NAME" | grep -v grep | awk '{print $2}')
if [ -n "$PID" ]; then
    echo ">>> 正在停止旧进程 (PID=$PID)..."
    kill -15 $PID
    sleep 3
else
    echo ">>> 没有正在运行的进程。"
fi

# 2. 执行 Maven 打包
echo ">>> 开始编译打包 (跳过单元测试)..."
# -DskipTests: 跳过测试用例，加快打包速度
mvn clean package -DskipTests

# 检查 Maven 是否成功 (返回码 0 为成功)
if [ $? -ne 0 ]; then
    echo ">>> ❌ 编译失败，请检查代码错误！"
    exit 1
fi

echo ">>> ✅ 编译成功！"

# 3. 启动 Jar 包
echo ">>> 正在启动应用..."
# nohup 后台运行
nohup java $JAVA_OPTS -jar $JAR_PATH > /dev/null 2>&1 &

# 4. 检查是否启动成功
sleep 3
NEW_PID=$(ps -ef | grep "$APP_NAME" | grep -v grep | awk '{print $2}')
if [ -n "$NEW_PID" ]; then
	    echo ">>> ✅ 应用已启动 (PID=$NEW_PID)"
	        echo ">>> ������ 远程调试端口已开启: $DEBUG_PORT"
		    echo ">>> 日志文件: ./logs/$APP_NAME.log"
		        echo ">>> 正在追踪日志 (Ctrl+C 退出查看)..."
			    echo "-------------------------------------------------------"
			        tail -f ./logs/$APP_NAME.log
			else
				    echo ">>> ❌ 启动失败，请检查 logs 目录下的日志。"
fi
