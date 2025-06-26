#!/bin/bash

echo "=== 生成商品比价模块API文档 ==="
echo ""

# 创建API文档目录
mkdir -p docs/api

# 生成ProductController的API文档
cat > docs/api/product_api.md << 'EOF'
# 商品比价模块API文档

## 基础信息
- 基础路径: `/products`
- 跨域支持: 是
- 认证要求: 需要JWT Token

## API列表

### 1. 获取热门商品
- **URL**: `GET /products/hot`
- **描述**: 获取热门商品列表
- **参数**: 无
- **响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "商品名称",
      "price": "¥3299.0",
      "img": "图片URL",
      "platforms": ["京东", "天猫"],
      "priceChange": 10.5
    }
  ]
}
```

### 2. 获取降价商品
- **URL**: `GET /products/discount`
- **描述**: 获取降价商品列表
- **参数**: 无
- **响应格式**: 同热门商品

### 3. 获取推荐商品
- **URL**: `GET /products/recommend`
- **描述**: 获取推荐商品列表
- **参数**: 无
- **响应格式**: 同热门商品

### 4. 获取商品详情
- **URL**: `GET /products/{id}`
- **描述**: 获取指定商品的详细信息
- **参数**: 
  - `id` (路径参数): 商品ID
- **响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "id": 1,
    "title": "商品名称",
    "desc": "商品描述",
    "price": "¥3299.0",
    "img": "图片URL",
    "platforms": ["京东", "天猫"],
    "priceChange": 10.5
  }
}
```

### 5. 搜索商品
- **URL**: `POST /products/search`
- **描述**: 根据条件搜索商品
- **请求体**:
```json
{
  "keyword": "手机",
  "category": "手机",
  "brand": "品牌名",
  "platform": "京东",
  "minPrice": 3000,
  "maxPrice": 4000,
  "isHot": true,
  "isRecommend": false,
  "page": 1,
  "size": 20,
  "sortBy": "price",
  "sortOrder": "ASC"
}
```
- **响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "content": [...],
    "totalElements": 100,
    "totalPages": 5,
    "size": 20,
    "number": 0
  }
}
```

### 6. 获取价格历史
- **URL**: `GET /products/{id}/price-history`
- **描述**: 获取商品的价格历史数据
- **参数**: 
  - `id` (路径参数): 商品ID
- **响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "date": "2024-01-01",
      "price": 3299.00,
      "platform": "京东"
    }
  ]
}
```

### 7. 获取价格趋势图表数据
- **URL**: `GET /products/{id}/price-trend`
- **描述**: 获取商品价格趋势图表所需的数据
- **参数**: 
  - `id` (路径参数): 商品ID
- **响应示例**:
```json
{
  "code": 200,
  "message": "success",
  "data": {
    "productName": "商品名称",
    "labels": ["1日", "5日", "10日"],
    "comparisonData": [
      {
        "label": "京东",
        "data": [3499, 3399, 3349],
        "borderColor": "#e74c3c",
        "borderWidth": 3,
        "tension": 0.3
      }
    ],
    "fluctuationLabels": ["1月", "2月", "3月"],
    "fluctuationValues": [3899, 3799, 3699]
  }
}
```

### 8. 根据分类获取商品
- **URL**: `GET /products/category/{category}`
- **描述**: 根据分类获取商品列表
- **参数**: 
  - `category` (路径参数): 商品分类
  - `page` (查询参数): 页码，默认1
  - `size` (查询参数): 每页大小，默认20
- **响应格式**: 同搜索商品

### 9. 根据品牌获取商品
- **URL**: `GET /products/brand/{brand}`
- **描述**: 根据品牌获取商品列表
- **参数**: 
  - `brand` (路径参数): 品牌名称
  - `page` (查询参数): 页码，默认1
  - `size` (查询参数): 每页大小，默认20
- **响应格式**: 同搜索商品

## 错误响应
所有API在发生错误时都会返回以下格式：
```json
{
  "code": 500,
  "message": "错误描述信息"
}
```

## 常见错误码
- 200: 成功
- 400: 请求参数错误
- 401: 未授权
- 404: 资源不存在
- 500: 服务器内部错误
EOF

echo "✓ API文档已生成到 docs/api/product_api.md"
echo ""

# 生成Postman集合
cat > docs/api/product_postman_collection.json << 'EOF'
{
  "info": {
    "name": "商品比价模块API",
    "description": "商品比价模块的API接口集合",
    "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
  },
  "item": [
    {
      "name": "获取热门商品",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/products/hot",
          "host": ["{{baseUrl}}"],
          "path": ["products", "hot"]
        }
      }
    },
    {
      "name": "获取降价商品",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/products/discount",
          "host": ["{{baseUrl}}"],
          "path": ["products", "discount"]
        }
      }
    },
    {
      "name": "获取推荐商品",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/products/recommend",
          "host": ["{{baseUrl}}"],
          "path": ["products", "recommend"]
        }
      }
    },
    {
      "name": "获取商品详情",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/products/1",
          "host": ["{{baseUrl}}"],
          "path": ["products", "1"]
        }
      }
    },
    {
      "name": "搜索商品",
      "request": {
        "method": "POST",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          },
          {
            "key": "Content-Type",
            "value": "application/json",
            "type": "text"
          }
        ],
        "body": {
          "mode": "raw",
          "raw": "{\n  \"keyword\": \"手机\",\n  \"page\": 1,\n  \"size\": 20,\n  \"sortBy\": \"price\",\n  \"sortOrder\": \"ASC\"\n}"
        },
        "url": {
          "raw": "{{baseUrl}}/products/search",
          "host": ["{{baseUrl}}"],
          "path": ["products", "search"]
        }
      }
    },
    {
      "name": "获取价格历史",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/products/1/price-history",
          "host": ["{{baseUrl}}"],
          "path": ["products", "1", "price-history"]
        }
      }
    },
    {
      "name": "获取价格趋势",
      "request": {
        "method": "GET",
        "header": [
          {
            "key": "Authorization",
            "value": "Bearer {{token}}",
            "type": "text"
          }
        ],
        "url": {
          "raw": "{{baseUrl}}/products/1/price-trend",
          "host": ["{{baseUrl}}"],
          "path": ["products", "1", "price-trend"]
        }
      }
    }
  ],
  "variable": [
    {
      "key": "baseUrl",
      "value": "http://localhost:8080"
    },
    {
      "key": "token",
      "value": "your_jwt_token_here"
    }
  ]
}
EOF

echo "✓ Postman集合已生成到 docs/api/product_postman_collection.json"
echo ""

echo "=== API文档生成完成 ===" 