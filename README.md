# 航班预订系统 Flight Booking System

## 项目概述

航班预订系统是一个基于Spring Boot + React的全栈Web应用，为用户提供完整的航班搜索、预订和管理功能。系统支持用户注册登录、航班查询、在线预订、订单管理，以及管理员后台对机场、航班和订单的全面管理。

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 3.5.0
- **数据库**: MySQL + JPA/Hibernate
- **安全**: Spring Security + JWT认证
- **构建工具**: Maven
- **Java版本**: Java 17

### 前端技术栈
- **框架**: React 19.1.0 + TypeScript
- **路由**: React Router DOM 7.6.2
- **HTTP客户端**: Axios 1.9.0
- **构建工具**: Vite 6.3.5
- **开发工具**: ESLint + TypeScript

### 核心功能

#### 用户功能
- **用户注册/登录**: 基于JWT的安全认证系统
- **航班搜索**: 支持按航班号、出发地、目的地搜索
- **航班预订**: 在线预订航班，自动生成订单编号
- **订单管理**: 查看个人历史订单和订单状态
- **个人信息**: 管理用户个人资料

#### 管理员功能
- **机场管理**: 添加、删除机场信息
- **航班管理**: 创建、删除航班信息
- **订单管理**: 查看所有用户订单
- **乘客管理**: 查看特定航班的乘客信息

## 系统架构

```
┌─────────────────┐    HTTP/REST API    ┌─────────────────┐
│   React前端     │ ◄─────────────────► │  Spring Boot    │
│   (Port 5173)   │                     │  后端API服务     │
└─────────────────┘                     │   (Port 8080)   │
                                        └─────────────────┘
                                                 │
                                                 ▼
                                        ┌─────────────────┐
                                        │   MySQL数据库   │
                                        │   (Port 3306)   │
                                        └─────────────────┘
```

## 数据库设计

### 核心数据表
- **users**: 用户信息表(id, username, password, email, fullName, role)
- **AIRPORT**: 机场信息表(id, code, name, city)
- **FLIGHT**: 航班信息表(id, flightNumber, departureAirport, destinationAirport, departureDate, departureTime, arrivalTime, price)
- **BOOKING**: 订单表(id, user_id, flight_id, reference, status, bookingTime, totalPrice)
- **PASSENGER**: 乘客信息表(id, firstName, lastName, email)

## 安装与配置

### 环境要求
- Java 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.6+

### 后端配置

1. **数据库配置**
```bash
# 创建数据库
mysql -u root -p
CREATE DATABASE JavaFullStackTraining;
```

2. **配置文件**
修改 `backend/src/main/resources/application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/JavaFullStackTraining
    username: JavaFullStackTrainingAdmin
    password: 8xchWheZpKiiQxnX
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

server:
  port: 8080

jwt:
  secret: mySecretKey123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890
  expiration: 86400000 # 24小时
```

3. **启动后端服务**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### 前端配置

1. **安装依赖**
```bash
cd frontend
npm install
```

2. **启动开发服务器**
```bash
npm run dev
```

3. **构建生产版本**
```bash
npm run build
```

## API接口文档

### 认证接口
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/register` - 用户注册

### 航班接口
- `POST /api/flights/search` - 搜索航班
- `GET /api/flights/{id}` - 获取航班详情

### 机场接口
- `GET /api/airports` - 获取所有机场
- `GET /api/airports/{id}` - 获取机场详情

### 订单接口
- `POST /api/bookings` - 创建预订 (需要认证)
- `GET /api/bookings/my` - 获取用户订单 (需要认证)

### 管理员接口
- `POST /api/admin/airports` - 创建机场 (需要管理员权限)
- `DELETE /api/admin/airports/{id}` - 删除机场 (需要管理员权限)
- `GET /api/admin/flights` - 获取所有航班 (需要管理员权限)
- `POST /api/admin/flights` - 创建航班 (需要管理员权限)
- `DELETE /api/admin/flights/{id}` - 删除航班 (需要管理员权限)
- `GET /api/admin/bookings` - 获取所有订单 (需要管理员权限)
- `GET /api/admin/flights/{id}/passengers` - 获取航班乘客 (需要管理员权限)

## 使用指南

### 用户操作流程

1. **注册账户**
   - 访问 `http://localhost:5173/register`
   - 填写用户名、邮箱、姓名和密码
   - 点击注册按钮

2. **登录系统**
   - 访问 `http://localhost:5173/login`
   - 输入用户名和密码
   - 登录成功后跳转到首页

3. **搜索航班**
   - 在首页搜索框中输入航班号、出发地或目的地
   - 点击搜索查看可用航班
   - 选择合适的航班

4. **预订航班**
   - 点击"预订"按钮
   - 填写乘客信息（姓名、邮箱）
   - 确认预订信息
   - 完成支付流程

5. **管理订单**
   - 访问 `http://localhost:5173/bookings`
   - 查看历史订单
   - 查看订单状态和详情
   

### 管理员操作流程

1. **登录管理员账户**
   - 使用管理员账户登录
   - 访问 `http://localhost:5173/admin`

2. **机场管理**
   - 添加新机场（机场代码、名称、城市）
   - 删除不需要的机场

3. **航班管理**
   - 创建新航班（航班号、出发/到达机场、时间、价格）
   - 删除已取消的航班

4. **订单管理**
   - 查看所有用户订单
   - 查看特定航班的乘客列表
   - 管理订单状态

## 安全特性

- **JWT认证**: 所有API请求都需要有效的JWT token
- **角色权限**: 区分普通用户和管理员权限
- **密码加密**: 使用Spring Security进行密码加密
- **CORS配置**: 支持跨域请求
- **请求验证**: 所有输入参数都经过验证

## 开发指南

### 后端开发

1. **添加新的API端点**
   - 在相应的Controller中添加新方法
   - 在Service层实现业务逻辑
   - 在Repository层处理数据访问

2. **数据模型修改**
   - 修改Entity类
   - 更新数据库迁移脚本
   - 相应更新DTO类

3. **安全配置**
   - 在`WebSecurityConfig`中配置访问权限
   - 使用`@PreAuthorize`注解进行方法级权限控制

### 前端开发

1. **添加新页面**
   - 在`components`目录创建新组件
   - 在`App.tsx`中添加路由配置
   - 更新导航菜单

2. **API集成**
   - 在`services/api.ts`中添加API调用方法
   - 在`types/index.ts`中定义数据类型
   - 在组件中使用API服务

3. **状态管理**
   - 使用React Context进行全局状态管理
   - 在`contexts/AuthContext.tsx`中管理用户认证状态

## 部署说明

### 生产环境部署

1. **后端部署**
```bash
# 构建JAR包
mvn clean package -DskipTests

# 运行应用
java -jar target/flight-booking-backend-1.0.0.jar
```

2. **前端部署**
```bash
# 构建生产版本
npm run build

# 使用nginx等Web服务器部署dist目录
```

3. **数据库配置**
   - 配置生产环境MySQL数据库
   - 更新application.yml中的数据库连接信息
   - 运行数据库迁移脚本

### 环境变量配置

```bash
# 后端环境变量
export DB_URL=jdbc:mysql://localhost:3306/JavaFullStackTraining
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
export SERVER_PORT=8080

# 前端环境变量
export VITE_API_BASE_URL=http://localhost:8080/api
```

## 故障排除

### 常见问题

1. **数据库连接失败**
   - 检查MySQL服务是否启动
   - 验证数据库连接参数
   - 确认数据库权限配置

2. **JWT认证失败**
   - 检查JWT密钥配置
   - 确认token是否过期
   - 验证请求头格式

3. **跨域请求问题**
   - 检查后端CORS配置
   - 确认前端API基础URL设置
   - 验证请求方法和头部

4. **前端构建错误**
   - 检查Node.js和npm版本
   - 清理node_modules重新安装
   - 验证TypeScript配置

