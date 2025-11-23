<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="原料名称" prop="materialName">
        <el-input
          v-model="queryParams.materialName"
          placeholder="请输入原料名称"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="categoryId">
        <el-select
          v-model="queryParams.categoryId"
          placeholder="请选择分类"
          clearable
          filterable
        >
          <el-option
            v-for="item in categoryList"
            :key="item.categoryId"
            :label="item.categoryName"
            :value="item.categoryId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" prop="supplierId">
        <el-select
          v-model="queryParams.supplierId"
          placeholder="请选择供应商"
          clearable
          filterable
        >
          <el-option
            v-for="item in supplierList"
            :key="item.supplierId"
            :label="item.supplierName"
            :value="item.supplierId"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购单价" prop="purchasePrice">
        <el-input
          v-model="queryParams.purchasePrice"
          placeholder="请输入采购单价"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="保质期" prop="shelfLife">
        <el-input
          v-model="queryParams.shelfLife"
          placeholder="请输入保质期(天)"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警天数" prop="warningDays">
        <el-input
          v-model="queryParams.warningDays"
          placeholder="请输入预警天数"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 150px;">
          <el-option
            v-for="dict in sys_normal_disable"
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
          v-hasPermi="['material:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['material:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['material:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['material:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="原料ID" align="center" prop="materialId" />
      <el-table-column label="原料名称" align="center" prop="materialName" />
      <el-table-column label="分类" align="center" prop="categoryId" >
        <template #default="scope">
          {{categoryMap.get(scope.row.categoryId)||scope.row.categoryId}}
        </template>
      </el-table-column>
      <el-table-column label="规格" align="center" prop="specification" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="供应商" align="center" prop="supplierId" >
        <template #default="scope">
          {{supplierMap.get(scope.row.supplierId)||scope.row.supplierId}}
        </template>
      </el-table-column>
      <el-table-column label="采购单价" align="center" prop="purchasePrice" />
      <el-table-column label="保质期(天)" align="center" prop="shelfLife" />
      <el-table-column label="预警天数" align="center" prop="warningDays" />
      <el-table-column label="状态" align="center" prop="status">
        <template #default="scope">
          <dict-tag :options="sys_normal_disable" :value="scope.row.status"/>
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
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['material:info:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['material:info:remove']">删除</el-button>
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

    <!-- 添加或修改原料信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原料名称" prop="materialName">
          <el-input v-model="form.materialName" placeholder="请输入原料名称" />
        </el-form-item>
        <el-form-item label="分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择分类">
            <el-option
              v-for="item in categoryList"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
            ></el-option>
          </el-select>

        </el-form-item>
        <el-form-item label="规格" prop="specification">
          <el-input v-model="form.specification" placeholder="请输入规格" />
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入单位" />
        </el-form-item>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable filterable>
            <el-option
              v-for="item in supplierList"
              :key="item.supplierId"
              :label="item.supplierName"
              :value="item.supplierId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="采购单价" prop="purchasePrice">
          <el-input v-model="form.purchasePrice" placeholder="请输入采购单价" />
        </el-form-item>
        <el-form-item label="保质期(天)" prop="shelfLife">
          <el-input v-model="form.shelfLife" placeholder="请输入保质期(天)" />
        </el-form-item>
        <el-form-item label="预警天数" prop="warningDays">
          <el-input v-model="form.warningDays" placeholder="请输入预警天数" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in sys_normal_disable"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-divider content-position="center">供应商信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="Plus" @click="handleAddSupplier">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="Delete" @click="handleDeleteSupplier">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="supplierList" :row-class-name="rowSupplierIndex" @selection-change="handleSupplierSelectionChange" ref="supplier">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="供应商名称" prop="supplierName" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.supplierName" placeholder="请输入供应商名称" />
            </template>
          </el-table-column>
          <el-table-column label="联系人" prop="contactPerson" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.contactPerson" placeholder="请输入联系人" />
            </template>
          </el-table-column>
          <el-table-column label="联系电话" prop="contactPhone" width="150">
            <template #default="scope">
              <el-input v-model="scope.row.contactPhone" placeholder="请输入联系电话" />
            </template>
          </el-table-column>
          <el-table-column label="状态" prop="status" width="150">
            <template #default="scope">
              <el-select v-model="scope.row.status" placeholder="请选择状态">
                <el-option
                  v-for="dict in sys_normal_disable"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" prop="createTime" width="240">
            <template #default="scope">
              <el-date-picker clearable
                v-model="scope.row.createTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择创建时间">
              </el-date-picker>
            </template>
          </el-table-column>
          <el-table-column label="更新时间" prop="updateTime" width="240">
            <template #default="scope">
              <el-date-picker clearable
                v-model="scope.row.updateTime"
                type="date"
                value-format="YYYY-MM-DD"
                placeholder="请选择更新时间">
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

<script setup name="Info">
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/material/info"
import {listCategory} from "@/api/material/category"
import {listSupplier} from "@/api/material/supplier"

const { proxy } = getCurrentInstance()
const { sys_normal_disable } = proxy.useDict('sys_normal_disable')

const infoList = ref([])
const supplierList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const checkedSupplier = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")
const categoryList = ref([])
const categoryMap = ref(new Map())
const supplierMap = ref(new Map())

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    materialName: null,
    categoryId: null,
    supplierId: null,
    purchasePrice: null,
    shelfLife: null,
    warningDays: null,
    status: null,
  },
  rules: {
    materialName: [
      { required: true, message: "原料名称不能为空", trigger: "blur" }
    ],
    categoryId: [
      { required: true, message: "原料分类不能为空", trigger: "change" }
    ],
    specification: [
      { required: true, message: "规格不能为空", trigger: "blur" }
    ],
    unit: [
      { required: true, message: "单位不能为空", trigger: "blur" }
    ],
    supplierId: [
      { required: true, message: "供应商不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询分类信息列表 */
function getCategoryList() {
  listCategory().then(response => {
    if (response && response.data) {
      categoryList.value = response.data
      categoryList.value.forEach(item => {
        categoryMap.value.set(item.categoryId, item.categoryName)
      })
    }
  }).catch(error => {
    console.error('获取分类列表失败:', error)
    categoryList.value = []
  })
}

/** 查询供应商列表 */
function getSupplierList() {
  listSupplier().then(response => {
    if (response && response.rows) {
      supplierList.value = response.rows
      supplierList.value.forEach(item => {
        supplierMap.value.set(item.supplierId, item.supplierName)
      })
    }
  }).catch(error => {
    console.error('获取供应商列表失败:', error)
    supplierList.value = []
  })
}

/** 查询原料信息列表 */
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
    materialId: null,
    materialName: null,
    categoryId: null,
    specification: null,
    unit: null,
    supplierId: null,
    purchasePrice: null,
    shelfLife: null,
    warningDays: null,
    status: null,
    createBy: null,
    createTime: null,
    updateBy: null,
    updateTime: null
  }
  supplierList.value = []
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
  ids.value = selection.map(item => item.materialId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  getSupplierList()
  open.value = true
  title.value = "添加原料信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  getSupplierList()
  const _materialId = row.materialId || ids.value
  getInfo(_materialId).then(response => {
    form.value = response.data
    supplierList.value = response.data.supplierList
    open.value = true
    title.value = "修改原料信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["infoRef"].validate(valid => {
    if (valid) {
      form.value.supplierList = supplierList.value
      if (form.value.materialId != null) {
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
  const _materialIds = row.materialId || ids.value
  proxy.$modal.confirm('是否确认删除原料信息编号为"' + _materialIds + '"的数据项？').then(function() {
    return delInfo(_materialIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 供应商序号 */
function rowSupplierIndex({ row, rowIndex }) {
  row.index = rowIndex + 1
}

/** 供应商添加按钮操作 */
function handleAddSupplier() {
  let obj = {}
  obj.supplierName = ""
  obj.contactPerson = ""
  obj.contactPhone = ""
  obj.status = ""
  obj.createTime = ""
  obj.updateTime = ""
  supplierList.value.push(obj)
}

/** 供应商删除按钮操作 */
function handleDeleteSupplier() {
  if (checkedSupplier.value.length == 0) {
    proxy.$modal.msgError("请先选择要删除的供应商数据")
  } else {
    const suppliers = supplierList.value
    const checkedSuppliers = checkedSupplier.value
    supplierList.value = suppliers.filter(function(item) {
      return checkedSuppliers.indexOf(item.index) == -1
    })
  }
}

/** 复选框选中数据 */
function handleSupplierSelectionChange(selection) {
  checkedSupplier.value = selection.map(item => item.index)
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('material/info/export', {
    ...queryParams.value
  }, `info_${new Date().getTime()}.xlsx`)
}
getCategoryList()
getSupplierList()
getList()
</script>