<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="属性名称" prop="attributeName">
        <el-input
          v-model="queryParams.attributeName"
          placeholder="请输入属性名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="属性值" prop="attributeValue">
        <el-input
          v-model="queryParams.attributeValue"
          placeholder="请输入属性值"
          clearable
          @keyup.enter="handleQuery"
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
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="Plus"
          @click="handleAdd"
          v-hasPermi="['product:attribute:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:attribute:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:attribute:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['product:attribute:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="attributeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="属性ID" align="center" prop="attributeId" />
      <el-table-column label="属性名称" align="center" prop="attributeName" />
      <el-table-column label="属性值" align="center">
        <template #default="scope">
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
        <template #default="scope">
          {{ productMap.get(scope.row.productId) || scope.row.productId }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['product:attribute:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['product:attribute:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      v-model:page="queryParams.pageNum"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改产品属性对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="attributeRef" :model="form" :rules="rules" label-width="80px">
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
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Attribute">
import { listAttribute, getAttribute, delAttribute, addAttribute, updateAttribute } from "@/api/product/attribute"
import { listInfo } from "@/api/product/info"

const { proxy } = getCurrentInstance()

const attributeList = ref([])
const productList = ref([])
const productMap = ref(new Map())
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {
    attributeValues: "" // 用于用户输入的属性值
  },
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    attributeName: null,
    attributeValue: null,
    productId: null,
  },
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
})

const { queryParams, form, rules } = toRefs(data)

/** 查询产品信息列表 */
function getProductList() {
  listInfo().then(response => {
    productList.value = response.rows
    // 构建产品ID到产品名称的映射
    productList.value.forEach(product => {
      productMap.value.set(product.productId, product.productName)
    })
  })
}

/** 格式化属性值显示，返回数组 */

function formatAttributeValue(value) {
  if (!value) return [];
  try {
    // 尝试解析JSON字符串
    const parsed = typeof value === 'string' ? JSON.parse(value) : value;
    // 如果是包含options数组的对象
    if (parsed && parsed.options && Array.isArray(parsed.options)) {
      console.log("parsed.options:", parsed.options);
      return parsed.options;
    }
    // 如果本身就是数组
    if (Array.isArray(parsed)) {
      console.log("parsed:", parsed);
      return parsed;
    }
    // 如果是用顿号连接的字符串，分割成数组
    if (typeof parsed === 'string' && parsed.includes('、')) {
      console.log("parsed.split('、'):", parsed.split('、'));
      return parsed.split('、');
    }
    // 返回单元素数组
    console.log("[String(parsed)]:", [String(parsed)]);
    return [String(parsed)];
  } catch (error) {
    // 如果解析失败，返回原始值作为单元素数组
    return [String(value)];
  }
}

/** 查询产品属性列表 */
function getList() {
  loading.value = true
  // 创建一个不包含attributeValue的查询参数对象，避免后端过滤
  const apiParams = { ...queryParams.value }
  delete apiParams.attributeValue
  
  listAttribute(apiParams).then(response => {
    let filteredList = response.rows
    // 如果有属性值搜索关键词，则进行前端模糊搜索
    if (queryParams.value.attributeValue) {
      const keyword = queryParams.value.attributeValue.toLowerCase()
      filteredList = response.rows.filter(item => {
        try {
          // 获取格式化后的属性值数组
          const attrValues = formatAttributeValue(item.attributeValue)
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
    attributeList.value = filteredList
    total.value = response.total || filteredList.length
    loading.value = false
  })
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    attributeId: null,
    attributeName: null,
    attributeValue: null,
    attributeValues: "",
    productId: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("attributeRef")
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  // 重置查询参数
  queryParams.value = {
    pageNum: 1,
    pageSize: 10,
    attributeName: null,
    attributeValue: null,
    productId: null
  }
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.attributeId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加产品属性"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _attributeId = row.attributeId || ids.value
  getAttribute(_attributeId).then(response => {
    form.value = response.data
    // 解析属性值，将JSON格式转换为用户友好的格式
    if (form.value.attributeValue) {
      try {
        const parsed = JSON.parse(form.value.attributeValue);
        if (parsed.options && Array.isArray(parsed.options)) {
          form.value.attributeValues = parsed.options.join(" ");
        } else {
          form.value.attributeValues = form.value.attributeValue;
        }
      } catch (e) {
        form.value.attributeValues = form.value.attributeValue;
      }
    }
    open.value = true
    title.value = "修改产品属性"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["attributeRef"].validate(valid => {
    if (valid) {
      // 创建表单数据副本
      const formData = { ...form.value };
      
      // 将用户输入的属性值转换为JSON格式
      if (formData.attributeValues) {
        const values = formData.attributeValues.trim().split(/\s+/).filter(item => item !== "");
        formData.attributeValue = JSON.stringify({ options: values });
      }
      
      if (formData.attributeId != null) {
        updateAttribute(formData).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAttribute(formData).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          // 重置搜索条件，确保新增的数据能显示出来
          resetQuery()
        })
      }
    }
  })
}

/** 删除按钮操作 */
function handleDelete(row) {
  const _attributeIds = row.attributeId || ids.value
  proxy.$modal.confirm('是否确认删除产品属性编号为"' + _attributeIds + '"的数据项？').then(function() {
    return delAttribute(_attributeIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('product/attribute/export', {
    ...queryParams.value
  }, `attribute_${new Date().getTime()}.xlsx`)
}

getProductList()
getList()
</script>