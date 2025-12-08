<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="原料" prop="materialId">
        <el-select v-model="queryParams.materialId" placeholder="请选择原料ID" clearable filterable>
          <el-option v-for="material in materialList" :key="material.materialId" :label="material.materialName" :value="material.materialId"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="库存数量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入库存数量"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预警阈值" prop="alertThreshold">
        <el-input
          v-model="queryParams.alertThreshold"
          placeholder="请输入预警阈值"
          clearable
          @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable filterable>
            <el-option v-for="warehouse in warehouseList" :key="warehouse.warehouseId" :label="warehouse.warehouseName" :value="warehouse.warehouseId"></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="存储位置" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入存储位置"
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
          v-hasPermi="['stock:info:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['stock:info:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="Delete"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['stock:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="Download"
          @click="handleExport"
          v-hasPermi="['stock:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库存ID" align="center" prop="stockId" />
      <el-table-column label="原料" align="center" prop="materialId" >
        <template #default="scope">{{materialMap.get(scope.row.materialId)||scope.row.materialId}}</template>
      </el-table-column>
      <el-table-column label="库存数量" align="center" prop="quantity" />
      <el-table-column label="预警阈值" align="center" prop="alertThreshold" />
      <el-table-column label="仓库" align="center" prop="warehouseId" >
        <template #default="scope">
          <el-button 
            type="primary" 
            link 
            @click="handleWarehouseClick(scope.row.warehouseId)">
            {{warehouseMap.get(scope.row.warehouseId)||scope.row.warehouseId}}
          </el-button>
        </template>
      </el-table-column>

      <el-table-column label="存储位置" align="center" prop="location" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button link type="primary" icon="Edit" @click="handleUpdate(scope.row)" v-hasPermi="['stock:info:edit']">修改</el-button>
          <el-button link type="primary" icon="Delete" @click="handleDelete(scope.row)" v-hasPermi="['stock:info:remove']">删除</el-button>
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

    <!-- 添加或修改库存信息对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="infoRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原料" prop="materialId">
          <el-select v-model="form.materialId" placeholder="请原料ID" filterable>
            <el-option v-for="material in materialList" :key="material.materialId" :label="material.materialName" :value="material.materialId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="库存数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入库存数量" />
        </el-form-item>
        <el-form-item label="预警阈值" prop="alertThreshold">
          <el-input v-model="form.alertThreshold" placeholder="请输入预警阈值" />
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseId">
          <el-select v-model="form.warehouseId" placeholder="请输入仓库ID" clearable filterable>
            <el-option v-for="warehouse in warehouseList" :key="warehouse.warehouseId" :label="warehouse.warehouseName" :value="warehouse.warehouseId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="存储位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入存储位置" />
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

<script setup name="Info">
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/stock/info"
import {listInfo as materialInfo } from  "@/api/material/info"
import {listWarehouse as WarehouseInfo } from  "@/api/stock/warehouse"

const { proxy } = getCurrentInstance()

const infoList = ref([])
const open = ref(false)
const loading = ref(true)
const showSearch = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const total = ref(0)
const title = ref("")

const materialList = ref([])
const warehouseList = ref([])
const materialMap=ref(new Map())
const warehouseMap=ref(new Map())

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    materialId: null,
    quantity: null,
    alertThreshold: null,
    warehouseId: null,
    location: null,
    createTime: null,
  },
  rules: {
    materialId: [
      { required: true, message: "原料不能为空", trigger: "change" }
    ],
    quantity: [
      { required: true, message: "库存数量不能为空", trigger: "blur" }
    ],
    alertThreshold: [
      { required: true, message: "预警阈值不能为空", trigger: "blur" }
    ],
    warehouseId: [
      { required: true, message: "仓库不能为空", trigger: "change" }
    ],
  }
})

const { queryParams, form, rules } = toRefs(data)

/** 查询库存信息列表 */
function getList() {
  loading.value = true
  listInfo(queryParams.value).then(response => {
    infoList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 查询原料信息 */
function getMaterialInfo() {
  materialInfo({ pageNum: 1, pageSize: 9999 }).then(response => {
    materialList.value = response.rows
    materialList.value.forEach((item) => {
      materialMap.value.set(item.materialId, item.materialName)
    })
  })
}
/** 查询仓库信息  */
function getWarehouseInfo() {
  WarehouseInfo().then(response => {
    warehouseList.value = response.rows
    warehouseList.value.forEach((item) => {
      warehouseMap.value.set(item.warehouseId, item.warehouseName)
    })
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
    stockId: null,
    materialId: null,
    quantity: null,
    alertThreshold: null,
    warehouseId: null,
    location: null,
    createTime: null,
    updateTime: null
  }
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
  ids.value = selection.map(item => item.stockId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function handleAdd() {
  reset()
  open.value = true
  title.value = "添加库存信息"
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset()
  const _stockId = row.stockId || ids.value
  getInfo(_stockId).then(response => {
    form.value = response.data
    open.value = true
    title.value = "修改库存信息"
  })
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["infoRef"].validate(valid => {
    if (valid) {
      if (form.value.stockId != null) {
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
  const _stockIds = row.stockId || ids.value
  proxy.$modal.confirm('是否确认删除库存信息编号为"' + _stockIds + '"的数据项？').then(function() {
    return delInfo(_stockIds)
  }).then(() => {
    getList()
    proxy.$modal.msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download('stock/info/export', {
    ...queryParams.value
  }, `info_${new Date().getTime()}.xlsx`)
}

/** 跳转到仓库模块并强调显示对应仓库 */
function handleWarehouseClick(warehouseId) {
  console.log('点击仓库跳转，ID:', warehouseId, '类型:', typeof warehouseId);
  // 保存要强调显示的仓库ID到会话存储，确保类型一致
  sessionStorage.setItem('highlightWarehouseId', String(warehouseId));
  console.log('sessionStorage设置结果:', sessionStorage.getItem('highlightWarehouseId'));
  // 跳转到仓库页面
  proxy.$router.push('/stock/warehouse');
}

getWarehouseInfo()
getMaterialInfo()
getList()
</script>
