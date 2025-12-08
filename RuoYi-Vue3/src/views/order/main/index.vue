<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNo">
        <el-input v-model="queryParams.orderNo" placeholder="请输入订单编号" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="顾客姓名" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入顾客姓名" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="总金额" prop="totalAmount">
        <el-input v-model="queryParams.totalAmount" placeholder="请输入总金额" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="" prop="orderType" style="width: 100px;">
        <el-select v-model="queryParams.orderType" placeholder="订单类型" clearable>
          <el-option v-for="dict in order_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="status" style="width: 100px;">
        <el-select v-model="queryParams.status" placeholder="订单状态" clearable>
          <el-option v-for="dict in order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <br>
      <el-form-item label="桌号" prop="tableNo">
        <el-input v-model="queryParams.tableNo" placeholder="请输入桌号(堂食)" clearable @keyup.enter="handleQuery" />
      </el-form-item>
      <el-form-item label="支付时间" prop="paymentTime">
        <el-date-picker clearable v-model="queryParams.paymentTime" type="date" value-format="YYYY-MM-DD"
          placeholder="请选择支付时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="完成时间" prop="completionTime">
        <el-date-picker clearable v-model="queryParams.completionTime" type="date" value-format="YYYY-MM-DD"
          placeholder="请选择完成时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="" prop="paymentStatus" style="width: 100px;">
        <el-select v-model="queryParams.paymentStatus" placeholder="支付状态" clearable>
          <el-option v-for="dict in pay_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item prop="paymentType" style="width: 100px;">
        <el-select v-model="queryParams.paymentType" placeholder="支付方式" clearable>
          <el-option v-for="dict in pay_way" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="Plus" @click="handleAdd" v-hasPermi="['order:main:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="Edit" :disabled="single" @click="handleUpdate"
          v-hasPermi="['order:main:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="Delete" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['order:main:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="Download" @click="handleExport"
          v-hasPermi="['order:main:export']">导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="mainList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="orderId" />
      <!--<el-table-column label="订单编号" align="center" prop="orderNo" />
      <el-table-column label="顾客ID" align="center" prop="customerId" />-->
      <el-table-column label="顾客姓名" align="center" prop="customerName" />
      <el-table-column label="顾客电话" align="center" prop="customerPhone" />
      <el-table-column label="总金额" align="center" prop="totalAmount" />
      <el-table-column label="实际支付" align="center" prop="actualAmount" />
      <el-table-column label="优惠金额" align="center" prop="discountAmount" />
      <el-table-column label="订单类型" align="center" prop="orderType">
        <template #default="scope">
          <dict-tag :options="order_type" :value="scope.row.orderType" />
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="order_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <!--<el-table-column label="支付状态" align="center" prop="paymentStatus">
        <template #default="scope">
          <dict-tag :options="pay_status" :value="scope.row.paymentStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="支付方式" align="center" prop="paymentType">
        <template #default="scope">
          <dict-tag :options="pay_way" :value="scope.row.paymentType"/>
        </template>
      </el-table-column>
      <el-table-column label="位置信息" align="center">
        <template #default="scope">
          <span v-if="scope.row.orderType === 1" style="color: #409EFF;">{{ scope.row.tableNo }} (堂食)</span>
          <span v-else-if="scope.row.orderType === 2" style="color: #67C23A;">{{ scope.row.deliveryAddress }} (外卖)</span>
          <span v-else-if="scope.row.orderType === 3" style="color: #E6A23C;">自提</span>
          <span v-else>{{ scope.row.tableNo || scope.row.deliveryAddress }}</span>
        </template>
      </el-table-column>-->
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['order:main:edit']">修改</el-button>
          <el-button link type="primary" icon="Document" @click="handleDetail(scope.row)">详情</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)"
            v-hasPermi="['order:main:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" v-model:page="queryParams.pageNum" v-model:limit="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改客户订单对话框 -->
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body>
      <el-form ref="mainRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="订单编号" prop="orderNo">
              <el-input v-model="form.orderNo" placeholder="请输入订单编号" clearable id="orderNo">
                <template #append>
                  <el-button @click="regenerateOrderNo" type="primary" size="small" icon="Refresh" title="重新生成订单编号" />
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="顾客ID" prop="customerId">
              <el-select v-model="form.customerId" placeholder="请选择顾客" id="customerId" @change="handleCustomerChange">
                <el-option
                  v-for="customer in customerList"
                  :key="customer.customerId"
                  :label="customer.customerName"
                  :value="customer.customerId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="顾客姓名" prop="customerName">
              <el-input v-model="form.customerName" placeholder="请输入顾客姓名" id="customerName" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="顾客电话" prop="customerPhone">
              <el-input v-model="form.customerPhone" placeholder="请输入顾客电话" id="customerPhone" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="总金额" prop="totalAmount">
              <el-input v-model="form.totalAmount" placeholder="自动计算" id="totalAmount" :disabled="true" />
              <div class="el-form-item__help"></div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="实际支付" prop="actualAmount">
              <el-input v-model="form.actualAmount" placeholder="默认为总金额，可修改" id="actualAmount"
                @change="calculateDiscountAmount" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="优惠金额" prop="discountAmount">
              <el-input v-model="form.discountAmount" placeholder="自动计算" id="discountAmount" :disabled="true" />
              <div class="el-form-item__help"></div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="订单类型" prop="orderType">
              <el-select v-model="form.orderType" placeholder="请选择订单类型" @change="handleOrderTypeChange" id="orderType">
                <el-option v-for="dict in order_type" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="订单状态" prop="status">
          <el-radio-group v-model="form.status" id="status">
            <el-radio v-for="dict in order_status" :key="dict.value"
              :label="parseInt(dict.value)">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="支付状态" prop="paymentStatus">
          <el-radio-group v-model="form.paymentStatus" id="paymentStatus">
            <el-radio v-for="dict in pay_status" :key="dict.value"
              :label="parseInt(dict.value)">{{ dict.label }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="完成时间" prop="completionTime">
              <el-date-picker clearable v-model="form.completionTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择完成时间" id="completionTime">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作人ID" prop="operatorId">
              <el-input v-model="form.operatorId" placeholder="请输入操作人ID" id="operatorId" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="支付时间" prop="paymentTime">
              <el-date-picker clearable v-model="form.paymentTime" type="datetime" value-format="YYYY-MM-DD HH:mm:ss"
                placeholder="请选择支付时间" id="paymentTime">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="支付方式" prop="paymentType">
              <el-select v-model="form.paymentType" placeholder="请选择支付方式" id="paymentType">
                <el-option v-for="dict in pay_way" :key="dict.value" :label="dict.label"
                  :value="parseInt(dict.value)"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" id="remark" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="桌号(堂食)" prop="tableNo" v-show="form.orderType === 1">
              <el-input v-model="form.tableNo" placeholder="请输入桌号(堂食)" id="tableNo" />
            </el-form-item>
            <el-form-item label="配送地址(外卖)" prop="deliveryAddress" v-show="form.orderType === 2">
              <el-input v-model="form.deliveryAddress" placeholder="请输入配送地址" id="deliveryAddress" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="center">订单明细信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddOrderDetail">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteOrderDetail">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="orderDetailList" :row-class-name="rowOrderDetailIndex"
          @selection-change="handleOrderDetailSelectionChange" ref="orderDetail">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50" />
          <el-table-column label="产品名称" prop="productName" width="180">
            <template #default="scope">
              <el-select v-model="scope.row.productName" placeholder="请选择产品名称"
                @change="handleProductChange(scope.row, $event)">
                <el-option v-for="product in productList" :key="product.productId" :label="product.productName"
                  :value="product.productName"></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="数量" prop="quantity" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入数量" @change="calculateSubtotal(scope.row)" />
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
          <el-table-column label="定制要求" prop="customization" width="200">
            <template #default="scope">
              <div v-if="scope.row.customization" class="customization-tags">
                <!-- 将定制要求字符串解析为数组 -->
                <template v-for="(item, index) in parseCustomization(scope.row.customization)" :key="index">
                  <el-tag
                    size="small"
                    :type="['primary', 'success', 'warning', 'danger', 'info'][index % 5]"
                    style="margin-right: 8px; margin-bottom: 8px;"
                  >
                    {{ item }}
                  </el-tag>
                </template>
              </div>
              <span v-else class="no-customization">无定制要求</span>
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

    <!-- 订单详情对话框 -->
    <el-dialog :title="'订单详情 - ' + detailForm.orderNo" v-model="detailOpen" width="1000px" append-to-body>
      <div class="detail-container">
        <h3>订单基本信息</h3>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="订单编号">{{ detailForm.orderNo }}</el-descriptions-item>
          <el-descriptions-item label="顾客姓名">{{ detailForm.customerName }}</el-descriptions-item>
          <el-descriptions-item label="顾客电话">{{ detailForm.customerPhone }}</el-descriptions-item>
          <el-descriptions-item label="总金额">{{ detailForm.totalAmount }}</el-descriptions-item>
          <el-descriptions-item label="实际支付金额">{{ detailForm.actualAmount }}</el-descriptions-item>
          <el-descriptions-item label="优惠金额">{{ detailForm.discountAmount }}</el-descriptions-item>
          <el-descriptions-item label="订单类型">
            <dict-tag :options="order_type" :value="detailForm.orderType" />
          </el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <dict-tag :options="order_status" :value="detailForm.status" />
          </el-descriptions-item>
          <el-descriptions-item label="支付状态">
            <dict-tag :options="pay_status" :value="detailForm.paymentStatus" />
          </el-descriptions-item>
          <el-descriptions-item label="支付方式">
            <dict-tag :options="pay_way" :value="detailForm.paymentType" />
          </el-descriptions-item>
          <el-descriptions-item label="位置信息" :span="2">
            <span v-if="detailForm.orderType === 1">桌号: {{ detailForm.tableNo }} (堂食)</span>
            <span v-else-if="detailForm.orderType === 2">配送地址: {{ detailForm.deliveryAddress }} (外卖)</span>
            <span v-else-if="detailForm.orderType === 3">自提</span>
          </el-descriptions-item>
          <el-descriptions-item label="备注" :span="2">{{ detailForm.remark || '-' }}</el-descriptions-item>
          <el-descriptions-item label="操作人ID">{{ detailForm.operatorId }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ parseTime(detailForm.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="支付时间">{{ parseTime(detailForm.paymentTime) }}</el-descriptions-item>
          <el-descriptions-item label="完成时间">{{ parseTime(detailForm.completionTime) }}</el-descriptions-item>
        </el-descriptions>

        <h3 style="margin-top: 30px;">订单明细</h3>
        <el-table :data="detailOrderItems" style="width: 100%">
          <el-table-column label="序号" type="index" width="50" />
          <el-table-column label="产品ID" prop="productId" />
          <el-table-column label="产品名称" prop="productName" />
          <el-table-column label="数量" prop="quantity" />
          <el-table-column label="单价" prop="price" />
          <el-table-column label="小计" prop="subtotal" />
          <el-table-column label="定制要求" prop="customization" width="200">
            <template #default="scope">
              <div v-if="scope.row.customization" class="customization-tags">
                <!-- 将定制要求字符串解析为数组 -->
                <template v-for="(item, index) in parseCustomization(scope.row.customization)" :key="index">
                  <el-tag
                    size="small"
                    :type="['primary', 'success', 'warning', 'danger', 'info'][index % 5]"
                    style="margin-right: 8px; margin-bottom: 8px;"
                  >
                    {{ item }}
                  </el-tag>
                </template>
              </div>
              <span v-else class="no-customization">无定制要求</span>
            </template>
          </el-table-column>
        </el-table>

        <!-- 订单状态流程图将在这里添加 -->
        <div id="orderStatusFlow" style="margin-top: 30px;">
          <h3>订单状态流程</h3>
          <div class="flow-container">
            <template v-for="(item, index) in filteredStatusFlowList" :key="item.key">
              <div class="flow-node" :class="{
                'active': currentStatusKey === item.key,  // 当前状态
                'completed': isCompleted(item.key)        // 已完成状态
              }">
                {{ item.name }}
              </div>
              <!-- 在每个节点后面添加箭头，除了最后一个节点 -->
              <div v-if="index < filteredStatusFlowList.length - 1" class="flow-line"
                :class="{ 'completed': isCompleted(filteredStatusFlowList[index + 1].key) }">
                <span class="arrow">→</span>
              </div>
            </template>
          </div>
        </div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="closeDetail">关闭</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 产品属性选择弹窗 -->
    <el-dialog title="选择产品属性" v-model="attributeDialogVisible" width="600px">

      <el-form>
        <el-form-item v-for="attribute in currentOrderDetail.attributes" :key="attribute.attributeId"
          :label="attribute.attributeName">
          <el-radio-group v-model="currentOrderDetail.selectedAttributes[attribute.attributeId]">
            <div v-for="values in attribute.attributeValues" :key="values" :label="values">

              <el-tag v-for="(value, index) in values"
              :key="index"
              size="small"
              :type="['primary', 'success', 'warning', 'danger', 'info'][index % 5]"
              style="margin-right: 8px;">
                <el-radio :label="value">{{ value }}</el-radio>
              </el-tag>
            </div>
          </el-radio-group>
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button @click="attributeDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmAttributeSelection">确 定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Main">
import { ref, reactive, computed } from 'vue'
import { listMain, getMain, delMain, addMain, updateMain, generateOrderNo } from "@/api/order/main"
import { listCustomer } from "@/api/order/customer"
import { listInfo as listProductInfo } from "@/api/product/info"
import { listAttribute } from "@/api/product/attribute"
import { parseTime } from '@/utils/ruoyi'

// 格式化日期时间为指定格式
function formatDateTime(date) {
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}

const { proxy } = getCurrentInstance()
const { pay_status, order_status, order_type, pay_way } = proxy.useDict('pay_status', 'order_status', 'order_type', 'pay_way')

// 获取客户列表
function getCustomerList() {
  listCustomer().then(response => {
    customerList.value = response.data
  }).catch(error => {
    console.error('获取客户列表失败:', error)
    customerList.value = []
  })
}

const mainList = ref([])
const orderDetailList = ref([])
const productList = ref([])
const open = ref(false)
const detailOpen = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedOrderDetail = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const currentOrderDetail = ref({})
const currentDetailIndex = ref(0)
const attributeDialogVisible = ref(false)
// 客户列表
const customerList = ref([])

// 订单详情数据
const detailForm = reactive({})

// 订单详情中的订单明细
const detailOrderItems = ref([])

// 订单状态流程图相关数据
const statusFlowList = [
  { name: '订单创建', key: 'created' },
  { name: '支付成功', key: 'paid' },
  { name: '开始制作', key: 'making' },
  { name: '制作完成', key: 'made' },
  { name: '待配送', key: 'waiting' },
  { name: '配送中', key: 'delivering' },
  { name: '配送完成', key: 'delivered' },
  { name: '订单完成', key: 'finished' }
]

// 根据订单类型动态过滤状态流程
const filteredStatusFlowList = computed(() => {
  // 堂食(1)或自取(3)类型不显示配送相关状态
  if (detailForm.orderType === 1 || detailForm.orderType === 3) {
    return statusFlowList.filter(item =>
      !['waiting', 'delivering', 'delivered'].includes(item.key)
    )
  }
  // 外卖类型(2)显示完整状态流程
  return statusFlowList
})

// 当前订单状态键
const currentStatusKey = ref('created')

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

// 组件挂载时获取产品列表和客户列表
onMounted(() => {
  getProductList()
  getCustomerList()
})

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
async function handleAdd() {
  reset()
  // 打开对话框
  open.value = true
  title.value = "添加客户订单"

  try {
    console.log('开始调用生成订单编号接口')
    // 调用接口生成订单编号
    const response = await generateOrderNo()
    console.log('生成订单编号接口响应:', response)
    // 检查响应是否成功
    if (response.code === 200) {
      // 设置自动生成的订单编号
      form.value.orderNo = response.msg
      console.log('生成的订单编号:', response.msg)
    } else {
      console.error('生成订单编号失败:', response)
      proxy.$modal.msgError('生成订单编号失败: ' + (response.msg || '未知错误'))
    }

    // 生成当前时间作为支付时间（精确到时分秒）
    const now = new Date()
    form.value.paymentTime = formatDateTime(now)
  } catch (error) {
    console.error('生成订单编号异常:', error)
    // 如果接口调用失败，显示提示信息
    proxy.$modal.msgError('生成订单编号异常: ' + (error.message || '网络错误'))
  }
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

/** 查看订单详情 */
async function handleDetail(row) {
  detailOpen.value = true
  // 重置详情表单
  Object.keys(detailForm).forEach(key => {
    delete detailForm[key]
  })

  try {
    // 获取订单详情数据
    const detailRes = await getMain(row.orderId)

    // 设置订单详情
    Object.assign(detailForm, detailRes.data)

    // 设置订单明细
    if (detailRes.data.orderDetailList) {
      detailOrderItems.value = detailRes.data.orderDetailList
    } else {
      detailOrderItems.value = []
    }



    // 根据订单状态设置当前状态键
    updateCurrentStatusKey(detailForm.status)
  } catch (error) {
    proxy.$modal.msgError('获取订单详情失败')
    console.error('获取订单详情失败:', error)
  }
}

/** 关闭详情弹窗 */
function closeDetail() {
  detailOpen.value = false
}

/** 更新当前状态键 */
function updateCurrentStatusKey(status) {
  // 根据订单状态映射到状态流程中的key
  const statusMap = {
    1: 'created',     // 待支付 -> 订单创建
    2: 'paid',        // 已支付 -> 支付成功
    3: 'making',      // 制作中 -> 开始制作
    4: 'made',        // 制作完成
    5: 'waiting',     // 待配送
    6: 'delivering',  // 配送中
    7: 'delivered',   // 已送达 -> 配送完成
    8: 'finished'     // 已完成 -> 订单完成
  }
  currentStatusKey.value = statusMap[status] || 'created'
}

/** 判断某个状态是否已完成 */
function isCompleted(statusKey) {
  const currentIndex = statusFlowList.findIndex(item => item.key === currentStatusKey.value)
  const targetIndex = statusFlowList.findIndex(item => item.key === statusKey)
  return targetIndex < currentIndex
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["mainRef"].validate(valid => {
    if (valid) {
      console.log('提交的表单数据:', form.value)
      // 校验订单类型与地址信息的匹配性
      if (form.value.orderType === 1 && (!form.value.tableNo || form.value.tableNo.trim() === '')) {
        proxy.$modal.msgError("堂食订单必须填写桌号")
        return
      }

      if (form.value.orderType === 2 &&
        (!form.value.deliveryAddress || form.value.deliveryAddress.trim() === '')) {
        proxy.$modal.msgError("外卖订单必须填写配送地址")
        return
      }

      if (form.value.orderType === 1 && form.value.deliveryAddress) {
        proxy.$modal.msgError("堂食订单不能填写配送地址")
        return
      }

      if ((form.value.orderType === 2) && form.value.tableNo) {
        proxy.$modal.msgError("外卖订单不能填写桌号")
        return
      }

      form.value.orderDetailList = orderDetailList.value
      if (form.value.orderId != null) {
        // 检查订单状态是否变更为完成状态（值为8）
        if (form.value.status === 8) {
          // 自动生成完成时间（精确到时分秒）
          form.value.completionTime = formatDateTime(new Date())
        }
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
  proxy.$modal.confirm('是否确认删除客户订单编号为"' + _orderIds + '"的数据项？').then(function () {
    return delMain(_orderIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => { })
}

/** 订单明细序号 */
function rowOrderDetailIndex({ row, rowIndex }) {
  row.index = rowIndex + 1
}

/** 订单明细添加按钮操作 */
function handleAddOrderDetail() {
  const obj = {};
  obj.productName = ""
  obj.productId = ""
  obj.quantity = 1
  obj.price = 0
  obj.subtotal = 0
  obj.customization = ""
  obj.attributes = [] // 产品属性列表
  obj.selectedAttributes = {} // 选中的属性值
  orderDetailList.value.push(obj)
}

function getProductList() {
  listProductInfo().then(response => {
    productList.value = response.rows
  })
}



// 处理产品选择变更，自动填充单价和产品ID
function handleProductChange(row, productName) {
  const selectedProduct = productList.value.find(p => p.productName === productName)
  if (selectedProduct) {
    row.price = selectedProduct.price || 0
    row.productId = selectedProduct.productId || ''
    // 自动计算小计
    calculateSubtotal(row)

    // 获取产品属性数据
    row.attributes = []
    row.selectedAttributes = {}

    const attributeParams = {
      productId: selectedProduct.productId
    }

    listAttribute(attributeParams).then(response => {
      if (response.code === 200 && response.rows && response.rows.length > 0) {
        // 解析属性值JSON并存储
        const attributes = response.rows.map(attr => {
          return {
            attributeId: attr.attributeId,
            attributeName: attr.attributeName,
            attributeValues: JSON.parse(attr.attributeValue)
          }
        })
        row.attributes = attributes

        // 获取当前订单明细在列表中的索引
        const index = orderDetailList.value.indexOf(row)

        // 如果有属性，自动弹出属性选择窗口
        if (attributes.length > 0) {
          showAttributeDialog(row, index)
        }
      }
    }).catch(error => {
      console.error('获取产品属性失败:', error)
    })
  }
}

// 显示属性选择弹窗
function showAttributeDialog(orderDetail, index) {
  // 设置当前操作的订单明细和索引
  currentOrderDetail.value = orderDetail
  currentDetailIndex.value = index

  // 打开属性选择弹窗
  attributeDialogVisible.value = true
}

// 根据属性ID获取属性名称
function getAttributeName(item, attrId) {
  if (!item.attributes) return ''
  const attribute = item.attributes.find(attr => attr.attributeId == attrId)
  return attribute ? attribute.attributeName : ''
}

// 解析定制要求字符串为数组
function parseCustomization(customization) {
  if (!customization) return []
  // 分割字符串并去除空格
  return customization.split(';').filter(item => item.trim()).map(item => item.trim())
}

// 确认属性选择
function confirmAttributeSelection() {
  // 关闭属性选择弹窗
  attributeDialogVisible.value = false

  // 可以在这里添加额外的逻辑，比如更新订单明细的定制要求字段
  const orderDetail = orderDetailList.value[currentDetailIndex.value]
  let customization = ''

  // 将选择的属性转换为字符串格式（可选，根据需求调整）
  for (const [attrId, value] of Object.entries(orderDetail.selectedAttributes)) {
    const attribute = orderDetail.attributes.find(attr => attr.attributeId == attrId)
    if (attribute) {
      customization += `${attribute.attributeName}: ${value}; `
    }
  }

  // 更新定制要求字段
  if (customization) {
    orderDetail.customization = customization.trim()
  }
}

// 计算小计
function calculateSubtotal(row) {
  const quantity = parseFloat(row.quantity) || 0
  const price = parseFloat(row.price) || 0
  row.subtotal = quantity * price
  calculateTotalAmount() // 更新总金额
}

// 计算总金额
function calculateTotalAmount() {
  let total = 0
  orderDetailList.value.forEach(item => {
    total += parseFloat(item.subtotal) || 0
  })
  form.value.totalAmount = total.toFixed(2)

  // 如果实际支付金额未设置或为0，则默认为总金额
  if (!form.value.actualAmount || parseFloat(form.value.actualAmount) === 0) {
    form.value.actualAmount = form.value.totalAmount
  }

  calculateDiscountAmount() // 更新优惠金额
}

// 计算优惠金额
function calculateDiscountAmount() {
  const total = parseFloat(form.value.totalAmount) || 0
  const actual = parseFloat(form.value.actualAmount) || 0
  form.value.discountAmount = (total - actual).toFixed(2)
}

/** 订单明细删除按钮操作 */
function handleDeleteOrderDetail() {
  if (checkedOrderDetail.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的订单明细数据")
  } else {
    const orderDetails = orderDetailList.value
    const checkedOrderDetails = checkedOrderDetail.value
    orderDetailList.value = orderDetails.filter(function (item) {
      return checkedOrderDetails.indexOf(item.index) == -1
    })
  }
}

/** 订单类型切换处理 */
function handleOrderTypeChange(value) {
  // 清除之前的数据
  form.value.tableNo = null
  form.value.deliveryAddress = null
}

/** 重新生成订单编号 */
async function regenerateOrderNo() {
  try {
    console.log('开始重新生成订单编号')
    // 先清空原订单编号
    form.value.orderNo = ''
    // 调用接口生成订单编号
    const response = await generateOrderNo()
    // console.log('重新生成订单编号接口响应:', response)
    // 检查响应是否成功
    if (response.code === 200) {
      // 设置自动生成的订单编号
      form.value.orderNo = response.msg
      proxy.$modal.msgSuccess('订单编号已重新生成')
    } else {
      // console.error('重新生成订单编号失败:', response)
      proxy.$modal.msgError('重新生成订单编号失败: ' + (response.msg || '未知错误'))
    }
  } catch (error) {
    // console.error('重新生成订单编号异常:', error)
    proxy.$modal.msgError('重新生成订单编号异常: ' + (error.message || '网络错误'))
  }
}

// 处理客户选择变化
function handleCustomerChange(customerId) {
  if (customerId) {
    const selectedCustomer = customerList.value.find(customer => customer.customerId === customerId)
    if (selectedCustomer) {
      form.value.customerName = selectedCustomer.customerName
      form.value.customerPhone = selectedCustomer.customerPhone
    }
  } else {
    form.value.customerName = null
    form.value.customerPhone = null
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

<style scoped>
.app-container {
  padding: 20px;
}

.detail-container {
  line-height: 1.8;
}

.detail-container h3 {
  margin-bottom: 15px;
  color: #333;
  font-weight: 600;
}

/* 订单状态流程图样式 */
.flow-container {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  margin-top: 10px;
  padding: 20px 0;
}

/* 节点样式 */
.flow-node {
  padding: 8px 16px;
  border-radius: 4px;
  background: #f0f0f0;
  /* 未完成状态背景 */
  color: #666;
  font-size: 14px;
  white-space: nowrap;
  display: inline-block;
}

/* 已完成节点 */
.flow-node.completed {
  background: #dff0d8;
  /* 浅绿色 */
  color: #3c763d;
}

/* 当前节点 */
.flow-node.active {
  background: #d9edf7;
  /* 浅蓝色 */
  color: #31708f;
  border: 1px solid #bce8f1;
  font-weight: 600;
}

/* 连线样式 */
.flow-line {
  color: #ccc;
  /* 未完成连线颜色 */
  font-size: 20px;
  margin: 0 5px;
  display: inline-block;
  vertical-align: middle;
}

/* 已完成连线 */
.flow-line.completed {
  color: #5cb85c;  /* 绿色箭头 */
}

/* 定制要求标签样式 */
.customization-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  padding: 4px;
}

.no-customization {
  color: #909399;
  font-style: italic;
  padding: 4px;
}


/* 响应式调整 */
@media (max-width: 768px) {
  .flow-container {
    flex-direction: column;
    align-items: flex-start;
  }

  .flow-line {
    transform: rotate(90deg);
  }
}
</style>