# 商品比价系统 - 商品模块

## 概述

这是商品比价系统的商品模块，负责处理商品相关的所有功能，包括商品展示、搜索、价格监控、用户收藏等。

## 功能特性

### 商品管理
- ✅ 商品列表展示（热门、推荐、降价商品）
- ✅ 商品详情查看
- ✅ 商品搜索（关键词、分类、品牌、价格范围）
- ✅ 商品分类和品牌筛选
- ✅ 分页和排序功能

### 价格监控
- ✅ 价格历史记录
- ✅ 价格趋势图表
- ✅ 多平台价格对比
- ✅ 价格波动统计

### 用户功能
- ✅ 商品收藏
- ✅ 价格提醒设置
- ✅ 收藏夹管理
- ✅ 收藏状态检查

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 3.5.3
- **数据库**: MySQL 8.0
- **ORM**: Spring Data JPA
- **缓存**: Redis
- **安全**: Spring Security + JWT
- **构建工具**: Maven

### 项目结构
```
modules/product/
├── controller/           # 控制器层
│   ├── ProductController.java    # 商品相关接口
│   └── UserController.java       # 用户收藏接口
├── service/             # 服务层
│   ├── ProductService.java       # 商品服务接口
│   ├── UserService.java          # 用户服务接口
│   └── impl/                     # 服务实现
│       ├── ProductServiceImpl.java
│       └── UserServiceImpl.java
├── entity/              # 实体类
│   ├── Product.java             # 商品实体
│   ├── PriceHistory.java        # 价格历史实体
│   └── UserFavorite.java        # 用户收藏实体
├── dto/                 # 数据传输对象
│   ├── ProductDTO.java          # 商品DTO
│   ├── ProductQuery.java        # 查询参数DTO
│   ├── PriceHistoryDTO.java     # 价格历史DTO
│   ├── PriceTrendChartDTO.java  # 价格趋势图表DTO
│   └── UserFavoriteDTO.java     # 用户收藏DTO
└── repository/          # 数据访问层
    ├── ProductRepository.java    # 商品数据访问
    └── UserFavoriteRepository.java # 用户收藏数据访问
```

## 快速开始

### 1. 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Maven 3.6+

### 2. 数据库配置
确保MySQL服务已启动，并创建数据库：
```sql
CREATE DATABASE your_database CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置文件
修改 `application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8
    username: your_username
    password: your_password
```

### 4. 启动应用
```bash
cd springboot-project/demo
mvn spring-boot:run
```

### 5. 测试接口
```bash
# 运行测试脚本
./test_product.sh

# 或者手动测试
curl -X GET "http://localhost:8080/api/products/hot"
```

## API 接口

### 商品接口
- `GET /api/products/hot` - 获取热门商品
- `GET /api/products/discount` - 获取降价商品
- `GET /api/products/recommend` - 获取推荐商品
- `GET /api/products/{id}` - 获取商品详情
- `POST /api/products/search` - 搜索商品
- `GET /api/products/{id}/price-history` - 获取价格历史
- `GET /api/products/{id}/price-trend` - 获取价格趋势图表

### 用户接口
- `GET /api/user/favorites` - 获取收藏列表
- `POST /api/user/favorites` - 添加收藏
- `DELETE /api/user/favorites` - 删除收藏
- `POST /api/user/price-alert` - 设置价格提醒
- `PUT /api/user/favorites/{id}/alert-price` - 更新提醒价格

详细API文档请参考 [PRODUCT_API.md](./PRODUCT_API.md)

## 数据库表结构

### products 表
```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    brand VARCHAR(100),
    category VARCHAR(100),
    image_url VARCHAR(500),
    source_url VARCHAR(200),
    platform VARCHAR(100),
    rating DECIMAL(3,1),
    review_count INT,
    sales_count INT,
    is_hot BOOLEAN DEFAULT FALSE,
    is_recommend BOOLEAN DEFAULT FALSE,
    create_time DATETIME,
    update_time DATETIME
);
```

### user_favorites 表
```sql
CREATE TABLE user_favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    alert_price DECIMAL(10,2),
    create_time DATETIME,
    update_time DATETIME
);
```

### price_history 表
```sql
CREATE TABLE price_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    date DATE NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    platform VARCHAR(100),
    create_time DATE
);
```

## 开发说明

### 添加新功能
1. 在 `entity` 包中创建实体类
2. 在 `dto` 包中创建对应的DTO类
3. 在 `repository` 包中创建数据访问接口
4. 在 `service` 包中创建服务接口和实现
5. 在 `controller` 包中创建控制器

### 数据验证
- 使用 `@Valid` 注解进行参数验证
- 在DTO类中使用 `@NotNull`、`@Size` 等注解

### 异常处理
- 统一使用 `Result.error()` 返回错误信息
- 在Service层抛出具体的业务异常
- 在Controller层捕获异常并返回统一格式

### 日志记录
- 使用 `@Slf4j` 注解添加日志
- 记录关键业务操作和异常信息

## 部署说明

### 生产环境配置
1. 修改数据库连接为生产环境配置
2. 配置Redis连接信息
3. 设置JWT密钥
4. 配置跨域白名单
5. 启用HTTPS

### 性能优化
1. 添加Redis缓存
2. 配置数据库连接池
3. 添加数据库索引
4. 使用分页查询
5. 配置CDN加速静态资源

## 常见问题

### Q: 如何添加新的商品分类？
A: 直接在数据库的 `products` 表中插入数据，`category` 字段会自动识别。

### Q: 价格历史数据如何更新？
A: 目前使用模拟数据，实际项目中需要定时任务从各电商平台抓取价格数据。

### Q: 用户认证如何集成？
A: 当前使用默认用户ID=1，实际项目中需要集成JWT认证，从token中获取用户信息。

### Q: 如何扩展多平台支持？
A: 在 `Product` 实体中添加更多平台字段，或在 `PriceHistory` 表中记录不同平台的价格。

## 贡献指南

1. Fork 项目
2. 创建功能分支
3. 提交代码
4. 创建 Pull Request

## 许可证

MIT License 