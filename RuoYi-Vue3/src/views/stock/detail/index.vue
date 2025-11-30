<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="盘点ID" prop="checkId">
        <el-input
          v-model="queryParams.checkId"
          placeholder="请输入盘点ID"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="原料" prop="materialId">
        <el-select
          v-model="queryParams.materialId"
          placeholder="请选择原料"
          clearable
          filterable
          style="width: 200px;"
        >
          <el-option
            v-for="item in materialOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="系统库存" prop="systemQuantity">
        <el-input
          v-model="queryParams.systemQuantity"
          placeholder="请输入系统库存"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际盘点数量" prop="actualQuantity">
        <el-input
          v-model="queryParams.actualQuantity"
          placeholder="请输入实际盘点数量"
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
      <el-form-item label="调整时间" prop="adjustmentTime">
        <el-date-picker clearable
          v-model="queryParams.adjustmentTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择调整时间">
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
          v-hasPermi="['stock:detail:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:detail:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:detail:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['stock:detail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="明细ID" align="center" prop="detailId" />
      <el-table-column label="盘点ID" align="center" prop="checkId" />
      <el-table-column label="原料" align="center" prop="materialId">
        <template #default="scope">
          {{ materialMap.get(scope.row.materialId) || scope.row.materialId }}
        </template>
      </el-table-column>
      <el-table-column label="系统库存" align="center" prop="systemQuantity" />
      <el-table-column label="实际盘点数量" align="center" prop="actualQuantity" />
      <el-table-column label="差异数量" align="center" prop="discrepancyQuantity" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="处理状态" align="center" prop="status" >
        <template #default="scope">
          <dict-tag :options="dispose_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="调整时间" align="center" prop="adjustmentTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.adjustmentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['stock:detail:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['stock:detail:remove']">删除</el-button>
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

    <!-- 添加或修改库存盘点明细对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="detailRef" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="盘点ID" prop="checkId">
          <el-input v-model="form.checkId" placeholder="请输入盘点ID" />
        </el-form-item>
        <el-form-item label="原料" prop="materialId">
          <el-select
            v-model="form.materialId"
            placeholder="请选择原料"
            filterable
            style="width: 100%;"
          >
            <el-option
              v-for="item in materialOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="系统库存" prop="systemQuantity">
          <el-input v-model="form.systemQuantity" placeholder="请输入系统库存" />
        </el-form-item>
        <el-form-item label="实际盘点数量" prop="actualQuantity">
          <el-input v-model="form.actualQuantity" placeholder="请输入实际盘点数量" />
        </el-form-item>
        <el-form-item label="差异数量" prop="discrepancyQuantity">
          <el-input v-model="form.discrepancyQuantity" placeholder="请输入差异数量" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="调整时间" prop="adjustmentTime">
          <el-date-picker clearable
            v-model="form.adjustmentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择调整时间">
          </el-date-picker>
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

<script setup name="Detail">
import { listDetail, getDetail, delDetail, addDetail, updateDetail } from "@/api/stock/detail"
import { listInfo as materialInfo } from "@/api/material/info"

const { proxy } = getCurrentInstance()
const { dispose_status } = proxy.useDict('dispose_status')
const route = useRoute()

const detailList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const materialList = ref([])
const materialMap = ref(new Map())
const materialOptions = ref([])

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    checkId: null,
    materialId: null,
    systemQuantity: null,
    actualQuantity: null,
    unit: null,
    status: null,
    adjustmentTime: null,
  },
  rules: {
    checkId: [
      { required: true, message: "盘点ID不能为空", trigger: "blur" }
    ],
    materialId: [
      { required: true, message: "原料不能为空", trigger: "change" }
    ],
    systemQuantity: [
      { required: true, message: "系统库存不能为空", trigger: "blur" }
    ],
    actualQuantity: [
      { required: true, message: "实际盘点数量不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询库存盘点明细列表 */
function getList() {
  loading.value = true
  listDetail(queryParams.value).then(response => {
    detailList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 获取原料列表 */
function getMaterialList() {
  materialInfo({}).then(response => {
    materialList.value = response.rows || [];
    // 构建materialMap用于显示原料名称
    materialList.value.forEach((item) => {
      materialMap.value.set(item.materialId, item.materialName);
    });
    // 格式化原料列表为下拉框选项格式
    materialOptions.value = materialList.value.map(item => ({
      value: item.materialId,
      label: `${item.materialName} - ${item.specification || ''}`
    }));
  }).catch(error => {
    console.error('获取原料列表失败:', error);
    materialOptions.value = [];
    proxy.$modal.msgError('获取原料信息失败，请稍后重试');
  });
}

// 初始化时检查路由参数
onMounted(() => {
  // 先获取原料列表
  getMaterialList();
  
  // 页面加载时检查路由参数，如果有checkId则自动查询
  const checkIdFromRoute = route.query.checkId;
  if (checkIdFromRoute) {
    queryParams.value.checkId = checkIdFromRoute;
    // 重置分页信息
    queryParams.value.pageNum = 1;
    // 显示提示信息
    proxy.$message.success(`已自动筛选盘点ID: ${checkIdFromRoute} 的明细数据`);
  }
  // 无论是否有路由参数，都加载数据
  getList();
});

// 监听路由变化，当路由参数checkId变化时重新加载数据
watch(
  () => route.query.checkId,
  (newCheckId) => {
    if (newCheckId) {
      queryParams.value.checkId = newCheckId;
    } else {
      queryParams.value.checkId = null;
    }
    // 重置分页信息
    queryParams.value.pageNum = 1;
    getList();
  },
  { immediate: false } // 不在初始化时执行，由onMounted处理
)

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef")
  handleQuery()
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

// 取消按钮
function cancel() {
  open.value = false
  reset()
}

// 表单重置
function reset() {
  form.value = {
    detailId: null,
    checkId: null,
    materialId: null,
    systemQuantity: null,
    actualQuantity: null,
    discrepancyQuantity: null,
    unit: null,
    status: null,
    adjustmentTime: null,
    remark: null
  }
  proxy.resetForm("detailRef")
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.detailId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加库存盘点明细"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _detailId = row.detailId || ids.value
  getDetail(_detailId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改库存盘点明细"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["detailRef"].validate(valid => {
    if (valid) {
      if (form.value.detailId != null) {
        updateDetail(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addDetail(form.value).then(response => {
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
  const _detailIds = row.detailId || ids.value
  proxy.$modal.confirm('是否确认删除库存盘点明细编号为"' + _detailIds + '"的数据项？').then(function() {
    return delDetail(_detailIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('stock/detail/export', {
    ...queryParams.value
  }, `detail_${new Date().getTime()}.xlsx`)
}

</script>
