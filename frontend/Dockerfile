# --- Build Stage ---
# 使用官方 Node.js 镜像作为构建环境
FROM node:18-alpine as build

# 设置工作目录
WORKDIR /app

# 复制 package.json 和 package-lock.json
COPY package.json package-lock.json ./

# 安装项目依赖
RUN npm install

# 复制所有前端代码到工作目录
COPY . .

# 构建生产版本的应用
RUN npm run build

# --- Production Stage ---
# 使用轻量级的 Nginx 镜像作为生产环境
FROM nginx:1.25-alpine

# 将构建好的静态文件从构建阶段复制到 Nginx 的默认服务目录
COPY --from=build /app/dist /usr/share/nginx/html

# 暴露 80 端口
EXPOSE 8066

# 启动 Nginx 服务
CMD ["nginx", "-g", "daemon off;"]