#!/bin/bash

echo "=== Spring Boot Auth 项目启动脚本 ==="
echo ""

# 检查Java版本
echo "检查Java版本..."
java -version
echo ""

# 检查Maven版本
echo "检查Maven版本..."
mvn -version
echo ""

# 清理并编译项目
echo "清理并编译项目..."
mvn clean compile -q
if [ $? -eq 0 ]; then
    echo "编译成功！"
else
    echo "编译失败，请检查错误信息"
    exit 1
fi
echo ""

# 启动应用
echo "启动Spring Boot应用..."
echo "应用将在 http://localhost:8080/api 启动"
echo "按 Ctrl+C 停止应用"
echo ""

mvn spring-boot:run 