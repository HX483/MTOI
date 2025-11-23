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
      <el-form-item label="属性值(JSON格式)" prop="attributeValue">
        <el-input
          v-model="queryParams.attributeValue"
          placeholder="请输入属性值(JSON格式)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入产品ID"
          clearable
          @keyup.enter="handleQuery"
        />
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
      <el-table-column label="属性值(JSON格式)" align="center" prop="attributeValue" />
      <el-table-column label="产品ID" align="center" prop="productId" />
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
        <el-form-item label="属性值(JSON格式)" prop="attributeValue">
          <el-input v-model="form.attributeValue" placeholder="请输入属性值(JSON格式)" />
        </el-form-item>
        <el-form-item label="产品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入产品ID" />
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

const { proxy } = getCurrentInstance()

const attributeList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
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
    attributeValue: [
      { required: true, message: "属性值(JSON格式)不能为空", trigger: "blur" }
    ],
    productId: [
      { required: true, message: "产品ID不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询产品属性列表 */
function getList() {
  loading.value = true
  listAttribute(queryParams.value).then(response => {
    attributeList.value = response.rows
    total.value = response.total
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
    open.value = true
    title.value = "修改产品属性"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["attributeRef"].validate(valid => {
    if (valid) {
      if (form.value.attributeId != null) {
        updateAttribute(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addAttribute(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功")
          open.value = false
          getList()
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

getList()
</script>
