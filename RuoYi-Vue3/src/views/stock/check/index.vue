<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="盘点单号" prop="checkNo">
        <el-input
          v-model="queryParams.checkNo"
          placeholder="请输入盘点单号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select
          v-model="queryParams.warehouseId"
          placeholder="请选择仓库"
          clearable
          filterable
        >
          <el-option
            v-for="item in warehouseList"
            :key="item.warehouseId"
            :label="item.warehouseName"
            :value="item.warehouseId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="盘点日期" prop="checkDate">
        <el-date-picker clearable
          v-model="queryParams.checkDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择盘点日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="盘点类型" prop="checkType">
        <el-select v-model="queryParams.checkType" placeholder="请选择盘点类型" clearable style="width: 150px;">
          <el-option
            v-for="dict in check_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['stock:check:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:check:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:check:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['stock:check:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="盘点ID" align="center" prop="checkId" />
      <el-table-column label="盘点单号" align="center" prop="checkNo" />
      <el-table-column label="仓库" align="center" prop="warehouseId" >
        <template #default="scope">
          {{warehouseMap.get(scope.row.warehouseId)||scope.row.warehouseId}}
        </template>
      </el-table-column>
      <el-table-column label="盘点日期" align="center" prop="checkDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.checkDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点类型" align="center" prop="checkType">
        <template #default="scope">
          <dict-tag :options="check_type" :value="scope.row.checkType"/>
        </template>
      </el-table-column>
      <el-table-column label="盘点状态" align="center" prop="status" >
        <template #default="scope">
          <dict-tag :options="check_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="总盘点数量" align="center" prop="totalCount" />
      <el-table-column label="差异数量" align="center" prop="discrepancyCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleViewDetail(scope.row)" v-hasPermi="['stock:check:query']">详情</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['stock:check:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['stock:check:remove']">删除</el-button>
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

    <!-- 添加或修改库存盘点对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="checkRef" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="盘点单号" prop="checkNo">
          <el-input v-model="form.checkNo" placeholder="自动生成" readonly style="background-color: #f5f7fa;" />
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请选择仓库" filterable>
            <el-option
              v-for="item in warehouseList"
              :key="item.warehouseId"
              :label="item.warehouseName"
              :value="item.warehouseId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="盘点日期" prop="checkDate">
          <el-date-picker clearable
            v-model="form.checkDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择盘点日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="盘点类型" prop="checkType">
          <el-select v-model="form.checkType" placeholder="请选择盘点类型">
            <el-option
              v-for="dict in check_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="总盘点数量" prop="totalCount" >
          <el-input v-model="form.totalCount" placeholder="请输入总盘点数量" />
        </el-form-item>
        <el-form-item label="差异数量" prop="discrepancyCount">
          <el-input v-model="form.discrepancyCount" placeholder="请输入差异数量" />
        </el-form-item>
        <el-form-item label="操作人ID" prop="operatorId">
          <el-input v-model="form.operatorId" placeholder="请输入操作人ID" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-divider content-position="center">库存盘点明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddStockCheckDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteStockCheckDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="stockCheckDetailList" :row-class-name="rowStockCheckDetailIndex" @selection-change="handleStockCheckDetailSelectionChange" ref="stockCheckDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="盘点ID" prop="checkId" width="150" v-show="false">
            <template #default="scope">
              <el-input v-model="scope.row.checkId" placeholder="自动关联" readonly />
            </template>
          </el-table-column>
          <el-table-column label="原料选择" prop="materialId" width="250">
              <template #default="scope">
                <el-select v-model="scope.row.materialId" placeholder="请选择原料" filterable>
                  <el-option
                    v-for="option in data.materialOptions"
                    :key="option.value"
                    :label="option.label"
                    :value="option.value"
                  />
                </el-select>
              </template>
           </el-table-column>
          <el-table-column label="系统库存" prop="systemQuantity" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.systemQuantity" placeholder="请输入系统库存" />
            </template>
          </el-table-column>
          <el-table-column label="实际盘点数量" prop="actualQuantity" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.actualQuantity" placeholder="请输入实际盘点数量" />
            </template>
          </el-table-column>
          <el-table-column label="差异数量" prop="discrepancyQuantity" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.discrepancyQuantity" placeholder="请输入差异数量" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位" />
            </template>
          </el-table-column>
          <el-table-column label="处理状态" prop="status" width="150">
            <template #default="scope">
              <el-select v-model="scope.row.status" placeholder="请选择处理状态" clearable>
                <el-option
                    v-for="dict in dispose_status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="调整时间" prop="adjustmentTime" width="240">
            <template #default="scope">
              <el-date-picker clearable
                v-model="scope.row.adjustmentTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择调整时间">
              </el-date-picker>
            </template>
          </el-table-column>
        </el-table>
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

<script setup name="Check">
import { listCheck, getCheck, delCheck, addCheck, updateCheck, generateCheckNo } from "@/api/stock/check"
import { listWarehouse } from "@/api/stock/warehouse"
import { listInfo } from "@/api/material/info"

const { proxy } = getCurrentInstance()
const { check_type } = proxy.useDict('check_type')
const { check_status } = proxy.useDict('check_status')
const { dispose_status } = proxy.useDict('dispose_status')

const checkList = ref([])
const stockCheckDetailList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedStockCheckDetail = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const warehouseList = ref([])
const warehouseMap=ref(new Map())
const materialList = ref([])
const materialMap=ref(new Map())

const data = reactive({
  // 原料下拉列表
  materialOptions: [],
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    checkNo: null,
    warehouseId: null,
    checkDate: null,
    checkType: null,
    status: null,
  },
  rules: {
    checkNo: [
      { required: true, message: "盘点单号不能为空", trigger: "blur" },
      { min: 0, max: 50, message: "长度不能超过50个字符", trigger: "blur" }
    ],
    warehouseId: [
      { required: true, message: "仓库不能为空", trigger: "change" }
    ],
    checkDate: [
      { required: true, message: "盘点日期不能为空", trigger: "blur" }
    ],
    checkType: [
      { required: true, message: "盘点类型(1:全部盘点,2:部分盘点)不能为空", trigger: "change" }
    ],
    status: [
      { required: true, message: "盘点状态(1:进行中,2:已完成,3:已取消)不能为空", trigger: "change" }
    ],
    totalCount: [
      { required: true, message: "总盘点数量不能为空", trigger: "blur" }
    ],
    discrepancyCount: [
      { required: true, message: "差异数量不能为空", trigger: "blur" }
    ],
    operatorId: [
      { required: true, message: "操作人ID不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询库存盘点列表 */
function getList() {
  loading.value = true
  listCheck(queryParams.value).then(response => {
    checkList.value = response.rows
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
    checkId: null,
    checkNo: null,
    warehouseId: null,
    checkDate: null,
    checkType: null,
    status: null,
    totalCount: null,
    discrepancyCount: null,
    operatorId: null,
    remark: null,
    createTime: null,
    updateTime: null
  }
  stockCheckDetailList.value = []
  proxy.resetForm("checkRef")
}

/** 查询仓库列表 */
function getWarehouseList() {
  listWarehouse().then(response => {
    warehouseList.value = response.rows
    warehouseList.value.forEach(item => {
      warehouseMap.value.set(item.warehouseId, item.warehouseName)
    })
  })
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
  ids.value = selection.map(item => item.checkId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  // 预先生成盘点单号
  generateCheckNo().then(response => {
    if (response.code === 200) {
      if (response.msg) {
        form.value.checkNo = response.msg
      } else {
        // 处理后端返回200但msg为空的情况
        console.log(response)
        proxy.$modal.msgError('生成的盘点单号为空，请稍后重试')
      }
      // 无论是否成功生成单号，都打开新增表单
      open.value = true
      title.value = "添加库存盘点"
    } else {
      proxy.$modal.msgError(`生成失败：${response.msg || '未知错误'}`)
      // 即使生成单号失败，也允许用户打开表单
      open.value = true
      title.value = "添加库存盘点"
    }
  }).catch(error => {
    // 打印详细错误信息（含状态码、响应内容）
    console.error('生成盘点单号错误详情：', error.response || error)
    const errorMsg = error.response?.data?.msg || error.message || '网络异常'
    proxy.$modal.msgError(`生成盘点单号失败：${errorMsg}`)
    // 即使发生异常，也允许用户打开表单
    open.value = true
    title.value = "添加库存盘点"
  })
}
/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _checkId = row.checkId || ids.value
  getCheck(_checkId).then(response => {
    form.value = response.data
    stockCheckDetailList.value = response.data.stockCheckDetailList
    open.value = true
    title.value = "修改库存盘点"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["checkRef"].validate(valid => {
    if (valid) {
      // 确保所有子表的checkId与主表一致
      if (form.value.checkId != null) {
        stockCheckDetailList.value.forEach(item => {
          item.checkId = form.value.checkId
        })
      }
      
      form.value.stockCheckDetailList = stockCheckDetailList.value
      
      if (form.value.checkId != null) {
        updateCheck(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addCheck(form.value).then(response => {
          // 保存成功后，获取返回的checkId并更新所有子表数据
          if (response.code === 200 && response.data && response.data.checkId) {
            stockCheckDetailList.value.forEach(item => {
              item.checkId = response.data.checkId
            })
          }
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
  const _checkIds = row.checkId || ids.value
  proxy.$modal.confirm('是否确认删除库存盘点编号为"' + _checkIds + '"的数据项？').then(function() {
    return delCheck(_checkIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 库存盘点明细序号 */
function rowStockCheckDetailIndex({ row, rowIndex }) {
  row.index = rowIndex + 1
}

/** 库存盘点明细添加按钮操作 */
function handleAddStockCheckDetail() {
  let obj = {}
  obj.materialId = ""
  obj.systemQuantity = ""
  obj.actualQuantity = ""
  obj.discrepancyQuantity = ""
  obj.unit = ""
  obj.status = ""
  obj.adjustmentTime = ""
  stockCheckDetailList.value.push(obj)
}

/** 库存盘点明细删除按钮操作 */
function handleDeleteStockCheckDetail() {
  if (checkedStockCheckDetail.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的库存盘点明细数据")
  } else {
    const stockCheckDetails = stockCheckDetailList.value
    const checkedStockCheckDetails = checkedStockCheckDetail.value
    stockCheckDetailList.value = stockCheckDetails.filter(function(item) {
      return checkedStockCheckDetails.indexOf(item.index) == -1
    })
  }
}

/** 复选框选中数据 */
function handleStockCheckDetailSelectionChange(selection) {
  checkedStockCheckDetail.value = selection.map(item => item.index)
}

/** 详情按钮操作 */
function handleViewDetail(row) {
  // 跳转到盘点明细页面，并传递checkId作为查询参数
  proxy.$router.push({
    path: '/stock/detail',
    query: {
      checkId: row.checkId
    }
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('stock/check/export', {
    ...queryParams.value
  }, `check_${new Date().getTime()}.xlsx`)
}

/** 获取原料列表 */
function getMaterialList() {
  listInfo({}).then(response => {
      materialList.value = response.rows
      // 构建materialMap用于显示原料名称
      materialList.value.forEach((item) => {
        materialMap.value.set(item.materialId, item.materialName)
      })
      // 格式化原料列表为下拉框选项格式
      data.materialOptions = materialList.value.map(item => ({
        value: item.materialId,
        label: item.materialName + ' - ' + item.specification 
      }))
  }).catch(error => {
    console.error('获取原料列表失败:', error)
    data.materialOptions = []
    proxy.$modal.msgError('获取原料信息失败，请稍后重试')
  })
}

getWarehouseList()
getMaterialList()
getList()
</script>
