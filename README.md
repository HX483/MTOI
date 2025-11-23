<p align="center">
	<img alt="logo" src="./img/make.png" width="150" height="150">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">奶茶店订单制作与原料库存管理系统</h1>
<h4 align="center">基于MTOI框架结合大模型技术的奶茶店智能管理平台</h4>
<p align="center">
	<a href="https://gitee.com/y_project/RuoYi-Vue/stargazers"><img src="https://gitee.com/y_project/RuoYi-Vue/badge/star.svg?theme=dark"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue"><img src="https://img.shields.io/badge/MTOI-v1.0.0-brightgreen.svg"></a>
	<a href="https://gitee.com/y_project/RuoYi-Vue/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>
</p>

## 平台简介

MTOI奶茶店订单制作与原料库存管理系统是一套全部开源的智能管理平台，基于RuoYi-Vue3框架开发，结合大模型技术，旨在帮助奶茶店实现订单处理、原料管理、数据分析等核心业务的数字化和智能化，提高运营效率和服务质量。

* 前端采用Vue3、Element Plus、Vite构建。
* 后端采用Spring Boot、Spring Security、Redis & Jwt。
* 权限认证使用Jwt，支持多终端认证系统。
* 支持加载动态权限菜单，多方式轻松权限控制。
* 高效率开发，使用代码生成器可以一键生成前后端代码。
* 集成大模型技术，提供智能推荐、智能客服、需求预测等功能。

## 技术架构

### 后端技术栈
- **核心框架**：Spring Boot 2.5.x + MyBatis
- **安全框架**：Spring Security + JWT
- **数据库**：MySQL 8.0
- **缓存**：Redis
- **接口文档**：Swagger 3.0
- **日志管理**：Logback
- **定时任务**：Quartz
- **代码生成**：MTOI代码生成器

### 前端技术栈
- **核心框架**：Vue 3.x
- **路由**：Vue Router
- **状态管理**：Vuex
- **UI框架**：Element Plus
- **构建工具**：Vite
- **HTTP客户端**：Axios
- **工具库**：Lodash、Moment.js等

### 大模型集成
- **AI平台**：DeepSeek API
- **应用场景**：智能推荐、客服对话、销售预测

## 核心功能模块

### 1. 系统管理模块
- **用户管理**：用户增删改查、密码重置、权限分配
- **角色管理**：角色创建、权限配置、用户关联
- **菜单管理**：动态菜单配置、权限标识设置
- **部门管理**：组织架构维护、数据权限控制
- **字典管理**：系统数据字典维护
- **参数配置**：系统参数设置
- **通知公告**：系统消息发布
- **日志管理**：操作日志、登录日志查询

### 2. 产品管理模块
- **产品分类管理**：奶茶产品分类维护
- **产品信息管理**：产品详细信息、图片管理
- **产品配方管理**：原料配方配置、用量管理

### 3. 订单管理模块
- **订单创建与管理**：支持堂食、外卖、自提等类型订单
- **订单制作流程**：订单接收、制作、完成、配送全流程管理
- **订单历史查询**：多条件筛选、导出功能
- **订单统计分析**：销售数据、热销产品、时段分析
- **异常订单处理**：退单、改单、投诉处理

### 4. 原料库存管理模块
- **原料信息管理**：原料分类、规格、保质期等基础信息维护
- **库存实时监控**：当前库存数量、预警阈值设置
- **入库出库管理**：采购入库、生产领用、报损等操作记录
- **库存盘点功能**：定期盘点、差异调整
- **智能补货建议**：基于历史销售和库存消耗的补货提醒

### 5. 大模型智能应用模块
- **智能推荐**：基于用户历史订单推荐个性化饮品
- **智能客服**：处理常见订单咨询和问题
- **配方智能优化**：基于原料成本和用户反馈优化配方
- **需求预测**：预测未来销售趋势和原料需求

### 6. 报表分析模块
- **销售报表**：日报、周报、月报、年报
- **原料消耗报表**：各原料消耗趋势分析
- **成本分析报表**：原料成本、人工成本、利润分析
- **异常报表**：库存异常、订单异常等告警统计

## 项目结构

```
MTOI/
├── RuoYi-Vue3/            # 前端应用
├── ruoyi-admin/           # 后端主应用
├── ruoyi-common/          # 通用模块
├── ruoyi-framework/       # 框架核心
├── ruoyi-generator/       # 代码生成器
├── ruoyi-material/        # 原料管理模块
├── ruoyi-product/         # 产品管理模块
├── ruoyi-quartz/          # 定时任务
├── ruoyi-system/          # 系统模块
├── sql/                   # 数据库脚本
├── doc/                   # 文档
├── img/                   # 图片资源
└── 文档/                  # 项目文档
```

## 开发环境要求

### 后端环境
1. **JDK版本**：Java 8 或以上
2. **开发工具**：IntelliJ IDEA
3. **数据库**：MySQL 8.0
4. **缓存**：Redis 5.0 或以上
5. **构建工具**：Maven 3.6 或以上

### 前端环境
1. **Node.js**：12.x 或以上
2. **包管理器**：npm 或 yarn
3. **开发工具**：Visual Studio Code

## 部署说明

### 后端部署
1. 使用Maven打包：`mvn clean package`
2. 将生成的jar包上传到服务器
3. 运行命令：`java -jar ruoyi-admin.jar`

### 前端部署
1. 进入RuoYi-Vue3目录
2. 安装依赖：`npm install`
3. 构建生产环境代码：`npm run build:prod`
4. 将dist目录下的文件部署到Nginx服务器

### Nginx配置示例
```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    location / {
        root /path/to/dist;
        try_files $uri $uri/ /index.html;
    }
    
    location /prod-api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
```

## 系统演示

### 功能展示

| 订单管理 | 原料库存 |
| :--- | :--- |
| <img src="./img/milkyTea1.png" width="300" height="200"> | <img src="./img/milkyTea2.png" width="300" height="200"> |

| 原料信息 | 制作流程 |
| :--- | :--- |
| <img src="./img/ingredient.png" width="300" height="200"> | <img src="./img/make.png" width="300" height="200"> |

## 开发计划

系统开发分为六个阶段：
1. **系统基础设施**（已完成）：基于MTOI框架的基础功能
2. **核心业务模块开发**：产品管理、原料管理、库存管理
3. **订单管理模块开发**：订单创建、流程管理、查询统计
4. **采购管理模块开发**：供应商管理、采购订单、采购入库
5. **报表分析模块开发**：销售报表、原料消耗报表、成本分析
6. **大模型智能应用模块开发**：智能推荐、客服、预测

## 许可证

本项目采用Apache License 2.0开源协议。

## 联系我们

如有问题或建议，请联系项目团队。

---

> 基于MTOI框架开发 | 打造智能奶茶店管理系统