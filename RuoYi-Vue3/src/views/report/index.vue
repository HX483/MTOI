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
              <!-- 综合统计图表区域 -->
              <el-row :gutter="20">
                <!-- 库存统计图表 -->
                <el-col :span="8">
                  <el-card class="box-card" shadow="hover">
                    <template #header><span>库存统计</span></template>
                    <v-chart class="chart" :option="stockStatsChartOption" autoresize />
                  </el-card>
                </el-col>
                
                <!-- 订单统计图表 -->
                <el-col :span="8">
                  <el-card class="box-card" shadow="hover">
                    <template #header><span>订单统计</span></template>
                    <v-chart class="chart" :option="orderStatsChartOption" autoresize />
                  </el-card>
                </el-col>
                
                <!-- 采购统计图表 -->
                <el-col :span="8">
                  <el-card class="box-card" shadow="hover">
                    <template #header><span>采购统计</span></template>
                    <v-chart class="chart" :option="purchaseStatsChartOption" autoresize />
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
                <!-- <el-descriptions-item label="预警数量" :span="2">
                  <el-tag type="warning">{{ stockStatistics.alertCount }}</el-tag>
                </el-descriptions-item> -->
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
            <div v-if="alertList.length > 0">
              <!-- 库存预警总数显示 -->
               <el-descriptions :column="2" border>
                <el-descriptions-item label="预警原料总数">{{ alertSummary }}</el-descriptions-item>
                </el-descriptions>
              <!-- <div style="margin-bottom: 20px; text-align: center;">
                <h4 style="display: inline; margin-right: 10px;">预警原料总数：</h4>
                <el-tag size="large" type="danger">{{ alertSummary }}</el-tag>
              </div> -->
              
              <h4>库存不足原料列表</h4>
              <v-chart class="chart" :option="alertChartOption" autoresize />
            </div>
            <el-empty v-else description="暂无库存预警数据" />
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
              <el-descriptions :column="3" border>
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
      <!-- 原料使用分析 -->
      <el-col :span="24">
        <el-card class="box-card" shadow="hover">
          <template #header>
            <span>原料使用分析</span>
          </template>
          <div v-loading="materialLoading">
            <div v-if="materialUsageList.length > 0">
              <div style="margin-top: 20px;">
                <h4>原料使用量与库存对比</h4>
                <v-chart class="chart" :option="materialUsageChartOption" autoresize />
              </div>
              
              <div style="margin-top: 20px;">
                <h4>原料仓库分布</h4>
                <v-chart class="chart" :option="materialWarehouseChartOption" autoresize />
              </div>
            </div>
            <el-empty v-else description="暂无原料使用数据" />
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

// 库存预警统计摘要
const alertSummary = computed(() => {
  if (!alertList.value || alertList.value.length === 0) {
    return 0
  }
  
  // 计算预警原料总数（去重）
  const uniqueMaterials = new Set(alertList.value.map(item => item.material_name))
  return uniqueMaterials.size
})
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

// 库存统计图表配置
const stockStatsChartOption = computed(() => {
  if (!stockStatistics.value) {
    return {}
  }
  
  const data = [
    { value: stockStatistics.value.totalQuantity || 0, name: '总库存数量' },
    { value: stockStatistics.value.totalValue || 0, name: '总库存价值' },
    { value: stockStatistics.value.warehouseCount || 0, name: '仓库数量' },
    { value: stockStatistics.value.alertCount || 0, name: '预警数量' }
  ]
  
  return {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 10,
      data: ['总库存数量', '总库存价值', '仓库数量', '预警数量']
    },
    series: [
      {
        name: '库存统计',
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
            fontSize: 16,
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

// 订单统计图表配置
const orderStatsChartOption = computed(() => {
  if (!orderStatistics.value) {
    return {}
  }
  
  const data = [
    { value: orderStatistics.value.totalOrders || 0, name: '订单总数' },
    { value: orderStatistics.value.pendingOrders || 0, name: '待处理订单' },
    { value: orderStatistics.value.completedOrders || 0, name: '已完成订单' }
  ]
  
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
        data: ['订单总数', '待处理订单', '已完成订单'],
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
        name: '订单数量',
        type: 'bar',
        barWidth: '60%',
        data: data.map(item => item.value),
        itemStyle: {
          color: function(params) {
            const colors = ['#409EFF', '#E6A23C', '#67C23A']
            return colors[params.dataIndex]
          }
        },
        label: {
          show: true,
          position: 'top'
        }
      }
    ]
  }
})

// 采购统计图表配置
const purchaseStatsChartOption = computed(() => {
  if (!purchaseStatistics.value) {
    return {}
  }
  
  const data = [
    { value: purchaseStatistics.value.totalPurchases || 0, name: '采购总数' },
    { value: purchaseStatistics.value.pendingPurchases || 0, name: '待处理采购' },
    { value: purchaseStatistics.value.completedPurchases || 0, name: '已完成采购' }
  ]
  
  return {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 10,
      data: ['采购总数', '待处理采购', '已完成采购']
    },
    series: [
      {
        name: '采购统计',
        type: 'pie',
        radius: '50%',
        data: data,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
})

// 库存预警图表配置
const alertChartOption = computed(() => {
  if (!alertList.value || alertList.value.length === 0) {
    return {}
  }
  
  // 按原料名称和仓库分组处理数据
  const materialDataMap = new Map()
  const warehouses = new Set()
  
  alertList.value.forEach(item => {
    const materialName = item.material_name
    const warehouseName = item.warehouse_name || '未知仓库'
    
    warehouses.add(warehouseName)
    
    if (!materialDataMap.has(materialName)) {
      materialDataMap.set(materialName, {
        name: materialName,
        threshold: item.alert_threshold,
        warehouses: new Map()
      })
    }
    
    materialDataMap.get(materialName).warehouses.set(warehouseName, {
      quantity: item.quantity
    })
  })
  
  // 准备图表数据
  const materialNames = Array.from(materialDataMap.keys())
  const warehouseArray = Array.from(warehouses)
  
  // 创建每个仓库的系列数据
  const series = []
  
  // 添加当前库存系列（按仓库分组）
  warehouseArray.forEach(warehouse => {
    const data = materialNames.map(materialName => {
      const material = materialDataMap.get(materialName)
      return material.warehouses.get(warehouse)?.quantity || 0
    })
    
    series.push({
      name: `${warehouse}`,
      type: 'bar',
      data: data
    })
  })
  
  // 添加预警阈值系列（线图）
  const thresholds = materialNames.map(materialName => {
    return materialDataMap.get(materialName).threshold
  })
  
  series.push({
    name: '预警阈值',
    type: 'line',
    data: thresholds,
    itemStyle: {
      color: '#E6A23C'
    },
    lineStyle: {
      width: 2
    },
    symbol: 'circle',
    symbolSize: 8
  })
  
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: [...warehouseArray, '预警阈值']
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '10%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: materialNames,
        axisLabel: {
          rotate: 45
        },
        axisTick: {
          alignWithLabel: true
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        name: '数量'
      }
    ],
    series: series
  }
})

// 原料使用量图表配置
const materialUsageChartOption = computed(() => {
  if (!materialUsageList.value || materialUsageList.value.length === 0) {
    return {}
  }
  
  // 按原料名称分组，求和总库存量和产品使用量
  const materialDataMap = new Map()
  
  materialUsageList.value.forEach(item => {
    const materialName = item.material_name
    
    if (!materialDataMap.has(materialName)) {
      materialDataMap.set(materialName, {
        totalQuantity: 0,
        usedInProducts: item.used_in_products // used_in_products应该是按原料汇总的，不是按仓库
      })
    }
    
    const materialData = materialDataMap.get(materialName)
    materialData.totalQuantity += item.total_quantity
  })
  
  const materialNames = Array.from(materialDataMap.keys())
  const totalQuantities = materialNames.map(name => materialDataMap.get(name).totalQuantity)
  const usedInProducts = materialNames.map(name => materialDataMap.get(name).usedInProducts)
  
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
      bottom: '10%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: materialNames,
        axisLabel: {
          rotate: 45
        },
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

// 原料仓库分布图表配置
const materialWarehouseChartOption = computed(() => {
  if (!materialUsageList.value || materialUsageList.value.length === 0) {
    return {}
  }
  
  // 处理数据，按原料和仓库分组
  const materialDataMap = new Map()
  const warehouseNames = new Set()
  
  materialUsageList.value.forEach(item => {
    const materialName = item.material_name
    const warehouseName = item.warehouse_name || '未分配'
    
    warehouseNames.add(warehouseName)
    
    if (!materialDataMap.has(materialName)) {
      materialDataMap.set(materialName, new Map())
    }
    
    const warehouseMap = materialDataMap.get(materialName)
    // 使用total_quantity作为仓库中的原料数量
    warehouseMap.set(warehouseName, (warehouseMap.get(warehouseName) || 0) + item.total_quantity)
  })
  
  const materialNames = Array.from(materialDataMap.keys())
  const warehouseList = Array.from(warehouseNames)
  
  // 生成颜色数组
  const colors = ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C', '#909399', '#722ED1', '#13C2C2', '#EB2F96']
  
  // 构建series数据
    const series = warehouseList.map((warehouse, index) => {
      const data = materialNames.map(materialName => {
        const warehouseMap = materialDataMap.get(materialName)
        return warehouseMap.get(warehouse) || 0
      })
      
      return {
        name: warehouse,
        type: 'bar',
        stack: '仓库分布',
        barWidth: '60%',
        data: data,
        itemStyle: {
          color: colors[index % colors.length]
        },
        label: {
          show: false
        }
      }
    })
  
  return {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: warehouseList
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '10%',
      containLabel: true
    },
    xAxis: [
      {
        type: 'category',
        data: materialNames,
        axisLabel: {
          rotate: 45
        },
        axisTick: {
          alignWithLabel: true
        }
      }
    ],
    yAxis: [
      {
        type: 'value',
        name: '原料数量',
        minInterval: 1
      }
    ],
    series: series
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

/* 综合统计图表样式 */
.box-card {
  margin-bottom: 20px;
}

.chart {
  height: 300px;
}

/* 确保图表在卡片内正确显示 */
.box-card .chart {
  margin-top: 20px;
}
</style>