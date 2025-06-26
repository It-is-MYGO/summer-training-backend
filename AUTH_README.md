# Auth 模块使用说明

## 功能特性

- 用户注册和登录
- JWT Token 认证
- 角色权限控制
- 短信验证码服务
- Redis 缓存支持

## API 接口

### 认证相关接口

#### 1. 用户注册
```
POST /api/auth/register
Content-Type: application/json

{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
}
```

#### 2. 用户登录
```
POST /api/auth/login
Content-Type: application/json

{
    "username": "testuser",
    "password": "password123"
}
```

#### 3. 获取当前用户信息
```
GET /api/auth/me
Authorization: Bearer <jwt_token>
```

#### 4. 退出登录
```
POST /api/auth/logout
Authorization: Bearer <jwt_token>
```

### 短信验证码接口

#### 1. 发送验证码
```
POST /api/sms/send?phone=13800138000
```

#### 2. 验证验证码
```
POST /api/sms/verify?phone=13800138000&code=123456
```

### 测试接口

#### 1. 公开端点
```
GET /api/test/public
```

#### 2. 用户专用端点
```
GET /api/test/user
Authorization: Bearer <jwt_token>
```

#### 3. 管理员专用端点
```
GET /api/test/admin
Authorization: Bearer <jwt_token>
```

## 配置说明

### 数据库配置
在 `application.yml` 中配置数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database
    username: your_username
    password: your_password
```

### Redis 配置
```yaml
spring:
  redis:
    host: localhost
    port: 6379
    password: 
    database: 0
```

### JWT 配置
```yaml
app:
  jwt:
    secret: your_jwt_secret_key_here_32_characters_long
    expiration-ms: 86400000 # 24小时
```

## 使用步骤

1. 启动 MySQL 和 Redis 服务
2. 修改 `application.yml` 中的数据库和 Redis 配置
3. 启动 Spring Boot 应用
4. 使用 API 接口进行测试

## 角色权限

- `ROLE_USER`: 普通用户权限
- `ROLE_ADMIN`: 管理员权限

## 注意事项

1. JWT Token 需要在请求头中添加：`Authorization: Bearer <token>`
2. 密码会自动使用 BCrypt 加密
3. 短信验证码目前是模拟发送，实际使用时需要集成真实的短信服务
4. 角色数据会在应用启动时自动初始化 