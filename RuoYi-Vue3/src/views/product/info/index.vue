<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否热销" prop="isHot">
        <el-select v-model="queryParams.isHot" placeholder="请选择是否热销" clearable>
          <el-option
            v-for="dict in sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否新品" prop="isNew">
        <el-select v-model="queryParams.isNew" placeholder="请选择是否新品" clearable>
          <el-option
            v-for="dict in sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态(1:上架,0:下架)" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态(1:上架,0:下架)" clearable>
          <el-option
            v-for="dict in putaway_yes_no"
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
          v-hasPermi="['product:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['product:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品ID" align="center" prop="productId" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="分类ID" align="center" prop="categoryId" />
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="产品描述" align="center" prop="description" />
      <el-table-column label="产品图片" align="center" prop="imageUrl" width="100">
        <template #default="scope">
          <image-preview :src="scope.row.imageUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="是否热销" align="center" prop="isHot">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.isHot"/>
        </template>
      </el-table-column>
      <el-table-column label="是否新品" align="center" prop="isNew">
        <template #default="scope">
          <dict-tag :options="sys_yes_no" :value="scope.row.isNew"/>
        </template>
      </el-table-column>
      <el-table-column label="状态(1:上架,0:下架)" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="putaway_yes_no" :value="scope.row.status"/>
        </template>
      </el-table-column>
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
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['product:info:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['product:info:remove']">删除</el-button>
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

    <!-- 添加或修改产品信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="分类ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入分类ID" />
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="产品描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="产品图片" prop="imageUrl">
          <image-upload v-model="form.imageUrl"/>
        </el-form-item>
        <el-form-item label="是否热销" prop="isHot">
          <el-select v-model="form.isHot" placeholder="请选择是否热销">
            <el-option
              v-for="dict in sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否新品" prop="isNew">
          <el-select v-model="form.isNew" placeholder="请选择是否新品">
            <el-option
              v-for="dict in sys_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态(1:上架,0:下架)" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态(1:上架,0:下架)">
            <el-option
              v-for="dict in putaway_yes_no"
              :key="dict.value"
              :label="dict.label"
              :value="parseInt(dict.value)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">产品配方信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddProductFormula">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteProductFormula">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="productFormulaList" :row-class-name="rowProductFormulaIndex" @selection-change="handleProductFormulaSelectionChange" ref="productFormula">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="原料ID" prop="materialId" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.materialId" placeholder="请输入原料ID" />
            </template>
          </el-table-column>
          <el-table-column label="用量" prop="quantity" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入用量" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位" />
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

<script setup name="Info">
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/product/info"

const { proxy } = getCurrentInstance()
const { sys_yes_no, putaway_yes_no } = proxy.useDict('sys_yes_no', 'putaway_yes_no')

const infoList = ref([])
const productFormulaList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedProductFormula = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    productName: null,
    price: null,
    isHot: null,
    isNew: null,
    status: null,
  },
  rules: {
    productName: [
      { required: true, message: "产品名称不能为空", trigger: "blur" }
    ],
    categoryId: [
      { required: true, message: "分类ID不能为空", trigger: "blur" }
    ],
    price: [
      { required: true, message: "价格不能为空", trigger: "blur" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询产品信息列表 */
function getList() {
  loading.value = true
  listInfo(queryParams.value).then(response => {
    infoList.value = response.rows
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
    productId: null,
    productName: null,
    categoryId: null,
    price: null,
    description: null,
    imageUrl: null,
    isHot: null,
    isNew: null,
    status: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  productFormulaList.value = []
  proxy.resetForm("infoRef")
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
  ids.value = selection.map(item => item.productId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加产品信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _productId = row.productId || ids.value
  getInfo(_productId).then(response => {
    form.value = response.data
    productFormulaList.value = response.data.productFormulaList
    open.value = true
    title.value = "修改产品信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["infoRef"].validate(valid => {
    if (valid) {
      form.value.productFormulaList = productFormulaList.value
      if (form.value.productId != null) {
        updateInfo(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功")
          open.value = false
          getList()
        })
      } else {
        addInfo(form.value).then(response => {
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
  const _productIds = row.productId || ids.value
  proxy.$modal.confirm('是否确认删除产品信息编号为"' + _productIds + '"的数据项？').then(function() {
    return delInfo(_productIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 产品配方序号 */
function rowProductFormulaIndex({ row, rowIndex }) {
  row.index = rowIndex + 1
}

/** 产品配方添加按钮操作 */
function handleAddProductFormula() {
  let obj = {}
  obj.materialId = ""
  obj.quantity = ""
  obj.unit = ""
  productFormulaList.value.push(obj)
}

/** 产品配方删除按钮操作 */
function handleDeleteProductFormula() {
  if (checkedProductFormula.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的产品配方数据")
  } else {
    const productFormulas = productFormulaList.value
    const checkedProductFormulas = checkedProductFormula.value
    productFormulaList.value = productFormulas.filter(function(item) {
      return checkedProductFormulas.indexOf(item.index) == -1
    })
  }
}

/** 复选框选中数据 */
function handleProductFormulaSelectionChange(selection) {
  checkedProductFormula.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('product/info/export', {
    ...queryParams.value
  }, `info_${new Date().getTime()}.xlsx`)
}

getList()
</script>
