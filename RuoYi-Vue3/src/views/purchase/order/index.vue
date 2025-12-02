<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="采购单号" prop="purchaseNo">
        <el-input
          v-model="queryParams.purchaseNo"
          placeholder="请输入采购单号"
          clearable
          @keyup.enter="handleQuery"
           style="width: 120px;"
        />
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select
          v-model="queryParams.supplierId"
          placeholder="请选择供应商"
          clearable
          style="width: 200px;"
          @change="handleQuery"
        >
          <el-option
            v-for="supplier in supplierList"
            :key="supplier.supplierId"
            :label="supplier.supplierName"
            :value="supplier.supplierId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="总金额" prop="totalAmount">
        <el-input
          v-model="queryParams.totalAmount"
          placeholder="请输入总金额"
          clearable
          @keyup.enter="handleQuery"
           style="width: 120px;"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 120px;">
          <el-option
            v-for="dict in purchase_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审核时间" prop="auditTime" >
        <el-date-picker clearable
          v-model="queryParams.auditTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择审核时间"
           style="width: 145px;">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成时间" prop="completionTime">
        <el-date-picker clearable
          v-model="queryParams.completionTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择完成时间"
           style="width: 145px;">
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
          v-hasPermi="['purchase:order:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['purchase:order:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['purchase:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['purchase:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="采购订单ID" align="center" prop="purchaseId" />
      <el-table-column label="采购单号" align="center" prop="purchaseNo" />
      <el-table-column label="供应商" align="center" prop="supplierId">
        <template #default="scope">
          <span>{{ supplierMap.get(scope.row.supplierId)||scope.row.supplierId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="purchase_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="创建人ID" align="center" prop="creatorId" />
      <el-table-column label="审核人ID" align="center" prop="auditorId" />
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
      <el-table-column label="审核时间" align="center" prop="auditTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="completionTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.completionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="View" @click="handleDetail(scope.row)">详情</el-button>
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['purchase:order:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['purchase:order:remove']">删除</el-button>
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

    <!-- 添加或修改采购订单对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body class="purchase-dialog">
      <el-form ref="orderRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="采购单号" prop="purchaseNo">
          <el-input v-model="form.purchaseNo" placeholder="请输入采购单号" id="purchaseNo">
            <template #append>
              <el-button @click="regeneratePurchaseOrderNo" type="primary" size="small" icon="Refresh" title="重新生成采购单号" />
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable style="width: 200px;" id="supplierId">
            <el-option
              v-for="supplier in supplierList"
              :key="supplier.supplierId"
              :label="supplier.supplierName"
              :value="supplier.supplierId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入总金额" id="totalAmount" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status" id="status">
            <el-radio
              v-for="dict in purchase_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" id="remark" />
        </el-form-item>
        <el-form-item label="创建人ID" prop="creatorId">
          <el-input v-model="form.creatorId" placeholder="请输入创建人ID" id="creatorId" />
        </el-form-item>
        <el-form-item label="审核人ID" prop="auditorId">
          <el-input v-model="form.auditorId" placeholder="请输入审核人ID" id="auditorId" />
        </el-form-item>
        <el-form-item label="审核时间" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择审核时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="完成时间" prop="completionTime">
          <el-date-picker clearable
            v-model="form.completionTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择完成时间">
          </el-date-picker>
        </el-form-item>
        <el-divider content-position="center">采购明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddPurchaseDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeletePurchaseDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="purchaseDetailList" :row-class-name="rowPurchaseDetailIndex" @selection-change="handlePurchaseDetailSelectionChange" ref="purchaseDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <!-- 隐藏原料ID列，前端不展示 -->
          <!-- <el-table-column prop="materialId" label="原料ID" width="0" show-overflow-tooltip >
            <template #default="scope">
              <el-input v-model="scope.row.materialId"/>
            </template>
          </el-table-column> -->
          <el-table-column label="原料名称" prop="materialName" width="180">
            <template #default="scope">
              <el-select v-model="scope.row.materialName" placeholder="请选择原料" @change="handleMaterialChange(scope.row, scope.$index)">
                <el-option
                  v-for="material in materialList"
                  :key="material.materialId"
                  :label="material.materialName"
                  :value="material.materialName"
                />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="规格" prop="specification" width="120">
            <template #default="scope">
              <el-input v-model="scope.row.specification" placeholder="请输入规格" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="80">
            <template #default="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位" />
            </template>
          </el-table-column>
          <el-table-column label="采购数量" prop="quantity" width="100">
            <template #default="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入采购数量" @input="calculateAmount(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="unitPrice" width="100">
            <template #default="scope">
              <el-input v-model="scope.row.unitPrice" placeholder="请输入单价" @input="calculateAmount(scope.row)" />
            </template>
          </el-table-column>
          <el-table-column label="金额" prop="amount" width="100">
            <template #default="scope">
              <el-input v-model="scope.row.amount" placeholder="请输入金额" />
            </template>
          </el-table-column>
          <el-table-column label="实际到货数量" prop="actualQuantity" width="120">
            <template #default="scope">
              <el-input v-model="scope.row.actualQuantity" placeholder="请输入实际到货数量" />
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

<script setup name="Order">
import { listOrder, getOrder, delOrder, addOrder, updateOrder, generatePurchaseNo } from "@/api/purchase/order"
import { listInfo } from "@/api/material/info"
import { listSupplier } from "@/api/material/supplier"

const { proxy } = getCurrentInstance()
const { purchase_status } = proxy.useDict('purchase_status')

const orderList = ref([])
const purchaseDetailList = ref([])
const materialList = ref([])
const supplierList = ref([])
const supplierMap = ref(new Map())
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedPurchaseDetail = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    purchaseNo: null,
    supplierId: null,
    totalAmount: null,
    status: null,
    auditTime: null,
    completionTime: null
  },
  rules: {
    purchaseNo: [
      { required: true, message: "采购单号不能为空", trigger: "blur" }
    ],
    supplierId: [
      { required: true, message: "供应商不能为空", trigger: "blur" }
    ],
    totalAmount: [
      { required: true, message: "总金额不能为空", trigger: "blur" }
    ],
    status: [
      { required: true, message: "状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

  /** 查询采购订单列表 */
  function getList() {
    loading.value = true
    listOrder(queryParams.value).then(response => {
      orderList.value = response.rows
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
      purchaseId: null,
      purchaseNo: null,
      supplierId: null,
      totalAmount: null,
      status: null,
      remark: null,
      creatorId: null,
      auditorId: null,
      createTime: null,
      updateTime: null,
      auditTime: null,
      completionTime: null
    }
    purchaseDetailList.value = []
    proxy.resetForm("orderRef")
  }

  /** 生成采购单号 */
  async function generatePurchaseOrderNo() {
    try {
      // 先清空原采购单号
      form.value.purchaseNo = ''
      // 调用接口生成采购单号
      const response = await generatePurchaseNo()
      // console.log('生成采购单号接口响应:', response)
      // 检查响应是否成功
      if (response.code === 200) {
        // 设置自动生成的采购单号
        form.value.purchaseNo = response.msg
        proxy.$modal.msgSuccess('采购单号已生成')
      } else {
        proxy.$modal.msgError('生成采购单号失败: ' + (response.msg || '未知错误'))
      }
    } catch (error) {
      proxy.$modal.msgError('生成采购单号异常: ' + (error.message || '网络错误'))
    }
  }

  /** 重新生成采购单号 */
    async function regeneratePurchaseOrderNo() {
      generatePurchaseOrderNo()
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
      ids.value = selection.map(item => item.purchaseId)
      single.value = selection.length != 1
      multiple.value = !selection.length
    }

/** 新增按钮操作 */
    function handleAdd() {
      reset()
      open.value = true
      title.value = "添加采购订单"
      loadMaterialList()
      loadSupplierList()
      generatePurchaseOrderNo()

  //     try {
  //   console.log('开始调用生成采购单号接口')
  //   // 调用接口生成采购单号
  //   const response = await generatePurchaseNo()
  //   console.log('生成采购单号接口响应:', response)
  //   // 检查响应是否成功
  //   if (response.code === 200) {
  //     // 设置自动生成的采购单号
  //     form.value.purchaseNo = response.msg
  //     console.log('生成的采购单号:', response.msg)
  //   } else {
  //     console.error('生成采购单号失败:', response)
  //     proxy.$modal.msgError('生成采购单号失败: ' + (response.msg || '未知错误'))
  //   }
    
  //   // 生成当前时间作为支付时间（精确到时分秒）
  //   const now = new Date()
  //   form.value.paymentTime = formatDateTime(now)
  // } catch (error) {
  //   console.error('生成订单编号异常:', error)
  //   // 如果接口调用失败，显示提示信息
  //   proxy.$modal.msgError('生成订单编号异常: ' + (error.message || '网络错误'))
  // }
    }

/** 加载原料列表 */
    function loadMaterialList() {
      listInfo().then(response => {
        materialList.value = response.rows
      })
    }

    /** 加载供应商列表 */
    function loadSupplierList() {
      listSupplier().then(response => {
        supplierList.value = response.rows
        // 构建供应商ID到名称的映射
        const map = new Map()
        response.rows.forEach(supplier => {
          map.set(supplier.supplierId, supplier.supplierName)
        })
        supplierMap.value = map
      })
    }

/** 查看详情 */
  function handleDetail(row) {
    // 跳转到采购明细表，带上采购订单ID作为查询参数
    proxy.$router.push({
      path: '/purchase/detail',
      query: { purchaseId: row.purchaseId }
    })
  }

/** 修改按钮操作 */
    function handleUpdate(row) {
      reset()
      const _purchaseId = row.purchaseId || ids.value
      getOrder(_purchaseId).then(response => {
        form.value = response.data
        purchaseDetailList.value = response.data.purchaseDetailList
        open.value = true
        title.value = "修改采购订单"
        loadMaterialList()
        loadSupplierList()
      })
    }

  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["orderRef"].validate(valid => {
      if (valid) {
        form.value.purchaseDetailList = purchaseDetailList.value
        if (form.value.purchaseId != null) {
          updateOrder(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功")
            open.value = false
            getList()
          })
        } else {
          addOrder(form.value).then(response => {
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
      const _purchaseIds = row.purchaseId || ids.value
      proxy.$modal.confirm('是否确认删除采购订单编号为"' + _purchaseIds + '"的数据项？').then(function() {
        return delOrder(_purchaseIds)
      }).then(() => {
        getList()
        proxy.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }

/** 采购明细序号 */
    function rowPurchaseDetailIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    }

    /** 采购明细添加按钮操作 */
    function handleAddPurchaseDetail() {
      let obj = {}
      obj.materialId = ""
      obj.materialName = ""
      obj.specification = ""
      obj.unit = ""
      obj.quantity = ""
      obj.unitPrice = ""
      obj.amount = ""
      obj.actualQuantity = ""
      purchaseDetailList.value.push(obj)
    }

    /** 采购明细删除按钮操作 */
    function handleDeletePurchaseDetail() {
      if (checkedPurchaseDetail.value.length == 0) {
        proxy.$modal.msgError("请先选择要删除的采购明细数据")
      } else {
        const purchaseDetails = purchaseDetailList.value
        const checkedPurchaseDetails = checkedPurchaseDetail.value
        purchaseDetailList.value = purchaseDetails.filter(function(item) {
          return checkedPurchaseDetails.indexOf(item.index) == -1
        })
      }
    }

    /** 复选框选中数据 */
    function handlePurchaseDetailSelectionChange(selection) {
      checkedPurchaseDetail.value = selection.map(item => item.index)
    }

    /** 原料选择变化处理 */
    function handleMaterialChange(row, index) {
      // 查找选中的原料信息
      const selectedMaterial = materialList.value.find(material => material.materialName === row.materialName)
      if (selectedMaterial) {
        // 自动设置原料ID（前端不展示）
        row.materialId = selectedMaterial.materialId
        // 自动填充规格
        row.specification = selectedMaterial.specification || ''
        // 自动填充单位
        row.unit = selectedMaterial.unit || ''
        // 自动填充单价，优先使用采购单价
        row.unitPrice = selectedMaterial.purchasePrice || selectedMaterial.unitPrice || ''
        // 如果有数量，自动计算金额
        if (row.quantity) {
          calculateAmount(row)
        }
      }
}

    /** 计算金额 */
    function calculateAmount(row) {
      const quantity = parseFloat(row.quantity || 0)
      const unitPrice = parseFloat(row.unitPrice || 0)
      if (!isNaN(quantity) && !isNaN(unitPrice)) {
        row.amount = (quantity * unitPrice).toFixed(2)
        // 重新计算总金额
        calculateTotalAmount()
      }
    }

    /** 计算总金额 */
    function calculateTotalAmount() {
      let total = 0
      purchaseDetailList.value.forEach(item => {
        if (item.amount) {
          total += parseFloat(item.amount)
        }
      })
      form.value.totalAmount = total.toFixed(2)
    }

/** 导出按钮操作 */
    function handleExport() {
      proxy.download('purchase/order/export', {
        ...queryParams.value
      }, `order_${new Date().getTime()}.xlsx`)
    }

// 初始加载供应商列表和采购订单列表
  loadSupplierList()
  getList()
</script>

<style scoped>
.purchase-dialog .el-dialog__body {
  max-height: 60vh;
  overflow-y: auto;
  padding: 15px;
}

.purchase-dialog .el-table {
  font-size: 14px;
}

.purchase-dialog .el-table__row td {
  padding: 8px 0;
}

.purchase-dialog .el-input {
  width: 100%;
}

/* 确保表格不会超出对话框宽度 */
.purchase-dialog .el-table__header-wrapper,
.purchase-dialog .el-table__body-wrapper {
  overflow-x: auto;
}
</style>
