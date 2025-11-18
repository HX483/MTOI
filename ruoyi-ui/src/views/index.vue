<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :span="10">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>MTOI管理系统</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 基于RuoYi-Vue 3.9.0二次开发
            </p>
            <p>
              <i class="el-icon-s-promotion"></i> 企业级快速开发平台
            </p>
            <p>
              <i class="el-icon-s-promotion"></i> 前后端分离架构，基于Vue3 + Element Plus
            </p>
            <p>
              <i class="el-icon-s-promotion"></i> 提供了丰富的组件和功能
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>开发文档</span>
          </div>
          <el-scrollbar height="400px">
            <div>
              <h4>后端技术栈</h4>
              <ul>
                <li>Spring Boot 2.5.15</li>
                <li>Spring Security 5.7.14</li>
                <li>JWT 0.9.1</li>
                <li>MyBatis 3.5.13</li>
                <li>Druid 1.2.23</li>
                <li>PageHelper 1.4.7</li>
                <li>Fastjson2 2.0.58</li>
                <li>Apache Commons 2.19.0</li>
                <li>POI 4.1.2</li>
                <li>Oshi 6.8.3</li>
                <li>Velocity 2.3</li>
                <li>Swagger3 3.0.0</li>
              </ul>
              <h4>前端技术栈</h4>
              <ul>
                <li>Vue 3.4.0</li>
                <li>Element Plus 2.6.3</li>
                <li>Axios 0.28.1</li>
                <li>Pinia 2.2.0</li>
                <li>Vite 5.0.0</li>
              </ul>
            </div>
          </el-scrollbar>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { useUserStore } from '@/stores/user'

const userStore = useUserStore()

const version = '3.9.0'

const goTarget = (href) => {
  window.open(href, '_blank')
}

</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

