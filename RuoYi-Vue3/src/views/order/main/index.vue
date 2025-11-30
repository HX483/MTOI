<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单编号"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="顾客姓名" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入顾客姓名"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="总金额" prop="totalAmount">
        <el-input
          v-model="queryParams.totalAmount"
          placeholder="请输入总金额"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="" prop="orderType" style="width: 100px;">
        <el-select v-model="queryParams.orderType" placeholder="订单类型" clearable>
          <el-option
              v-for="dict in order_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="status" style="width: 100px;">
        <el-select v-model="queryParams.status" placeholder="订单状态" clearable>
          <el-option
              v-for="dict in order_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="桌号" prop="tableNo" >
        <el-input
          v-model="queryParams.tableNo"
          placeholder="请输入桌号(堂食)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="支付时间" prop="paymentTime">
        <el-date-picker clearable
          v-model="queryParams.paymentTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择支付时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成时间" prop="completionTime">
        <el-date-picker clearable
          v-model="queryParams.completionTime"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择完成时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="" prop="paymentStatus" style="width: 100px;">
        <el-select v-model="queryParams.paymentStatus" placeholder="支付状态" clearable>
          <el-option
              v-for="dict in pay_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item  prop="paymentType" style="width: 100px;">
        <el-select v-model="queryParams.paymentType" placeholder="支付方式" clearable>
          <el-option
              v-for="dict in pay_way"
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
          v-hasPermi="['order:main:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['order:main:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['order:main:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['order:main:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mainList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="orderId" />
      <el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="顾客ID" align="center" prop="customerId" />
      <el-table-column label="顾客姓名" align="center" prop="customerName" />
      <el-table-column label="顾客电话" align="center" prop="customerPhone" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="实际支付金额" align="center" prop="actualAmount" />
      <el-table-column label="优惠金额" align="center" prop="discountAmount" />
      <el-table-column label="订单类型" align="center" prop="orderType">
        <template #default="scope">
          <dict-tag :options="order_type" :value="scope.row.orderType"/>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="paymentStatus">
        <template #default="scope">
          <dict-tag :options="pay_status" :value="scope.row.paymentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" align="center" prop="paymentType">
        <template #default="scope">
          <dict-tag :options="pay_way" :value="scope.row.paymentType"/>
        </template>
      </el-table-column>
      <el-table-column label="桌号(堂食)" align="center" prop="tableNo" />
      <el-table-column label="配送地址(外卖)" align="center" prop="deliveryAddress" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作人ID" align="center" prop="operatorId" />
      <el-table-column label="支付时间" align="center" prop="paymentTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.paymentTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="completionTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.completionTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['order:main:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['order:main:remove']">删除</el-button>
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

    <!-- 添加或修改客户订单对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="mainRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单编号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单编号" />
        </el-form-item>
        <el-form-item label="顾客ID" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入顾客ID" />
        </el-form-item>
        <el-form-item label="顾客姓名" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入顾客姓名" />
        </el-form-item>
        <el-form-item label="顾客电话" prop="customerPhone">
          <el-input v-model="form.customerPhone" placeholder="请输入顾客电话" />
        </el-form-item>
        <el-form-item label="总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入总金额" />
        </el-form-item>
        <el-form-item label="实际支付金额" prop="actualAmount">
          <el-input v-model="form.actualAmount" placeholder="请输入实际支付金额" />
        </el-form-item>
        <el-form-item label="优惠金额" prop="discountAmount">
          <el-input v-model="form.discountAmount" placeholder="请输入优惠金额" />
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型">
            <el-option
              v-for="dict in order_type"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="订单状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in order_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付状态" prop="paymentStatus">
          <el-radio-group v-model="form.paymentStatus">
            <el-radio
              v-for="dict in pay_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付方式" prop="paymentType">
          <el-select v-model="form.paymentType" placeholder="请选择支付方式">
            <el-option
              v-for="dict in pay_way"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="桌号(堂食)" prop="tableNo">
          <el-input v-model="form.tableNo" placeholder="请输入桌号(堂食)" />
        </el-form-item>
        <el-form-item label="配送地址(外卖)" prop="deliveryAddress">
          <el-input v-model="form.deliveryAddress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="操作人ID" prop="operatorId">
          <el-input v-model="form.operatorId" placeholder="请输入操作人ID" />
        </el-form-item>
        <el-form-item label="支付时间" prop="paymentTime">
          <el-date-picker clearable
            v-model="form.paymentTime"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择支付时间">
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
        <el-divider content-position="center">订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddOrderDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteOrderDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="orderDetailList" :row-class-name="rowOrderDetailIndex" @selection-change="handleOrderDetailSelectionChange" ref="orderDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="产品ID" prop="productId" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.productId" placeholder="请输入产品ID" />
            </template>
          </el-table-column>
          <el-table-column label="产品名称" prop="productName" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.productName" placeholder="请输入产品名称" />
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入数量" />
            </template>
          </el-table-column>
          <el-table-column label="单价" prop="price" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.price" placeholder="请输入单价" />
            </template>
          </el-table-column>
          <el-table-column label="小计" prop="subtotal" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.subtotal" placeholder="请输入小计" />
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

<script setup name="Main">
import { listMain, getMain, delMain, addMain, updateMain } from "@/api/order/main"

const { proxy } = getCurrentInstance()
const { pay_status, order_status, order_type, pay_way } = proxy.useDict('pay_status', 'order_status', 'order_type', 'pay_way')

const mainList = ref([])
const orderDetailList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedOrderDetail = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    orderNo: null,
    customerName: null,
    totalAmount: null,
    orderType: null,
    status: null,
    paymentStatus: null,
    paymentType: null,
    tableNo: null,
    deliveryAddress: null,
    paymentTime: null,
    completionTime: null
  },
  rules: {
    orderNo: [
      { required: true, message: "订单编号不能为空", trigger: "blur" }
    ],
    customerName: [
      { required: true, message: "顾客姓名不能为空", trigger: "blur" }
    ],
    customerPhone: [
      { required: true, message: "顾客电话不能为空", trigger: "blur" }
    ],
    totalAmount: [
      { required: true, message: "总金额不能为空", trigger: "blur" }
    ],
    actualAmount: [
      { required: true, message: "实际支付金额不能为空", trigger: "blur" }
    ],
    orderType: [
      { required: true, message: "订单类型不能为空", trigger: "change" }
    ],
    status: [
      { required: true, message: "订单状态不能为空", trigger: "change" }
    ],
    paymentStatus: [
      { required: true, message: "支付状态不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询客户订单列表 */
function getList() {
  loading.value = true
  listMain(queryParams.value).then(response => {
    mainList.value = response.rows
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
    orderId: null,
    orderNo: null,
    customerId: null,
    customerName: null,
    customerPhone: null,
    totalAmount: null,
    actualAmount: null,
    discountAmount: null,
    orderType: null,
    status: null,
    paymentStatus: null,
    paymentType: null,
    tableNo: null,
    deliveryAddress: null,
    remark: null,
    operatorId: null,
    createTime: null,
    updateTime: null,
    paymentTime: null,
    completionTime: null
  }
  orderDetailList.value = []
  proxy.resetForm("mainRef")
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
  ids.value = selection.map(item => item.orderId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加客户订单"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _orderId = row.orderId || ids.value
  getMain(_orderId).then(response => {
    form.value = response.data
    orderDetailList.value = response.data.orderDetailList
    open.value = true
    title.value = "修改客户订单"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["mainRef"].validate(valid => {
    if (valid) {
      form.value.orderDetailList = orderDetailList.value
      if (form.value.orderId != null) {
        updateMain(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addMain(form.value).then(response => {
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
  const _orderIds = row.orderId || ids.value
  proxy.$modal.confirm('是否确认删除客户订单编号为"' + _orderIds + '"的数据项？').then(function() {
    return delMain(_orderIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 订单明细序号 */
function rowOrderDetailIndex({ row, rowIndex }) {
  row.index = rowIndex + 1
}

/** 订单明细添加按钮操作 */
function handleAddOrderDetail() {
  let obj = {}
  obj.productId = ""
  obj.productName = ""
  obj.quantity = ""
  obj.price = ""
  obj.subtotal = ""
  obj.customization = ""
  orderDetailList.value.push(obj)
}

/** 订单明细删除按钮操作 */
function handleDeleteOrderDetail() {
  if (checkedOrderDetail.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的订单明细数据")
  } else {
    const orderDetails = orderDetailList.value
    const checkedOrderDetails = checkedOrderDetail.value
    orderDetailList.value = orderDetails.filter(function(item) {
      return checkedOrderDetails.indexOf(item.index) == -1
    })
  }
}

/** 复选框选中数据 */
function handleOrderDetailSelectionChange(selection) {
  checkedOrderDetail.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('order/main/export', {
    ...queryParams.value
  }, `main_${new Date().getTime()}.xlsx`)
}

getList()
</script>
