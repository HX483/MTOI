<template>
  <el-image
    :src="`${realSrc}`"
    fit="cover"
    :style="`width:${realWidth};height:${realHeight};`"
    :preview-src-list="realSrcList"
  >
    <div slot="error" class="image-slot">
      <i class="el-icon-picture-outline"></i>
    </div>
  </el-image>
</template>

<script>
import { isExternal } from "@/utils/validate"

export default {
  name: "ImagePreview",
  props: {
    src: {
      type: String,
      default: ""
    },
    width: {
      type: [Number, String],
      default: ""
    },
    height: {
      type: [Number, String],
      default: ""
    }
  },
  computed: {
    realSrc() {
      if (!this.src) {
        return
      }
      let real_src = this.src.split(",")[0]
      // 处理外部链接
      if (isExternal(real_src)) {
        return real_src
      }
      // 特殊处理以img/开头的路径，这类路径直接指向public目录下的图片
      if (real_src.startsWith('img/')) {
        return '/' + real_src
      }
      // 其他相对路径添加API前缀
      return import.meta.env.VITE_APP_BASE_API + real_src
    },
    realSrcList() {
      if (!this.src) {
        return
      }
      let real_src_list = this.src.split(",")
      let srcList = []
      real_src_list.forEach(item => {
        // 处理外部链接
        if (isExternal(item)) {
          return srcList.push(item)
        }
        // 特殊处理以img/开头的路径，这类路径直接指向public目录下的图片
        if (item.startsWith('img/')) {
          return srcList.push('/' + item)
        }
        // 其他相对路径添加API前缀
        return srcList.push(import.meta.env.VITE_APP_BASE_API + item)
      })
      return srcList
    },
    realWidth() {
      return typeof this.width == "string" ? this.width : `${this.width}px`
    },
    realHeight() {
      return typeof this.height == "string" ? this.height : `${this.height}px`
    }
  }
}
</script>

<style lang="scss" scoped>
.el-image {
  border-radius: 5px;
  background-color: #ebeef5;
  box-shadow: 0 0 5px 1px #ccc;
  ::v-deep .el-image__inner {
    transition: all 0.3s;
    cursor: pointer;
    &:hover {
      transform: scale(1.2);
    }
  }
  ::v-deep .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    color: #909399;
    font-size: 30px;
  }
}
</style>
