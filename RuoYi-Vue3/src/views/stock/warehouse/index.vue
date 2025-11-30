<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库名称" prop="warehouseName">
        <el-input
          v-model="queryParams.warehouseName"
          placeholder="请输入仓库名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入仓库地址"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="负责人ID" prop="managerId">
        <el-input
          v-model="queryParams.managerId"
          placeholder="请输入负责人ID"
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
          v-hasPermi="['stock:warehouse:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:warehouse:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:warehouse:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['stock:warehouse:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="warehouseList" @selection-change="handleSelectionChange" ref="warehouseTable" :row-class-name="tableRowClassName">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库ID" align="center" prop="warehouseId" />
      <el-table-column label="仓库名称" align="center" prop="warehouseName" />
      <el-table-column label="仓库地址" align="center" prop="address" />
      <el-table-column label="负责人ID" align="center" prop="managerId" />
      <el-table-column label="状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['stock:warehouse:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['stock:warehouse:remove']">删除</el-button>
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

    <!-- 添加或修改仓库对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="warehouseRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="仓库名称" prop="warehouseName">
          <el-input v-model="form.warehouseName" placeholder="请输入仓库名称" />
        </el-form-item>
        <el-form-item label="仓库地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入仓库地址" />
        </el-form-item>
        <el-form-item label="负责人ID" prop="managerId">
          <el-input v-model="form.managerId" placeholder="请输入负责人ID" />
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

<script setup name="Warehouse">
import { listWarehouse, getWarehouse, delWarehouse, addWarehouse, updateWarehouse } from "@/api/stock/warehouse"
import { onMounted, nextTick, ref, reactive, toRefs } from "vue"

const { proxy } = getCurrentInstance()
const warehouseTable = ref(null)

const warehouseList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
// 高亮显示的仓库ID
const highlightWarehouseId = ref('')

// 页面加载时检查是否有需要高亮的仓库ID
onMounted(() => {
  const storedHighlightId = sessionStorage.getItem('highlightWarehouseId');
  if (storedHighlightId) {
    console.log('页面加载时发现需要高亮的仓库ID:', storedHighlightId);
    highlightWarehouseId.value = storedHighlightId;
  }
});

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    warehouseName: null,
    address: null,
    managerId: null,
    status: null,
  },
  rules: {
    warehouseName: [
      { required: true, message: "仓库名称不能为空", trigger: "blur" }
    ],
    address: [
      { required: true, message: "仓库地址不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询仓库列表 */
function getList() {
  loading.value = true
  // 从sessionStorage获取需要高亮的仓库ID
  const storedHighlightId = sessionStorage.getItem('highlightWarehouseId');
  console.log('从sessionStorage获取的高亮仓库ID:', storedHighlightId);
  if (storedHighlightId) {
    highlightWarehouseId.value = storedHighlightId;
    console.log('设置高亮仓库ID:', highlightWarehouseId.value);
    // 清除sessionStorage中的标记
    sessionStorage.removeItem('highlightWarehouseId');
  }
  
  listWarehouse(queryParams.value).then(response => {
    warehouseList.value = response.rows
    total.value = response.total
    loading.value = false
    console.log('仓库列表数据:', warehouseList.value);
    
    // 检查是否有需要强调显示的仓库
    nextTick(() => {
      if (highlightWarehouseId.value) {
        console.log('准备高亮仓库:', highlightWarehouseId.value);
        // 查找对应的行并强调显示 - 使用String进行类型转换确保匹配
        setTimeout(() => {
          const index = warehouseList.value.findIndex(item => 
            String(item.warehouseId) === String(highlightWarehouseId.value)
          );
          console.log('找到的索引:', index);
          if (index !== -1) {
            // 滚动到对应行
            proxy.$refs.warehouseTable.setCurrentRow(warehouseList.value[index]);
            // 添加额外的视觉效果提示
            proxy.$message.success(`已定位到仓库ID: ${highlightWarehouseId.value}`);
          } else {
            proxy.$message.warning(`未找到仓库ID: ${highlightWarehouseId.value}`);
          }
        }, 100);
      }
    });
  })
}

/**
 * 表格行样式类名设置
 * @param {Object} row 行数据
 * @returns {String} 样式类名
 */
function tableRowClassName({ row }) {
  // 使用String进行类型转换确保匹配
  const isMatch = highlightWarehouseId.value && 
                  String(row.warehouseId) === String(highlightWarehouseId.value);
  console.log('检查行样式:', {highlightId: highlightWarehouseId.value, rowId: row.warehouseId, match: isMatch});
  
  if (isMatch) {
    return 'highlight-row';
  }
  return '';
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    warehouseId: null,
    warehouseName: null,
    address: null,
    managerId: null,
    status: null,
    createTime: null,
    updateTime: null
  }
  proxy.resetForm("warehouseRef")
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
  ids.value = selection.map(item => item.warehouseId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加仓库"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _warehouseId = row.warehouseId || ids.value
  getWarehouse(_warehouseId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改仓库"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["warehouseRef"].validate(valid => {
    if (valid) {
      if (form.value.warehouseId != null) {
        updateWarehouse(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addWarehouse(form.value).then(response => {
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
  const _warehouseIds = row.warehouseId || ids.value
  proxy.$modal.confirm('是否确认删除仓库编号为"' + _warehouseIds + '"的数据项？').then(function() {
    return delWarehouse(_warehouseIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('stock/warehouse/export', {
    ...queryParams.value
  }, `warehouse_${new Date().getTime()}.xlsx`)
}

getList()
</script>

<style scoped>
/* 高亮行样式 */
:deep(.highlight-row) {
  background-color: #e6f7ff !important;
  color: #000 !important;
}

:deep(.highlight-row:hover) {
  background-color: #bae7ff !important;
  color: #000 !important;
}

/* 确保高亮行中的文字清晰可见 */
:deep(.highlight-row td) {
  color: #000 !important;
  font-weight: bold;
}
</style>
