# 使用官方Java镜像作为基础镜像
FROM openjdk:8-jdk-alpine

# 设置工作目录
WORKDIR /app

# 复制编译好的jar文件到容器中
COPY target/ruoyi-admin.jar app.jar

# 暴露服务端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]