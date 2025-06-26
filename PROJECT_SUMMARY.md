# Auth 模块项目总结

## 项目概述

这是一个基于 Spring Boot 3.x 的完整认证授权模块，提供了用户注册、登录、JWT Token 认证、角色权限控制等功能。

## 技术栈

- **Spring Boot 3.5.3**: 主框架
- **Spring Security**: 安全框架
- **Spring Data JPA**: 数据访问层
- **MySQL**: 数据库
- **Redis**: 缓存和会话存储
- **JWT**: 无状态认证
- **Maven**: 项目管理工具
- **Lombok**: 代码简化工具

## 核心功能

### 1. 用户认证
- 用户注册：支持用户名、邮箱、密码注册
- 用户登录：支持用户名密码登录
- JWT Token：生成和验证JWT令牌
- 密码加密：使用BCrypt加密存储

### 2. 权限控制
- 角色管理：支持USER和ADMIN角色
- 方法级安全：使用@PreAuthorize注解
- URL级安全：配置不同端点的访问权限

### 3. 短信服务
- 验证码发送：生成并发送短信验证码
- 验证码验证：验证用户输入的验证码
- Redis缓存：存储验证码并设置过期时间

## API接口

### 认证接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/auth/me` - 获取当前用户信息
- `POST /api/auth/logout` - 退出登录

### 短信接口
- `POST /api/sms/send` - 发送验证码
- `POST /api/sms/verify` - 验证验证码

### 测试接口
- `GET /api/test/public` - 公开端点
- `GET /api/test/user` - 用户专用端点
- `GET /api/test/admin` - 管理员专用端点

## 部署说明

### 环境要求
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 启动步骤
1. 启动MySQL和Redis服务
2. 修改application.yml配置
3. 运行 `./start.sh` 启动应用
4. 运行 `./test_auth.sh` 测试功能

## 注意事项

1. 生产环境中请修改JWT密钥
2. 确保数据库和Redis的安全配置
3. 定期更新依赖版本以修复安全漏洞
4. 建议添加API文档（如Swagger）
5. 考虑添加监控和健康检查 