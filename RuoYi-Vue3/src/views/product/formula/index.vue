<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品ID" prop="productId">
        <el-input
          v-model="queryParams.productId"
          placeholder="请输入产品ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原料ID" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入原料ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入用量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入单位"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择更新时间">
        </el-date-picker>
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
          v-hasPermi="['product:formula:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:formula:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:formula:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['product:formula:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="formulaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="配方ID" align="center" prop="formulaId" />
      <el-table-column label="产品ID" align="center" prop="productId" />
      <el-table-column label="原料ID" align="center" prop="materialId" />
      <el-table-column label="用量" align="center" prop="quantity" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['product:formula:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['product:formula:remove']">删除</el-button>
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

    <!-- 添加或修改产品配方对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="formulaRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品ID" prop="productId">
          <el-input v-model="form.productId" placeholder="请输入产品ID" />
        </el-form-item>
        <el-form-item label="原料ID" prop="materialId">
          <el-input v-model="form.materialId" placeholder="请输入原料ID" />
        </el-form-item>
        <el-form-item label="用量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入用量" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
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

<script setup name="Formula">
import { listFormula, getFormula, delFormula, addFormula, updateFormula } from "@/api/product/formula"

const { proxy } = getCurrentInstance()

const formulaList = ref([])
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
    productId: null,
    materialId: null,
    quantity: null,
    unit: null,
    createTime: null,
    updateTime: null
  },
  rules: {
    productId: [
      { required: true, message: "产品ID不能为空", trigger: "blur" }
    ],
    materialId: [
      { required: true, message: "原料ID不能为空", trigger: "blur" }
    ],
    quantity: [
      { required: true, message: "用量不能为空", trigger: "blur" }
    ],
    unit: [
      { required: true, message: "单位不能为空", trigger: "blur" }
    ],
    createTime: [
      { required: true, message: "创建时间不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询产品配方列表 */
function getList() {
  loading.value = true
  listFormula(queryParams.value).then(response => {
    formulaList.value = response.rows
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
    formulaId: null,
    productId: null,
    materialId: null,
    quantity: null,
    unit: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("formulaRef")
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
  ids.value = selection.map(item => item.formulaId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加产品配方"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _formulaId = row.formulaId || ids.value
  getFormula(_formulaId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改产品配方"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["formulaRef"].validate(valid => {
    if (valid) {
      if (form.value.formulaId != null) {
        updateFormula(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addFormula(form.value).then(response => {
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
  const _formulaIds = row.formulaId || ids.value
  proxy.$modal.confirm('是否确认删除产品配方编号为"' + _formulaIds + '"的数据项？').then(function() {
    return delFormula(_formulaIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('product/formula/export', {
    ...queryParams.value
  }, `formula_${new Date().getTime()}.xlsx`)
}

getList()
</script>
