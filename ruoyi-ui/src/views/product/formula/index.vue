<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品" prop="productId">
        <el-select 
          v-model="queryParams.productId" 
          placeholder="请选择产品" 
          clearable 
          filterable
        >
          <el-option
            v-for="product in productList"
            :key="product.productId"
            :label="product.productName"
            :value="product.productId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="原料" prop="materialId">
        <el-select 
          v-model="queryParams.materialId" 
          placeholder="请选择原料" 
          clearable 
          filterable
        >
          <el-option
            v-for="material in materialList"
            :key="material.materialId"
            :label="material.materialName"
            :value="material.materialId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="用量" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          placeholder="请输入用量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单位" prop="unit">
        <el-input
          v-model="queryParams.unit"
          placeholder="请输入单位"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['product:formula:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['product:formula:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['product:formula:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:formula:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="formulaList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="配方ID" align="center" prop="formulaId" />
      <el-table-column label="产品" align="center" prop="productId">
        <template slot-scope="scope">
          {{ productMap.get(scope.row.productId) || scope.row.productId }}
        </template>
      </el-table-column>
      <el-table-column label="原料" align="center" prop="materialId">
        <template slot-scope="scope">
          {{ materialMap.get(scope.row.materialId) || scope.row.materialId }}
        </template>
      </el-table-column>
      <el-table-column label="用量" align="center" prop="quantity" />
      <el-table-column label="单位" align="center" prop="unit" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:formula:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:formula:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改产品配方对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品" prop="productId">
          <el-select 
            v-model="form.productId" 
            placeholder="请选择产品"
            filterable
          >
            <el-option
              v-for="product in productList"
              :key="product.productId"
              :label="product.productName"
              :value="product.productId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="原料" prop="materialId">
          <el-select 
            v-model="form.materialId" 
            placeholder="请选择原料"
            filterable
          >
            <el-option
              v-for="material in materialList"
              :key="material.materialId"
              :label="material.materialName"
              :value="material.materialId">
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
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listFormula, getFormula, delFormula, addFormula, updateFormula } from "@/api/product/formula"
import { listInfo } from "@/api/product/info"
import { listMaterial } from "@/api/material/info"

export default {
  name: "Formula",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品配方表格数据
      formulaList: [],
      // 产品信息列表
      productList: [],
      // 原料信息列表
      materialList: [],
      // 产品名称映射
      productMap: new Map(),
      // 原料名称映射
      materialMap: new Map(),
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productId: null,
        materialId: null,
        quantity: null,
        unit: null,
        createTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
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
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getProductList()
    this.getMaterialList()
    this.getList()
  },
  methods: {
    /** 查询产品信息列表 */
    getProductList() {
      listInfo().then(response => {
        this.productList = response.rows
        // 构建产品ID到产品名称的映射
        this.productList.forEach(product => {
          this.productMap.set(product.productId, product.productName)
        })
      })
    },
    /** 查询原料信息列表 */
    getMaterialList() {
      listMaterial().then(response => {
        this.materialList = response.rows
        // 构建原料ID到原料名称的映射
        this.materialList.forEach(material => {
          this.materialMap.set(material.materialId, material.materialName)
        })
      })
    },
    /** 查询产品配方列表 */
    getList() {
      this.loading = true
      listFormula(this.queryParams).then(response => {
        this.formulaList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        formulaId: null,
        productId: null,
        materialId: null,
        quantity: null,
        unit: null,
        createTime: null,
        updateTime: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.formulaId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加产品配方"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const formulaId = row.formulaId || this.ids
      getFormula(formulaId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改产品配方"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.formulaId != null) {
            updateFormula(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addFormula(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const formulaIds = row.formulaId || this.ids
      this.$modal.confirm('是否确认删除产品配方编号为"' + formulaIds + '"的数据项？').then(function() {
        return delFormula(formulaIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/formula/export', {
        ...this.queryParams
      }, `formula_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
