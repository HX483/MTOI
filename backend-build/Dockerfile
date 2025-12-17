# FROM eclipse-temurin:8-jdk-alpine
# 使用非Alpine的Java镜像，或者安装必要的字体库
# FROM eclipse-temurin:8-jre

# 或者如果坚持用Alpine，需要安装字体库
FROM eclipse-temurin:8-jre-alpine
RUN apk add --no-cache fontconfig ttf-dejavu libgcc libstdc++
# 创建上传目录
WORKDIR /app
RUN mkdir -p /app/upload

# 复制应用文件
COPY ruoyi-admin.jar ./app.jar
COPY ../img /app/img

# 暴露端口
EXPOSE 8080

# 启动命令（指定上传目录）
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Druoyi.profile=/app/upload", "-jar", "app.jar"]