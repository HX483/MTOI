# 阶段1：构建前端项目
FROM node:18-alpine as build-stage

# 设置工作目录
WORKDIR /app

# 复制package.json和package-lock.json
COPY package*.json ./

# 安装依赖
RUN npm install

# 复制项目文件
COPY . .

# 构建生产版本
RUN npm run build:prod

# 阶段2：使用Nginx作为服务器
FROM nginx:alpine

# 复制构建好的文件到Nginx
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 复制自定义Nginx配置（可选）
COPY nginx.conf /etc/nginx/nginx.conf

# 暴露80端口
EXPOSE 80

# 启动Nginx
CMD ["nginx", "-g", "daemon off;"]