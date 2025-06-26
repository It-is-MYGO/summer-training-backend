#!/bin/bash

# 设置基础URL
BASE_URL="http://localhost:8080/api"

echo "=== Auth 模块测试脚本 ==="
echo "基础URL: $BASE_URL"
echo ""

# 测试公开端点
echo "1. 测试公开端点..."
curl -X GET "$BASE_URL/test/public" -H "Content-Type: application/json"
echo ""
echo ""

# 测试用户注册
echo "2. 测试用户注册..."
REGISTER_RESPONSE=$(curl -s -X POST "$BASE_URL/auth/register" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }')
echo "$REGISTER_RESPONSE"
echo ""

# 提取token
TOKEN=$(echo "$REGISTER_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4)
echo "获取到的Token: $TOKEN"
echo ""

# 测试获取当前用户信息
echo "3. 测试获取当前用户信息..."
curl -X GET "$BASE_URL/auth/me" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
echo ""
echo ""

# 测试用户专用端点
echo "4. 测试用户专用端点..."
curl -X GET "$BASE_URL/test/user" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
echo ""
echo ""

# 测试短信发送
echo "5. 测试短信验证码发送..."
curl -X POST "$BASE_URL/sms/send?phone=13800138000" \
  -H "Content-Type: application/json"
echo ""
echo ""

# 测试退出登录
echo "6. 测试退出登录..."
curl -X POST "$BASE_URL/auth/logout" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json"
echo ""
echo ""

echo "=== 测试完成 ===" 