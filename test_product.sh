#!/bin/bash

# 商品模块接口测试脚本
BASE_URL="http://localhost:8080/api"

echo "=== 商品比价系统 - 商品模块接口测试 ==="
echo ""

# 测试获取热门商品
echo "1. 测试获取热门商品"
curl -X GET "$BASE_URL/products/hot" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试获取降价商品
echo "2. 测试获取降价商品"
curl -X GET "$BASE_URL/products/discount" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试获取推荐商品
echo "3. 测试获取推荐商品"
curl -X GET "$BASE_URL/products/recommend" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试获取商品详情
echo "4. 测试获取商品详情 (ID=1)"
curl -X GET "$BASE_URL/products/1" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试搜索商品
echo "5. 测试搜索商品"
curl -X POST "$BASE_URL/products/search" \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "iPhone",
    "category": "手机",
    "page": 1,
    "size": 10
  }' | jq .
echo ""

# 测试获取价格历史
echo "6. 测试获取价格历史 (ID=1)"
curl -X GET "$BASE_URL/products/1/price-history" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试获取价格趋势图表数据
echo "7. 测试获取价格趋势图表数据 (ID=1)"
curl -X GET "$BASE_URL/products/1/price-trend" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试根据分类获取商品
echo "8. 测试根据分类获取商品 (手机)"
curl -X GET "$BASE_URL/products/category/手机?page=1&size=5" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试根据品牌获取商品
echo "9. 测试根据品牌获取商品 (Apple)"
curl -X GET "$BASE_URL/products/brand/Apple?page=1&size=5" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试获取用户收藏列表
echo "10. 测试获取用户收藏列表"
curl -X GET "$BASE_URL/user/favorites?userId=1" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试添加商品到收藏
echo "11. 测试添加商品到收藏"
curl -X POST "$BASE_URL/user/favorites" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "userId=1&productId=1" | jq .
echo ""

# 测试检查收藏状态
echo "12. 测试检查收藏状态"
curl -X GET "$BASE_URL/user/favorites/check?userId=1&productId=1" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试设置价格提醒
echo "13. 测试设置价格提醒"
curl -X POST "$BASE_URL/user/price-alert" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "userId=1&productId=1&alertPrice=7500" | jq .
echo ""

# 测试更新提醒价格
echo "14. 测试更新提醒价格"
curl -X PUT "$BASE_URL/user/favorites/1/alert-price?alertPrice=7000" \
  -H "Content-Type: application/json" | jq .
echo ""

# 再次获取收藏列表查看结果
echo "15. 再次获取收藏列表查看结果"
curl -X GET "$BASE_URL/user/favorites?userId=1" \
  -H "Content-Type: application/json" | jq .
echo ""

# 测试删除收藏
echo "16. 测试删除收藏"
curl -X DELETE "$BASE_URL/user/favorites?userId=1&productId=1" \
  -H "Content-Type: application/json" | jq .
echo ""

echo "=== 测试完成 ===" 