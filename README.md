# School Mall - 校园电商平台（后端）

基于 Spring Boot 3 + MyBatis 的现代化电商平台后端系统。支持完整的电商业务流程，包括用户认证、商品管理、订单处理、物流追踪、购物车、收藏、评论等功能。

## ✨ 特性

### 核心功能
- 🔐 **用户认证系统**：手机验证码登录/注册，JWT Token 鉴权
- 🛍️ **商品管理**：商品 CRUD、多款式支持、分页搜索、评价系统
- 🛒 **购物车系统**：添加/删除商品、数量调整、批量选择
- ❤️ **收藏功能**：商品收藏/取消、收藏列表管理
- 📦 **订单系统**：下单、支付模拟、订单状态管理、地址快照
- 🚚 **物流追踪**：物流记录创建、状态更新、物流查询
- 💬 **评价系统**：商品评价、匿名评价、评价管理
- 📊 **统计功能**：用户/商品/订单统计、数据报表
- 👨‍💼 **管理后台**：商品管理、订单管理、用户管理、发货管理
- 📁 **文件上传**：图片上传、多文件上传、文件类型验证

### 技术特色
- 现代化架构设计，分层清晰
- RESTful API 设计规范
- 完善的异常处理机制
- 统一的响应格式
- CORS 跨域支持
- SQL 注入防护（参数白名单）
- 事务管理
- 自动化时间戳管理

## 🛠️ 技术栈

- **框架**：Spring Boot 3.5.5
- **语言**：Java 17
- **持久层**：MyBatis 3.0.5 + MySQL 8
- **安全**：JWT（java-jwt 4.4.0）
- **验证**：Bean Validation
- **工具**：Lombok
- **构建**：Maven

## 📁 项目结构

```
src/main/java/com/school_mall/
├── entity/              # 实体类
│   ├── BaseEntity.java
│   ├── User.java
│   ├── Product.java
│   ├── Order.java
│   ├── Cart.java
│   ├── Favorite.java
│   └── ...
├── repository/          # MyBatis Mapper 接口
│   ├── BaseMapper.java
│   ├── UserMapper.java
│   ├── ProductMapper.java
│   └── ...
├── service/             # 业务逻辑层
│   ├── BaseService.java
│   ├── BaseServiceImpl.java
│   ├── UserService.java
│   └── ...
├── web/                 # Web 层
│   ├── dto/            # 数据传输对象
│   ├── AuthController.java
│   ├── ProductController.java
│   ├── OrderController.java
│   ├── CartController.java
│   ├── AdminProductController.java
│   └── ...
└── security/           # 安全相关
    ├── JwtUtil.java
    ├── AuthInterceptor.java
    ├── UserContext.java
    └── WebConfig.java

src/main/resources/
├── mappers/            # MyBatis XML 映射文件
├── db/
│   ├── schema.sql     # 数据库结构
│   └── test-data.sql  # 测试数据
└── application.yml    # 配置文件
```

## 📊 数据模型

### 核心表
- **user** - 用户表
- **login_code** - 登录验证码
- **user_address** - 收货地址
- **product** - 商品表
- **product_style** - 商品款式
- **product_comment** - 商品评价
- **order** - 订单主表（含地址快照）
- **order_item** - 订单明细
- **logistics** - 物流信息
- **cart** - 购物车
- **favorite** - 收藏/心愿单

## 🚀 快速开始

### 1. 环境要求
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库初始化
```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE school_mall DEFAULT CHARACTER SET utf8mb4;

# 执行建表脚本
mysql -u root -p school_mall < src/main/resources/db/schema.sql

# （可选）导入测试数据
mysql -u root -p school_mall < src/main/resources/db/test-data.sql
```

### 3. 配置
编辑 `src/main/resources/application.yml`，配置数据库连接：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/school_mall?useSSL=false&serverTimezone=UTC&characterEncoding=utf-8
    username: your_username
    password: your_password

jwt:
  secret: "your_secret_key_here"
  expireMinutes: 1440
```

### 4. 构建运行
```bash
# 编译打包
mvn clean package -DskipTests

# 运行应用
java -jar target/ecommerce-0.0.1-SNAPSHOT.jar

# 或直接运行
mvn spring-boot:run
```

应用将在 http://localhost:8080 启动

## 📡 API 接口

### 认证相关
```http
POST /api/auth/send-code      # 发送验证码
POST /api/auth/login          # 登录/注册
```

### 用户相关
```http
GET    /api/users/{userId}                    # 获取用户信息
PUT    /api/users/{userId}                    # 更新用户信息
GET    /api/users/{userId}/addresses          # 获取用户地址列表
POST   /api/users/{userId}/addresses          # 添加地址
PUT    /api/users/{userId}/addresses/{id}     # 更新地址
DELETE /api/users/{userId}/addresses/{id}     # 删除地址
PUT    /api/users/{userId}/addresses/{id}/default  # 设为默认地址
```

### 商品相关
```http
GET    /api/products                          # 获取商品列表
GET    /api/products/paged                    # 分页获取商品
GET    /api/products/{productId}              # 获取商品详情
GET    /api/products/{productId}/styles       # 获取商品款式
GET    /api/products/{productId}/comments     # 获取商品评价
POST   /api/products/{productId}/comments     # 添加评价
DELETE /api/products/{productId}/comments/{commentId}  # 删除评价
```

### 购物车相关
```http
GET    /api/cart                    # 获取购物车
GET    /api/cart/count              # 获取购物车数量
POST   /api/cart/add                # 添加到购物车
PUT    /api/cart/{cartId}/quantity  # 更新数量
PUT    /api/cart/{cartId}/select    # 选择/取消选择
PUT    /api/cart/select-all         # 全选
PUT    /api/cart/unselect-all       # 取消全选
DELETE /api/cart/{cartId}           # 删除购物车项
DELETE /api/cart/selected           # 删除选中项
DELETE /api/cart/clear              # 清空购物车
```

### 收藏相关
```http
GET    /api/favorites                    # 获取收藏列表
GET    /api/favorites/count              # 获取收藏数量
GET    /api/favorites/check/{productId}  # 检查是否收藏
POST   /api/favorites                    # 添加收藏
POST   /api/favorites/toggle             # 切换收藏状态
DELETE /api/favorites/{productId}        # 取消收藏
DELETE /api/favorites/clear              # 清空收藏
```

### 订单相关
```http
GET    /api/orders                  # 获取订单列表
GET    /api/orders/{orderId}        # 获取订单详情
POST   /api/orders                  # 创建订单
POST   /api/orders/{orderId}/pay    # 支付订单
```

### 物流相关
```http
GET    /api/logistics/order/{orderId}  # 获取订单物流
GET    /api/logistics/{no}             # 根据物流单号查询
```

### 管理后台
```http
# 商品管理
POST   /api/admin/products                     # 创建商品
PUT    /api/admin/products/{id}                # 更新商品
DELETE /api/admin/products/{id}                # 删除商品
DELETE /api/admin/products/batch               # 批量删除
POST   /api/admin/products/{id}/styles         # 添加款式
DELETE /api/admin/products/{id}/styles/{name}  # 删除款式

# 订单管理
GET    /api/admin/orders                           # 获取所有订单
GET    /api/admin/orders/status/{status}          # 按状态查询
PUT    /api/admin/orders/{orderId}/status         # 更新订单状态
POST   /api/admin/orders/{orderId}/ship           # 发货
PUT    /api/admin/orders/{orderId}/cancel         # 取消订单
PUT    /api/admin/orders/{orderId}/complete       # 完成订单
PUT    /api/admin/logistics/{orderId}/status      # 更新物流状态

# 用户管理
GET    /api/admin/users              # 获取所有用户
GET    /api/admin/users/{userId}     # 获取用户详情
GET    /api/admin/users/search       # 搜索用户
PUT    /api/admin/users/{userId}     # 更新用户
DELETE /api/admin/users/{userId}     # 删除用户
GET    /api/admin/users/count        # 获取用户数量
```

### 统计相关
```http
GET    /api/statistics/overview           # 总体统计
GET    /api/statistics/products/{id}      # 商品统计
GET    /api/statistics/users/{id}         # 用户统计
GET    /api/statistics/orders/by-status   # 订单状态统计
```

### 文件上传
```http
POST   /api/upload/image   # 上传单个图片
POST   /api/upload/images  # 上传多个图片
DELETE /api/upload         # 删除文件
```

## 🔐 认证说明

### JWT 认证流程
1. 发送验证码：`POST /api/auth/send-code { "phone": "13800138000" }`
2. 登录获取 Token：`POST /api/auth/login { "phone": "13800138000", "code": "123456" }`
3. 后续请求携带 Token：`Authorization: Bearer {token}`

### 免认证接口
- `/api/auth/**` - 认证相关
- `/api/products/**` - 商品浏览（GET）
- `/api/public/**` - 公开接口
- `/uploads/**` - 静态资源

## 📝 请求示例

### 创建订单
```json
POST /api/orders
{
  "addressId": 1,
  "items": [
    {
      "productId": 1,
      "styleId": 1,
      "count": 2
    },
    {
      "productId": 2,
      "count": 1
    }
  ]
}
```

### 添加商品评价
```json
POST /api/products/1/comments
{
  "commentContent": "商品很好，物流快",
  "commentImg": "http://example.com/image.jpg",
  "isAnonymous": 0
}
```

### 创建商品（管理员）
```json
POST /api/admin/products
{
  "shopType": 0,
  "productName": "iPhone 15 Pro",
  "originalPrice": 9999.00,
  "discountPrice": 8999.00,
  "productImg": "http://example.com/iphone.jpg",
  "qualityCheck": 0,
  "styles": ["黑色 256GB", "白色 512GB"]
}
```

## 🏗️ 架构设计

### 分层架构
```
Controller（Web层）
    ↓ 调用
Service（业务逻辑层）
    ↓ 调用
Repository（数据访问层）
    ↓ 操作
Database（数据库）
```

### 设计亮点
- **通用 CRUD**：`BaseServiceImpl` 提供通用 CRUD 操作
- **地址快照**：订单创建时保存地址快照，避免后续修改影响历史订单
- **订单主从**：`order` + `order_item` 支持一单多商品
- **JWT 上下文**：`UserContext` 存储当前用户信息
- **全局异常**：统一异常处理，返回标准响应格式
- **参数校验**：Bean Validation + 自定义校验
- **SQL 安全**：排序字段白名单，防止 SQL 注入
- **事务管理**：关键业务使用 `@Transactional`

## 📈 未来扩展

- [ ] Swagger/OpenAPI 文档
- [ ] Redis 缓存
- [ ] 消息队列（订单异步处理）
- [ ] Elasticsearch 商品搜索
- [ ] 真实支付集成
- [ ] 库存管理
- [ ] 优惠券系统
- [ ] 秒杀活动
- [ ] 数据统计报表
- [ ] 单元测试与集成测试

## 📄 许可证

本项目仅供学习交流使用。

## 👥 贡献

欢迎提交 Issue 和 Pull Request！ 