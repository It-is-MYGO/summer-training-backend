# 商品比价系统 - 商品模块 API 文档

## 基础信息
- 基础URL: `http://localhost:8080/api`
- 响应格式: JSON
- 统一响应结构:
```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## 商品相关接口

### 1. 获取热门商品
- **URL**: `GET /products/hot`
- **描述**: 获取热门商品列表
- **响应示例**:
```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "iPhone 15 Pro",
      "desc": "苹果最新旗舰手机，搭载A17 Pro芯片",
      "price": "¥7999.00",
      "img": "https://example.com/iphone15.jpg",
      "platforms": ["京东"],
      "priceChange": -5.2,
      "brand": "Apple",
      "category": "手机",
      "rating": 4.8,
      "reviewCount": 1250,
      "salesCount": 5000,
      "isHot": true,
      "isRecommend": true
    }
  ]
}
```

### 2. 获取降价商品
- **URL**: `GET /products/discount`
- **描述**: 获取降价商品列表
- **响应示例**: 同热门商品格式

### 3. 获取推荐商品
- **URL**: `GET /products/recommend`
- **描述**: 获取推荐商品列表
- **响应示例**: 同热门商品格式

### 4. 获取商品详情
- **URL**: `GET /products/{id}`
- **参数**: 
  - `id`: 商品ID
- **响应示例**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "id": 1,
    "title": "iPhone 15 Pro",
    "desc": "苹果最新旗舰手机，搭载A17 Pro芯片",
    "price": "¥7999.00",
    "img": "https://example.com/iphone15.jpg",
    "platforms": ["京东"],
    "priceChange": -5.2,
    "brand": "Apple",
    "category": "手机",
    "rating": 4.8,
    "reviewCount": 1250,
    "salesCount": 5000,
    "isHot": true,
    "isRecommend": true
  }
}
```

### 5. 搜索商品
- **URL**: `POST /products/search`
- **请求体**:
```json
{
  "keyword": "iPhone",
  "category": "手机",
  "brand": "Apple",
  "platform": "京东",
  "minPrice": 5000,
  "maxPrice": 10000,
  "isHot": true,
  "isRecommend": false,
  "page": 1,
  "size": 20,
  "sortBy": "price",
  "sortOrder": "asc"
}
```
- **响应示例**:
```json
{
  "code": 0,
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

### 6. 根据分类获取商品
- **URL**: `GET /products/category/{category}`
- **参数**:
  - `category`: 商品分类
  - `page`: 页码（默认1）
  - `size`: 每页大小（默认20）

### 7. 根据品牌获取商品
- **URL**: `GET /products/brand/{brand}`
- **参数**:
  - `brand`: 品牌名称
  - `page`: 页码（默认1）
  - `size`: 每页大小（默认20）

## 价格相关接口

### 8. 获取价格历史
- **URL**: `GET /products/{id}/price-history`
- **参数**: 
  - `id`: 商品ID
- **响应示例**:
```json
{
  "code": 0,
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

### 9. 获取价格趋势图表数据
- **URL**: `GET /products/{id}/price-trend`
- **参数**: 
  - `id`: 商品ID
- **响应示例**:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "productName": "某品牌旗舰手机",
    "labels": ["1日", "5日", "10日", "15日", "20日", "25日", "30日"],
    "comparisonData": [
      {
        "label": "京东",
        "data": [3499, 3399, 3349, 3299, 3399, 3299, 3299],
        "borderColor": "#e74c3c",
        "borderWidth": 3,
        "tension": 0.3
      }
    ],
    "fluctuationLabels": ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
    "fluctuationValues": [3899, 3799, 3699, 3599, 3499, 3399, 3499, 3399, 3299, 3199, 3299, 3299]
  }
}
```

## 用户收藏相关接口

### 10. 获取用户收藏列表
- **URL**: `GET /user/favorites`
- **参数**: 
  - `userId`: 用户ID（默认1）
- **响应示例**:
```json
{
  "code": 0,
  "message": "success",
  "data": [
    {
      "id": 1,
      "productId": 1,
      "title": "iPhone 15 Pro",
      "price": "¥7999.00",
      "img": "https://example.com/iphone15.jpg",
      "priceChange": -5.2,
      "alertPrice": 7500.00,
      "createTime": "2024-01-01T10:00:00"
    }
  ]
}
```

### 11. 添加商品到收藏
- **URL**: `POST /user/favorites`
- **参数**: 
  - `userId`: 用户ID
  - `productId`: 商品ID

### 12. 从收藏夹删除商品
- **URL**: `DELETE /user/favorites`
- **参数**: 
  - `userId`: 用户ID
  - `productId`: 商品ID

### 13. 设置价格提醒
- **URL**: `POST /user/price-alert`
- **参数**: 
  - `userId`: 用户ID
  - `productId`: 商品ID
  - `alertPrice`: 提醒价格

### 14. 更新提醒价格
- **URL**: `PUT /user/favorites/{favoriteId}/alert-price`
- **参数**: 
  - `favoriteId`: 收藏记录ID
  - `alertPrice`: 新的提醒价格

### 15. 检查用户是否已收藏某商品
- **URL**: `GET /user/favorites/check`
- **参数**: 
  - `userId`: 用户ID
  - `productId`: 商品ID
- **响应示例**:
```json
{
  "code": 0,
  "message": "success",
  "data": true
}
```

## 错误码说明
- `0`: 成功
- `500`: 服务器内部错误
- 其他错误码根据具体业务定义

## 注意事项
1. 所有接口都支持跨域访问
2. 价格相关的图表数据目前使用模拟数据
3. 用户ID目前使用默认值1，实际项目中应该从JWT token中获取
4. 分页参数从1开始计数
5. 价格精度为2位小数 