## 技术栈变更说明

本项目已完成以下技术栈升级：

1. **UI框架**：从 Element UI 2.x 升级到 Element Plus
2. **构建工具**：从 Vue CLI 迁移到 Vite
3. **Vue API**：组件已从 Options API 迁移到 Composition API

## 开发

```bash
# 克隆项目
git clone https://gitee.com/y_project/RuoYi-Vue

# 进入项目目录
cd ruoyi-ui

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npmmirror.com

# 启动服务
npm run dev
```

浏览器访问 http://localhost:80

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```