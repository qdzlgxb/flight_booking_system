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
   - 访问 `http://<ip>:80/register`
   - 填写用户名、邮箱、姓名和密码
   - 点击注册按钮

2. **登录系统**
   - 访问 `http://<ip>:80/login`
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
   - 访问 `http://<ip>:80/bookings`
   - 查看历史订单
   - 查看订单状态和详情
   

### 管理员操作流程

1. **登录管理员账户**
   - 使用管理员账户登录
   - 访问 `http://<ip>:80/admin`

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

## 部署说明（WSL-Docker）

*建议将WSL配置为版本2，并且使用镜像网络模式*

1. 从项目Release页面下载最新版本的TAR镜像包

2. 创建`.env`环境变量文件，根据实际情况修改

   ```bash
   # ------------------ 数据库配置 ------------------
   # 数据库名
   MYSQL_DATABASE=JavaFullStackTraining
   
   # 数据库用户名
   MYSQL_USER=JavaFullStackTrainingAdmin
   
   # 数据库用户密码
   MYSQL_PASSWORD=demopassword123
   
   # 数据库 root 用户密码
   MYSQL_ROOT_PASSWORD=demorootpassword123
   
   # ------------------ 后端应用配置 ------------------
   # JWT 密钥
   JWT_SECRET=a_very_long_and_secure_secret_for_demonstration_purposes_only_123456
   
   # JWT 过期时间 (24小时)
   JWT_EXPIRATION=86400000
   
   # 后端服务端口
   SERVER_PORT=8080
   ```

3. 创建`docker-compose.yml`文件，根据实际情况修改，确保与.env处在同一路径中

   该compose已经包含了mysql镜像，如需使用单独部署的数据库，可以将compose中mysql配置的部分删除，另行配置

   adminer是数据库可视化管理工具，根据实际需求选择是否安装

   volumes和端口号需要注意命名冲突

   ```yml
   version: '3.8'
   
   services:
     db:
       image: mysql:8.0
       container_name: mysql-db
       restart: unless-stopped
       environment:
         MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD}
         MYSQL_DATABASE: ${MYSQL_DATABASE}
         MYSQL_USER: ${MYSQL_USER}
         MYSQL_PASSWORD: ${MYSQL_PASSWORD}
       volumes:
         - mysql-data:/var/lib/mysql
       ports:
         - "3306:3306"
   
     backend:
       image: backend-app:v1.0.0
       container_name: flight-booking-backend
       restart: unless-stopped
       depends_on:
         - db
       ports:
         - "8080:8080"
       environment:
         DB_URL: jdbc:mysql://db:3306/${MYSQL_DATABASE}
         DB_USERNAME: ${MYSQL_USER}
         DB_PASSWORD: ${MYSQL_PASSWORD}
         JWT_SECRET: ${JWT_SECRET}
         JWT_EXPIRATION: ${JWT_EXPIRATION}
         SERVER_PORT: ${SERVER_PORT}
   
     frontend:
       image: frontend-app:v1.0.0
       container_name: flight-booking-frontend
       restart: unless-stopped
       ports:
         - "80:80"
       depends_on:
         - backend
   
     adminer:
       image: adminer
       container_name: adminer
       restart: unless-stopped
       ports:
         - "8088:8080"
       depends_on:
         - db
   
   volumes:
     mysql-data:
   ```

4. 在下载的TAR镜像包所在路径下运行以下指令，该指令会将镜像解压后导入docker，注意文件名要修改成实际文件名

   ```bash
   docker load -i images-v1.x.x.tar
   ```

5. 在`docker-compose.yml`所在路径中运行docker compose

   ```bash
   sudo docker compose up -d
   ```

6. 此时访问`http://<you ip>:80`将进入到前端首页
