<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input
          v-model="queryParams.price"
          placeholder="请输入价格"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item label="是否热销" prop="isHot">

        <el-select v-model="queryParams.isHot" placeholder="请选择是否热销" clearable>
          <el-option label="是" :value="1"/>
          <el-option label="否" :value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="是否新品" prop="isNew">
        <el-select v-model="queryParams.isNew" placeholder="请选择是否新品" clearable>
          <el-option label="是" :value="1"/>
          <el-option label="否" :value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
         <el-option label="上架" :value="1"/>
          <el-option label="下架" :value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="产品分类" prop="categoryId">
        <el-select v-model="queryParams.categoryId" placeholder="请选择产品分类" clearable>
          <el-option
            v-for="category in categoryList"
            :key="category.categoryId"
            :label="category.categoryName"
            :value="category.categoryId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker clearable
          v-model="queryParams.createTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择创建时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="更新时间" prop="updateTime">
        <el-date-picker clearable
          v-model="queryParams.updateTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择更新时间">
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
          v-hasPermi="['product:info:add']"
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
          v-hasPermi="['product:info:edit']"
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
          v-hasPermi="['product:info:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['product:info:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="infoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品ID" align="center" prop="productId" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="分类" align="center" prop="categoryId">
        <template slot-scope="scope">
          {{ categoryMap.get(scope.row.categoryId) || scope.row.categoryId }}
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="产品描述" align="center" prop="description" />
      <el-table-column label="产品图片" align="center" prop="imageUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.imageUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="是否热销" align="center" prop="isHot">
          <template slot-scope="scope">
            {{ scope.row.isHot === 1 ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column label="是否新品" align="center" prop="isNew">
          <template slot-scope="scope">
            {{ scope.row.isNew === 1 ? '是' : '否' }}
          </template>
        </el-table-column>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            {{ scope.row.status === 1 ? '上架' : '下架' }}
          </template>
        </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['product:info:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['product:info:remove']"
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

    <!-- 添加或修改产品信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="产品分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择产品分类">
            <el-option
              v-for="category in categoryList"
              :key="category.categoryId"
              :label="category.categoryName"
              :value="category.categoryId">
            </el-option>
          </el-select>
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
            <el-option label="是" :value="1"></el-option>
            <el-option label="否" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="是否新品" prop="isNew">
          <el-select v-model="form.isNew" placeholder="请选择是否新品">
            <el-option label="是" :value="1"></el-option>
            <el-option label="否" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option label="上架" :value="1"></el-option>
            <el-option label="下架" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-divider content-position="center">产品配方信息</el-divider>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" icon="el-icon-plus" size="mini" @click="handleAddProductFormula">添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="handleDeleteProductFormula">删除</el-button>
          </el-col>
        </el-row>
        <el-table :data="productFormulaList" :row-class-name="rowProductFormulaIndex" @selection-change="handleProductFormulaSelectionChange" ref="productFormula">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="原料ID" prop="materialId" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.materialId" placeholder="请输入原料ID" />
            </template>
          </el-table-column>
          <el-table-column label="用量" prop="quantity" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantity" placeholder="请输入用量" />
            </template>
          </el-table-column>
          <el-table-column label="单位" prop="unit" width="150">
            <template slot-scope="scope">
              <el-input v-model="scope.row.unit" placeholder="请输入单位" />
            </template>
          </el-table-column>
        </el-table>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listInfo, getInfo, delInfo, addInfo, updateInfo } from "@/api/product/info"
import { listCategory } from "@/api/product/category"

export default {
  name: "Info",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedProductFormula: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 产品信息表格数据
      infoList: [],
      // 产品配方表格数据
      productFormulaList: [],
      // 产品分类列表
      categoryList: [],
      // 分类名称映射
      categoryMap: new Map(),
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        price: null,
        description: null,
        imageUrl: null,
        isHot: null,
        isNew: null,
        status: null,
        categoryId: null,
        createTime: null,
        updateTime: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "产品分类不能为空", trigger: "change" }
        ],
        price: [
          { required: true, message: "价格不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getCategoryList()
    this.getList()
  },
  methods: {
    /** 查询产品分类列表 */
    getCategoryList() {
      listCategory().then(response => {
        this.categoryList = response.data
        // 构建分类ID到分类名称的映射
        this.categoryList.forEach(category => {
          this.categoryMap.set(category.categoryId, category.categoryName)
        })
      })
    },
    /** 查询产品信息列表 */
    getList() {
      this.loading = true
      listInfo(this.queryParams).then(response => {
        this.infoList = response.rows
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
      this.productFormulaList = []
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
      this.ids = selection.map(item => item.productId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加产品信息"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const productId = row.productId || this.ids
      getInfo(productId).then(response => {
        this.form = response.data
        this.productFormulaList = response.data.productFormulaList
        this.open = true
        this.title = "修改产品信息"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 创建表单数据的副本，避免直接修改原始form对象
          const formData = { ...this.form }
          formData.productFormulaList = this.productFormulaList
          
          if (formData.productId != null) {
            updateInfo(formData).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addInfo(formData).then(response => {
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
      const productIds = row.productId || this.ids
      this.$modal.confirm('是否确认删除产品信息编号为"' + productIds + '"的数据项？').then(() => {
        return delInfo(productIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(error=>{
        // 检查是否是用户取消操作
        // Element UI的$modal.confirm取消时通常返回的error没有response属性，或者有特定的标记
        if (!error || !error.response) {
          // 用户取消操作，不显示错误信息
          return
        }
        
        // 处理真正的错误
        // 检查是否包含外键约束错误信息（order_detail表引用）
        const errorMsg = error.response.data && error.response.data.msg ? error.response.data.msg : '';
        if (errorMsg.includes('foreign key constraint') || error.message.includes('foreign key constraint')) {
          // 提供更友好的错误提示，指导用户如何解决
          this.$modal.msgError("无法删除产品：该产品正在被订单使用\n请先前往订单管理页面，删除相关的订单信息，然后再尝试删除该产品。")
        } else if (errorMsg) {
          // 如果有其他错误信息，显示该信息
          this.$modal.msgError(errorMsg)
        } else {
          // 默认错误提示
          this.$modal.msgError("删除失败，请检查产品是否被其他数据引用")
        }
      })
    },
	/** 产品配方序号 */
    rowProductFormulaIndex({ row, rowIndex }) {
      row.index = rowIndex + 1
    },
    /** 产品配方添加按钮操作 */
    handleAddProductFormula() {
      let obj = {}
      obj.materialId = ""
      obj.quantity = ""
      obj.unit = ""
      this.productFormulaList.push(obj)
    },
    /** 产品配方删除按钮操作 */
    handleDeleteProductFormula() {
      if (this.checkedProductFormula.length == 0) {
        this.$modal.msgError("请先选择要删除的产品配方数据")
      } else {
        const productFormulaList = this.productFormulaList
        const checkedProductFormula = this.checkedProductFormula
        this.productFormulaList = productFormulaList.filter(function(item) {
          return checkedProductFormula.indexOf(item.index) == -1
        })
      }
    },
    /** 复选框选中数据 */
    handleProductFormulaSelectionChange(selection) {
      this.checkedProductFormula = selection.map(item => item.index)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('product/info/export', {
        ...this.queryParams
      }, `info_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
