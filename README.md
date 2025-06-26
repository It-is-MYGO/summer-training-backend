# summer-training-backend
后端架构：
price-comparison/
├── app.js                  # 主入口文件
├── config/                 # 配置文件
│   ├── database.js         # 数据库配置
│   ├── redis.js            # Redis配置
│   ├── middleware.js       # 中间件配置
│   └── constants.js        # 常量定义
├── modules/                # 功能模块
│   ├── auth/               # 认证模块
│   ├── user/               # 用户模块
│   ├── product/            # 商品模块
│   ├── price/              # 比价模块
│   ├── crawler/            # 爬虫模块
│   ├── prediction/         # 价格预测模块
│   ├── recommendation/     # 推荐模块
│   ├── community/          # 社区动态模块
│   └── admin/              # 管理后台模块
├── lib/                    # 公共库
│   ├── database/           # 数据库封装
│   ├── cache/              # 缓存封装
│   ├── utils/              # 工具函数
│   └── middleware/         # 公共中间件
├── jobs/                   # 后台任务
│   ├── crawler/            # 爬虫任务
│   └── data-processing/    # 数据处理任务
├── tests/                  # 测试代码
└── public/                 # 静态文件

modules/product/
├── controllers/            # 控制器
│   ├── product.controller.js
│   └── price.controller.js
├── services/               # 服务层
│   ├── product.service.js
│   └── price.service.js
├── repositories/           # 数据访问层
│   ├── product.repository.js
│   └── price.repository.js
├── models/                 # 数据模型
│   ├── product.model.js
│   └── price-history.model.js
├── validators/             # 验证器
│   └── product.validator.js
├── routes/                 # 路由定义
│   └── product.routes.js
└── tests/                  # 模块测试
