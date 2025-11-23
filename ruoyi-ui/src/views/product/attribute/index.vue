<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="属性名称" prop="attributeName">
        <el-input
          v-model="queryParams.attributeName"
          placeholder="请输入属性名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值" prop="attributeValue">
        <el-input
          v-model="queryParams.attributeValue"
          placeholder="请输入属性值"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品" prop="productId">
        <el-select 
          v-model="queryParams.productId" 
          placeholder="请选择产品" 
          clearable
          filterable
        >
          <el-option
            v-for="product in productList"
            :key="product.productId"
            :label="product.productName"
            :value="product.productId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['product:attribute:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:attribute:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:attribute:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:attribute:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attributeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="属性ID" align="center" prop="attributeId" />
      <el-table-column label="属性名称" align="center" prop="attributeName" />
      <el-table-column label="属性值" align="center">
        <template slot-scope="scope">
          <el-tag 
            v-for="(value, index) in formatAttributeValue(scope.row.attributeValue)" 
            :key="index" 
            size="small" 
            :type="['primary', 'success', 'warning', 'danger', 'info'][index % 5]"
            style="margin-right: 8px;"
          >
            {{ value }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="产品" align="center" prop="productId">
        <template slot-scope="scope">
          {{ productMap.get(scope.row.productId) || scope.row.productId }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:attribute:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:attribute:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改产品属性对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="属性名称" prop="attributeName">
          <el-input v-model="form.attributeName" placeholder="请输入属性名称" />
        </el-form-item>
        <el-form-item label="属性值" prop="attributeValues">
          <el-input 
            v-model="form.attributeValues" 
            placeholder="请输入属性值，多个值用空格分隔，如：冰 常温 热" 
            type="textarea"
            :rows="3"
          />
          <div style="margin-top: 5px; font-size: 12px; color: #999;">
            输入示例：冰 常温 热 （系统将自动转换为JSON格式存储）
          </div>
        </el-form-item>
        <el-form-item label="产品" prop="productId">
          <el-select 
            v-model="form.productId" 
            placeholder="请选择产品"
            filterable
          >
            <el-option
              v-for="product in productList"
              :key="product.productId"
              :label="product.productName"
              :value="product.productId">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAttribute, getAttribute, delAttribute, addAttribute, updateAttribute } from "@/api/product/attribute"
import { listInfo } from "@/api/product/info"

export default {
  name: "Attribute",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品属性表格数据
      attributeList: [],
      // 产品信息列表
      productList: [],
      // 产品名称映射
      productMap: new Map(),
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        attributeName: null,
        attributeValue: null,
        productId: null,
      },
      // 表单参数
      form: {
        attributeValues: "" // 用于用户输入的属性值
      },
      // 表单校验
      rules: {
        attributeName: [
          { required: true, message: "属性名称不能为空", trigger: "blur" }
        ],
        attributeValues: [
          { required: true, message: "属性值不能为空", trigger: "blur" }
        ],
        productId: [
          { required: true, message: "产品不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getProductList()
    this.getList()
  },
  methods: {
    /** 查询产品信息列表 */
    getProductList() {
      listInfo().then(response => {
        this.productList = response.rows
        // 构建产品ID到产品名称的映射
        this.productList.forEach(product => {
          this.productMap.set(product.productId, product.productName)
        })
      })
    },
    /** 格式化属性值显示，返回数组 */
    formatAttributeValue(value) {
      if (!value) return [];
      try {
        // 尝试解析JSON字符串
        const parsed = typeof value === 'string' ? JSON.parse(value) : value;
        // 如果是包含options数组的对象
        if (parsed && parsed.options && Array.isArray(parsed.options)) {
          return parsed.options;
        }
        // 如果本身就是数组
        if (Array.isArray(parsed)) {
          return parsed;
        }
        // 如果是用顿号连接的字符串，分割成数组
        if (typeof parsed === 'string' && parsed.includes('、')) {
          return parsed.split('、');
        }
        // 返回单元素数组
        return [String(parsed)];
      } catch (error) {
        // 如果解析失败，返回原始值作为单元素数组
        return [String(value)];
      }
    },
    // 原始的getList方法已被上面的新版本替代
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        attributeId: null,
        attributeName: null,
        attributeValue: null,
        attributeValues: "",
        productId: null,
        createTime: null,
        updateTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重写getList方法，添加属性值的前端模糊搜索支持 */
    getList() {
      this.loading = true
      // 创建一个不包含attributeValue的查询参数对象，避免后端过滤
      const apiParams = { ...this.queryParams }
      delete apiParams.attributeValue
      
      listAttribute(apiParams).then(response => {
        let filteredList = response.rows
        // 如果有属性值搜索关键词，则进行前端模糊搜索
        if (this.queryParams.attributeValue) {
          const keyword = this.queryParams.attributeValue.toLowerCase()
          filteredList = response.rows.filter(item => {
            try {
              // 获取格式化后的属性值数组
              const attrValues = this.formatAttributeValue(item.attributeValue)
              // 检查是否有任何一个属性值包含关键词
              return attrValues.some(value => 
                String(value).toLowerCase().includes(keyword)
              )
            } catch (error) {
              console.error('属性值搜索过滤出错:', error)
              // 出错时返回原始值进行匹配
              return String(item.attributeValue).toLowerCase().includes(keyword)
            }
          })
        }
        this.attributeList = filteredList
        this.total = response.total || filteredList.length  // 修复：确保总数量正确显示
        this.loading = false
      })
    },
    // 原始获取列表方法作为备份（实际未使用，保留参考）
    originalGetList() {
      this.loading = true
      listAttribute(this.queryParams).then(response => {
        this.attributeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      // 重置查询参数
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        attributeName: null,
        attributeValue: null,
        productId: null
      };
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.attributeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加产品属性"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const attributeId = row.attributeId || this.ids
      getAttribute(attributeId).then(response => {
        this.form = response.data
        // 解析属性值，将JSON格式转换为用户友好的格式
        if (this.form.attributeValue) {
          try {
            const parsed = JSON.parse(this.form.attributeValue);
            if (parsed.options && Array.isArray(parsed.options)) {
              this.form.attributeValues = parsed.options.join(" ");
            } else {
              this.form.attributeValues = this.form.attributeValue;
            }
          } catch (e) {
            this.form.attributeValues = this.form.attributeValue;
          }
        }
        this.open = true
        this.title = "修改产品属性"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 创建表单数据副本
          const formData = { ...this.form };
          
          // 将用户输入的属性值转换为JSON格式
          if (formData.attributeValues) {
            const values = formData.attributeValues.trim().split(/\s+/).filter(item => item !== "");
            formData.attributeValue = JSON.stringify({ options: values });
          }
          
          if (formData.attributeId != null) {
            updateAttribute(formData).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAttribute(formData).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              // 重置搜索条件，确保新增的数据能显示出来
              this.resetQuery();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const attributeIds = row.attributeId || this.ids
      this.$modal.confirm('是否确认删除产品属性编号为"' + attributeIds + '"的数据项？').then(function() {
        return delAttribute(attributeIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/attribute/export', {
        ...this.queryParams
      }, `attribute_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
