<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 综合仪表盘 -->
      <el-col :span="24">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <div class="card-header">
              <span>综合仪表盘</span>
              <el-button type="primary" icon="Refresh" @click="refreshDashboard">刷新</el-button>
            </div>
          </template>
          <div v-loading="dashboardLoading">
            <div v-if="stockStatistics || orderStatistics || purchaseStatistics">
              <el-row :gutter="20">
                <!-- 库存统计卡片 -->
                <el-col :span="6" v-for="(item, index) in stockCards" :key="index">
                  <el-card class="stat-card" shadow="never">
                    <div class="stat-content">
                      <div class="stat-value">{{ item.value }}</div>
                      <div class="stat-label">{{ item.label }}</div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              
              <!-- 订单统计卡片 -->
              <el-row :gutter="20" style="margin-top: 20px;">
                <el-col :span="6" v-for="(item, index) in orderCards" :key="index">
                  <el-card class="stat-card order-card" shadow="never">
                    <div class="stat-content">
                      <div class="stat-value">{{ item.value }}</div>
                      <div class="stat-label">{{ item.label }}</div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
              
              <!-- 采购统计卡片 -->
              <el-row :gutter="20" style="margin-top: 20px;">
                <el-col :span="6" v-for="(item, index) in purchaseCards" :key="index">
                  <el-card class="stat-card purchase-card" shadow="never">
                    <div class="stat-content">
                      <div class="stat-value">{{ item.value }}</div>
                      <div class="stat-label">{{ item.label }}</div>
                    </div>
                  </el-card>
                </el-col>
              </el-row>
            </div>
            <el-empty v-else description="暂无数据" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 库存统计图表 -->
      <el-col :span="12">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <span>库存统计</span>
          </template>
          <div v-loading="stockLoading">
            <div v-if="stockStatistics">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="总库存数量">{{ stockStatistics.totalQuantity }}</el-descriptions-item>
                <el-descriptions-item label="总库存价值">¥{{ formatNumber(stockStatistics.totalValue) }}</el-descriptions-item>
                <el-descriptions-item label="仓库数量">{{ stockStatistics.warehouseCount }}</el-descriptions-item>
                <el-descriptions-item label="原料种类数">{{ stockStatistics.materialCount }}</el-descriptions-item>
                <el-descriptions-item label="预警数量" :span="2">
                  <el-tag type="warning">{{ stockStatistics.alertCount }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
              
              <div style="margin-top: 20px;">
                <h4>按仓库库存数量统计</h4>
                <v-chart class="chart" :option="warehouseChartOption" autoresize />
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 盘点分析图表 -->
      <el-col :span="12">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <span>盘点分析</span>
          </template>
          <div v-loading="checkLoading">
            <div v-if="checkAnalysis">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="盘点总数">{{ checkAnalysis.totalChecks }}</el-descriptions-item>
                <el-descriptions-item label="进行中">
                  <el-tag type="info">{{ checkAnalysis.ongoingChecks }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="已完成">
                  <el-tag type="success">{{ checkAnalysis.completedChecks }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="总差异数量">
                  <el-tag type="danger">{{ checkAnalysis.totalDiscrepancy }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
              
              <div style="margin-top: 20px;">
                <h4>盘点状态分布</h4>
                <v-chart class="chart" :option="checkStatusChartOption" autoresize />
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 库存预警 -->
      <el-col :span="12">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <span>库存预警</span>
          </template>
          <div v-loading="alertLoading">
            <el-table :data="alertList" style="width: 100%" max-height="300">
              <el-table-column prop="material_name" label="原料名称" />
              <el-table-column prop="warehouse_name" label="仓库" />
              <el-table-column prop="quantity" label="当前库存" />
              <el-table-column prop="alert_threshold" label="预警阈值" />
              <el-table-column label="状态">
                <template #default="scope">
                  <el-tag type="danger">库存不足</el-tag>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>

      <!-- 产品分析图表 -->
      <el-col :span="12">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <span>产品分析</span>
          </template>
          <div v-loading="productLoading">
            <div v-if="productAnalysis">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="产品总数">{{ productAnalysis.totalProducts }}</el-descriptions-item>
                <el-descriptions-item label="上架产品">
                  <el-tag type="success">{{ productAnalysis.onSaleProducts }}</el-tag>
                </el-descriptions-item>
                <el-descriptions-item label="热销产品">
                  <el-tag type="warning">{{ productAnalysis.hotProducts }}</el-tag>
                </el-descriptions-item>
              </el-descriptions>
              
              <div style="margin-top: 20px;">
                <h4>产品分类统计</h4>
                <v-chart class="chart" :option="productCategoryChartOption" autoresize />
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <!-- 原料使用分析图表 -->
      <el-col :span="24">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <span>原料使用分析</span>
          </template>
          <div v-loading="materialLoading">
            <el-table :data="materialUsageList" style="width: 100%">
              <el-table-column prop="material_name" label="原料名称" />
              <el-table-column prop="specification" label="规格" />
              <el-table-column prop="total_quantity" label="总库存量" />
              <el-table-column prop="warehouse_count" label="仓库数" />
              <el-table-column prop="used_in_products" label="产品使用量" />
            </el-table>
            
            <div style="margin-top: 20px;">
              <h4>原料使用量对比</h4>
              <v-chart class="chart" :option="materialUsageChartOption" autoresize />
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup name="Report">
import { getStockStatistics, getStockAlert, getCheckAnalysis, getMaterialUsage, getProductAnalysis, getDashboard, getOrderStatistics, getPurchaseStatistics } from "@/api/report"
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers' // 确保从正确路径导入
import { BarChart, PieChart } from 'echarts/charts'
import { GridComponent, TooltipComponent, LegendComponent } from 'echarts/components'
import VChart from 'vue-echarts'

use([
  CanvasRenderer,
  BarChart,
  PieChart,
  GridComponent,
  TooltipComponent,
  LegendComponent
])

const { proxy } = getCurrentInstance()

const dashboardLoading = ref(false)
const stockLoading = ref(false)
const checkLoading = ref(false)
const alertLoading = ref(false)
const productLoading = ref(false)
const materialLoading = ref(false)

const dashboardData = ref(null)
const stockStatistics = ref(null)
const checkAnalysis = ref(null)
const alertList = ref([])
const productAnalysis = ref(null)
const materialUsageList = ref([])
const orderStatistics = ref(null)
const purchaseStatistics = ref(null)

// 仓库库存统计图表配置
const warehouseChartOption = computed(() => {
  if (!stockStatistics.value || !stockStatistics.value.warehouseStats) {
    return {}
  }
  
  const warehouseNames = stockStatistics.value.warehouseStats.map(item => item.warehouse_name)
  const quantities = stockStatistics.value.warehouseStats.map(item => item.total_quantity)
  
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: warehouseNames,
        axisTick: {
          alignWithLabel: true
        }
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: [
      {
        name: '库存数量',
        type: 'bar',
        barWidth: '60%',
        data: quantities,
        itemStyle: {
          color: '#409EFF'
        }
      }
    ]
  }
})

// 盘点状态分布图表配置
const checkStatusChartOption = computed(() => {
  if (!checkAnalysis.value) {
    return {}
  }
  
  const data = [
    { value: checkAnalysis.value.ongoingChecks, name: '进行中' },
    { value: checkAnalysis.value.completedChecks, name: '已完成' }
  ]
  
  return {
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: '盘点状态',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: data
      }
    ]
  }
})

// 产品分类统计图表配置
const productCategoryChartOption = computed(() => {
  if (!productAnalysis.value || !productAnalysis.value.categoryStats) {
    return {}
  }
  
  const categoryNames = productAnalysis.value.categoryStats.map(item => item.category_name)
  const productCounts = productAnalysis.value.categoryStats.map(item => item.product_count)
  
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: categoryNames,
        axisTick: {
          alignWithLabel: true
        }
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: [
      {
        name: '产品数',
        type: 'bar',
        barWidth: '60%',
        data: productCounts,
        itemStyle: {
          color: 'rgba(119,215,127,0.85)'
        }
      }
    ]
  }
})

// 原料使用量图表配置
const materialUsageChartOption = computed(() => {
  if (!materialUsageList.value || materialUsageList.value.length === 0) {
    return {}
  }
  
  const materialNames = materialUsageList.value.map(item => item.material_name)
  const totalQuantities = materialUsageList.value.map(item => item.total_quantity)
  const usedInProducts = materialUsageList.value.map(item => item.used_in_products)
  
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: ['总库存量', '产品使用量']
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: materialNames,
        axisTick: {
          alignWithLabel: true
        }
      }
    ],
    yAxis: [
      {
        type: 'value'
      }
    ],
    series: [
      {
        name: '总库存量',
        type: 'bar',
        stack: '总量',
        barWidth: '60%',
        data: totalQuantities,
        itemStyle: {
          color: '#409EFF'
        }
      },
      {
        name: '产品使用量',
        type: 'bar',
        stack: '总量',
        barWidth: '60%',
        data: usedInProducts,
        itemStyle: {
          color: '#6ce63c'
        }
      }
    ]
  }
})

const stockCards = computed(() => {
  if (!stockStatistics.value) return []
  return [
    { label: '总库存数量', value: stockStatistics.value.totalQuantity || 0 },
    { label: '总库存价值', value: '¥' + formatNumber(stockStatistics.value.totalValue || 0) },
    { label: '仓库数量', value: stockStatistics.value.warehouseCount || 0 },
    { label: '预警数量', value: stockStatistics.value.alertCount || 0 }
  ]
})

// 订单统计卡片
const orderCards = computed(() => {
  if (!orderStatistics.value) return []
  return [
    { label: '客户订单总数', value: orderStatistics.value.totalOrders || 0 },
    { label: '待处理订单', value: orderStatistics.value.pendingOrders || 0 },
    { label: '已完成订单', value: orderStatistics.value.completedOrders || 0 },
    { label: '本月订单金额', value: '¥' + formatNumber(orderStatistics.value.monthOrderAmount || 0) }
  ]
})

// 采购统计卡片
const purchaseCards = computed(() => {
  if (!purchaseStatistics.value) return []
  return [
    { label: '采购订单总数', value: purchaseStatistics.value.totalPurchases || 0 },
    { label: '待处理采购', value: purchaseStatistics.value.pendingPurchases || 0 },
    { label: '已完成采购', value: purchaseStatistics.value.completedPurchases || 0 },
    { label: '本月采购金额', value: '¥' + formatNumber(purchaseStatistics.value.monthPurchaseAmount || 0) }
  ]
})

/** 格式化数字 */
function formatNumber(num) {
  if (!num) return '0.00'
  return Number(num).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
}

/** 格式化金额 */
function formatCurrency(value) {
  return '¥' + (value || 0).toFixed(2).replace(/\B(?=(\d{3})+(?!\d))/g, ',')
}

/** 刷新仪表盘 */
function refreshDashboard() {
  dashboardLoading.value = true
  getDashboard().then(response => {
    dashboardData.value = response.data
    if (response.data.stockStatistics) {
      stockStatistics.value = response.data.stockStatistics
    }
    if (response.data.checkAnalysis) {
      checkAnalysis.value = response.data.checkAnalysis
    }
    if (response.data.productAnalysis) {
      productAnalysis.value = response.data.productAnalysis
    }
    if (response.data.alerts) {
      alertList.value = response.data.alerts
    }
    if (response.data.orderStatistics) {
      orderStatistics.value = response.data.orderStatistics
    }
    if (response.data.purchaseStatistics) {
      purchaseStatistics.value = response.data.purchaseStatistics
    }
    dashboardLoading.value = false
  }).catch(() => {
    dashboardLoading.value = false
  })
}

/** 加载库存统计 */
function loadStockStatistics() {
  stockLoading.value = true
  getStockStatistics().then(response => {
    stockStatistics.value = response.data
    stockLoading.value = false
  }).catch(() => {
    stockLoading.value = false
  })
}

/** 加载盘点分析 */
function loadCheckAnalysis() {
  checkLoading.value = true
  getCheckAnalysis().then(response => {
    checkAnalysis.value = response.data
    checkLoading.value = false
  }).catch(() => {
    checkLoading.value = false
  })
}

/** 加载库存预警 */
function loadStockAlert() {
  alertLoading.value = true
  getStockAlert().then(response => {
    alertList.value = response.data
    alertLoading.value = false
  }).catch(() => {
    alertLoading.value = false
  })
}

/** 加载产品分析 */
function loadProductAnalysis() {
  productLoading.value = true
  getProductAnalysis().then(response => {
    productAnalysis.value = response.data
    productLoading.value = false
  }).catch(() => {
    productLoading.value = false
  })
}

/** 加载原料使用分析 */
function loadMaterialUsage() {
  materialLoading.value = true
  getMaterialUsage().then(response => {
    materialUsageList.value = response.data
    materialLoading.value = false
  }).catch(() => {
    materialLoading.value = false
  })
}

// 页面加载时获取数据
onMounted(() => {
  refreshDashboard()
  loadStockStatistics()
  loadCheckAnalysis()
  loadStockAlert()
  loadProductAnalysis()
  loadMaterialUsage()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.stat-card {
  text-align: center;
  transition: all 0.3s ease;
}

.stat-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 订单统计卡片样式 */
.stat-card.order-card {
  background-color: #ecf5ff;
  border-left: 4px solid #409eff;
}

/* 采购统计卡片样式 */
.stat-card.purchase-card {
  background-color: #f0f9eb;
  border-left: 4px solid #67c23a;
}

.stat-content {
  padding: 20px;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #409EFF;
  margin-bottom: 10px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.box-card {
  margin-bottom: 20px;
}

.chart {
  height: 300px;
}
</style>