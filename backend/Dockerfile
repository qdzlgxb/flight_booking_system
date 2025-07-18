# --- Build Stage ---
# 使用 Maven 镜像来构建项目
FROM maven:3.8.5-openjdk-17 AS build

# 设置工作目录
WORKDIR /app

# 复制 pom.xml 文件
COPY pom.xml .

# 下载所有依赖项
RUN mvn dependency:go-offline

# 复制源代码
COPY src ./src

# 打包应用
RUN mvn clean install -DskipTests

# --- Production Stage ---
# 使用轻量级的 Java 运行环境
FROM openjdk:17-jdk-slim

# 设置工作目录
WORKDIR /app

# 从构建阶段复制打包好的 JAR 文件
COPY --from=build /app/target/*.jar app.jar

# 暴露后端服务端口
EXPOSE 8067

# 启动应用
ENTRYPOINT ["java","-jar","app.jar"]