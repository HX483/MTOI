<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品" prop="productId">
        <el-select v-model="queryParams.productId" placeholder="请选择产品" clearable filterable>
          <el-option
            v-for="item in productList"
            :key="item.productId"
            :label="item.productName"
            :value="item.productId"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="原料" prop="materialId">
        <el-select v-model="queryParams.materialId" placeholder="请选择原料" clearable filterable>
          <el-option
            v-for="item in materialList"
            :key="item.materialId"
            :label="item.materialName"
            :value="item.materialId"
          >
          </el-option>
        </el-select>
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
      <el-table-column label="产品" align="center" prop="productId" >
      <template #default="scope">
        <!--根据产品ID获取对应的产品名称||获取不到则直接显示原始的产品ID-->
        {{ productMap.get(scope.row.productId) || scope.row.productId }}
      </template>
      </el-table-column>
      <el-table-column label="原料" align="center" prop="materialId" >
        <template #default="scope">
        {{ materialMap.get(scope.row.materialId) || scope.row.materialId }}
        </template>
      </el-table-column>
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
        <el-form-item label="产品" prop="productId">
          <el-select
              v-model="form.productId"
              placeholder="请选择产品"  filterable>
            <el-option
              v-for="item in productList"
              :key="item.productId"
              :label="item.productName"
              :value="item.productId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="原料" prop="materialId">
        <el-select v-model="form.materialId" placeholder="请选择原料" filterable>
          <el-option
            v-for="item in materialList"
            :key="item.materialId"
            :label="item.materialName"
            :value="item.materialId"
          >
          </el-option>
        </el-select>
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
import { listInfo as listProductInfo } from "@/api/product/info"
import { listInfo as listMaterialInfo } from "@/api/material/info"

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
const productList = ref([])
const materialList = ref([])

// 正确地声明表单引用
const formulaRef = ref(null)
const queryRef = ref(null)

// 1. 改用reactive包裹Map，提升响应式灵敏度
const productMap = reactive(new Map())
const materialMap = reactive(new Map())

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
      { required: true, message: "产品不能为空", trigger: "change" }
    ],
    materialId: [
      { required: true, message: "原料不能为空", trigger: "change" }
    ],
    quantity: [
      { required: true, message: "用量不能为空", trigger: "blur" }
    ],
    unit: [
      { required: true, message: "单位不能为空", trigger: "blur" }
    ],
    // 移除创建时间必填校验（创建时间通常由后端生成）
    // createTime: [
    //   { required: true, message: "创建时间不能为空", trigger: "blur" }
    // ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询产品信息列表 - 关键修改：获取全部产品（取消分页限制） */
function getProductList() {
  return new Promise((resolve, reject) => {
    // 传入pageNum=1，pageSize=9999 确保获取全部产品（根据实际接口调整）
    listProductInfo({ pageNum: 1, pageSize: 9999 }).then(response => {
      if (response && response.rows) {
        productList.value = response.rows
        // 2. 清空旧映射，重建新映射（关键修复）
        productMap.clear()
        productList.value.forEach(product => {
          productMap.set(product.productId, product.productName)
        })
        // 触发响应式更新（针对Map的特殊处理）
        productMap.set(productMap.size > 0 ? productMap.keys().next().value : 'dummy', productMap.get(productMap.size > 0 ? productMap.keys().next().value : 'dummy'))
      }
      resolve(response)
    }).catch(error => {
      console.error('获取产品列表失败:', error)
      productList.value = []
      productMap.clear()
      reject(error)
    })
  })
}

/** 查询原料信息列表 - 同理优化 */
function getMaterialList() {
  return new Promise((resolve, reject) => {
    listMaterialInfo({ pageNum: 1, pageSize: 9999 }).then(response => {
      if (response && response.rows) {
        materialList.value = response.rows
        materialMap.clear()
        materialList.value.forEach(material => {
          materialMap.set(material.materialId, material.materialName)
        })
        // 触发响应式更新
        materialMap.set(materialMap.size > 0 ? materialMap.keys().next().value : 'dummy', materialMap.get(materialMap.size > 0 ? materialMap.keys().next().value : 'dummy'))
      }
      resolve(response)
    }).catch(error => {
      console.error('获取原料列表失败:', error)
      materialList.value = []
      materialMap.clear()
      reject(error)
    })
  })
}

/** 查询产品配方列表 - 优化加载顺序 */
function getList() {
  loading.value = true
  // 3. 同时获取产品和原料列表，都完成后再查配方（关键修复）
  Promise.all([getProductList(), getMaterialList()]).then(() => {
    listFormula(queryParams.value).then(response => {
      formulaList.value = response.rows
      total.value = response.total
    }).finally(() => {
      loading.value = false
    })
  }).catch(() => {
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
  // 修复：使用正确的表单重置方式（避免proxy依赖）
  if (formulaRef.value) {
    formulaRef.value.resetFields()
  }
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1
  getList()
}

/** 重置按钮操作 */
function resetQuery() {
  // 修复：使用正确的表单重置方式
  if (queryRef.value) {
    queryRef.value.resetFields()
  }
  handleQuery()
}

// 多选框选中数据
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.formulaId)
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  // 确保打开对话框时产品/原料列表是最新的
  Promise.all([getProductList(), getMaterialList()]).then(() => {
    open.value = true
    title.value = "添加产品配方"
  })
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _formulaId = row.formulaId || ids.value[0] // 修复：避免数组直接赋值
  Promise.all([getProductList(), getMaterialList()]).then(() => {
    getFormula(_formulaId).then(response => {
      form.value = response.data
      open.value = true
      title.value = "修改产品配方"
    })
  })
}

/** 提交按钮 - 优化成功后刷新逻辑 */
function submitForm() {
  // 修复：正确使用表单引用
  if (formulaRef.value) {
    formulaRef.value.validate(valid => {
      if (valid) {
        const request = form.value.formulaId ? updateFormula(form.value) : addFormula(form.value)
        request.then(response => {
          proxy.$modal.msgSuccess(form.value.formulaId ? "修改成功" : "新增成功")
          open.value = false
          // 4. 延迟500ms刷新（确保数据库已同步新数据）
          setTimeout(() => {
            getList()
          }, 500)
        })
      }
    })
  }
}

/** 删除按钮操作 - 修复ids取值问题 */
function handleDelete(row) {
  const _formulaIds = row ? [row.formulaId] : ids.value
  proxy.$modal.confirm(`是否确认删除产品配方编号为"${_formulaIds.join(',')}"的数据项？`).then(function() {
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

// 初始化加载
getList()
</script>