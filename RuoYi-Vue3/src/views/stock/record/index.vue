<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="原料ID" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入原料ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联订单ID" prop="orderId">
        <el-input
          v-model="queryParams.orderId"
          placeholder="请输入关联订单ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="参考单号" prop="referenceNo">
        <el-input
          v-model="queryParams.referenceNo"
          placeholder="请输入参考单号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人ID" prop="operatorId">
        <el-input
          v-model="queryParams.operatorId"
          placeholder="请输入操作人ID"
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
          v-hasPermi="['stock:record:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:record:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:record:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['stock:record:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="recordList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="记录ID" align="center" prop="recordId" />
      <el-table-column label="原料ID" align="center" prop="materialId" />
      <el-table-column label="变动类型(1:入库,2:出库,3:盘点,4:报损)" align="center" prop="type" />
      <el-table-column label="变动数量" align="center" prop="quantity" />
      <el-table-column label="变动前数量" align="center" prop="beforeQuantity" />
      <el-table-column label="变动后数量" align="center" prop="afterQuantity" />
      <el-table-column label="关联订单ID" align="center" prop="orderId" />
      <el-table-column label="参考单号" align="center" prop="referenceNo" />
      <el-table-column label="操作人ID" align="center" prop="operatorId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['stock:record:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['stock:record:remove']">删除</el-button>
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

    <!-- 添加或修改库存变动记录对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="recordRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原料ID" prop="materialId">
          <el-input v-model="form.materialId" placeholder="请输入原料ID" />
        </el-form-item>
        <el-form-item label="变动数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入变动数量" />
        </el-form-item>
        <el-form-item label="变动前数量" prop="beforeQuantity">
          <el-input v-model="form.beforeQuantity" placeholder="请输入变动前数量" />
        </el-form-item>
        <el-form-item label="变动后数量" prop="afterQuantity">
          <el-input v-model="form.afterQuantity" placeholder="请输入变动后数量" />
        </el-form-item>
        <el-form-item label="关联订单ID" prop="orderId">
          <el-input v-model="form.orderId" placeholder="请输入关联订单ID" />
        </el-form-item>
        <el-form-item label="参考单号" prop="referenceNo">
          <el-input v-model="form.referenceNo" placeholder="请输入参考单号" />
        </el-form-item>
        <el-form-item label="操作人ID" prop="operatorId">
          <el-input v-model="form.operatorId" placeholder="请输入操作人ID" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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

<script setup name="Record">
import { listRecord, getRecord, delRecord, addRecord, updateRecord } from "@/api/stock/record"

const { proxy } = getCurrentInstance()

const recordList = ref([])
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
    materialId: null,
    type: null,
    orderId: null,
    referenceNo: null,
    operatorId: null,
  },
  rules: {
    materialId: [
      { required: true, message: "原料ID不能为空", trigger: "blur" }
    ],
    type: [
      { required: true, message: "变动类型(1:入库,2:出库,3:盘点,4:报损)不能为空", trigger: "change" }
    ],
    quantity: [
      { required: true, message: "变动数量不能为空", trigger: "blur" }
    ],
    beforeQuantity: [
      { required: true, message: "变动前数量不能为空", trigger: "blur" }
    ],
    afterQuantity: [
      { required: true, message: "变动后数量不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询库存变动记录列表 */
function getList() {
  loading.value = true
  listRecord(queryParams.value).then(response => {
    recordList.value = response.rows
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
    recordId: null,
    materialId: null,
    type: null,
    quantity: null,
    beforeQuantity: null,
    afterQuantity: null,
    orderId: null,
    referenceNo: null,
    operatorId: null,
    remark: null,
    createTime: null
  }
  proxy.resetForm("recordRef")
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
  ids.value = selection.map(item => item.recordId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加库存变动记录"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _recordId = row.recordId || ids.value
  getRecord(_recordId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改库存变动记录"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["recordRef"].validate(valid => {
    if (valid) {
      if (form.value.recordId != null) {
        updateRecord(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addRecord(form.value).then(response => {
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
  const _recordIds = row.recordId || ids.value
  proxy.$modal.confirm('是否确认删除库存变动记录编号为"' + _recordIds + '"的数据项？').then(function() {
    return delRecord(_recordIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('stock/record/export', {
    ...queryParams.value
  }, `record_${new Date().getTime()}.xlsx`)
}

getList()
</script>
