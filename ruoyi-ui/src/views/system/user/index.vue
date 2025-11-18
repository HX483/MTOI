<template>
  <div class="app-container">
    <el-card>
      <!-- 搜索条件 -->
      <Splitpanes class="default-theme" horizontal>
        <Pane size="30">
          <!-- 部门树 -->
          <div class="dept-tree">
            <el-input v-model="deptName" placeholder="部门名称" clearable prefix-icon="Search" />
            <el-tree
              ref="treeRef"
              :data="deptOptions"
              :props="defaultProps"
              :filter-node-method="filterNode"
              node-key="id"
              @node-click="handleNodeClick"
            />
          </div>
        </Pane>
        <Pane>
          <el-form ref="queryFormRef" :model="queryParams" label-width="80px">
            <el-row :gutter="20">
              <el-col :span="6">
                <el-form-item label="用户名称" prop="userName">
                  <el-input v-model="queryParams.userName" placeholder="请输入用户名称" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="手机号码" prop="phonenumber">
                  <el-input v-model="queryParams.phonenumber" placeholder="请输入手机号码" clearable />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="状态" prop="status">
                  <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
                    <el-option v-for="dict in dict.status" :key="dict.dictValue" :label="dict.dictLabel" :value="dict.dictValue" />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="时间范围">
                  <el-date-picker
                    v-model="dateRange"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="24" style="text-align: right;">
                <el-button type="primary" @click="handleQuery" icon="Search">搜索</el-button>
                <el-button @click="resetQuery" icon="RefreshLeft">重置</el-button>
              </el-col>
            </el-row>
          </el-form>
        </Pane>
      </Splitpanes>

      <!-- 工具栏 -->
      <div class="toolbar">
        <el-button type="primary" @click="handleAdd" icon="Plus" v-hasPermi="['system:user:add']">新增</el-button>
        <el-button type="primary" @click="handleDelete" :disabled="multiple" icon="Delete" v-hasPermi="['system:user:remove']">删除</el-button>
        <el-button type="primary" @click="handleExport" icon="Download" v-hasPermi="['system:user:export']">导出</el-button>
        <el-button type="primary" @click="handleImport" icon="Upload" v-hasPermi="['system:user:import']">导入</el-button>
        <RightToolbar :showSearch.sync="showSearch" @queryTable="getList" />
      </div>

      <!-- 数据表格 -->
      <el-table
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
        :header-cell-style="{background:'#f5f7fa',color:'#606266',fontWeight:'bold'}"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column prop="userId" label="用户编号" align="center" v-if="columns.userId.visible" />
        <el-table-column prop="userName" label="用户名称" align="center" v-if="columns.userName.visible" />
        <el-table-column prop="nickName" label="用户昵称" align="center" v-if="columns.nickName.visible" />
        <el-table-column prop="deptName" label="部门" align="center" v-if="columns.deptName.visible" />
        <el-table-column prop="phonenumber" label="手机号码" align="center" v-if="columns.phonenumber.visible" />
        <el-table-column prop="status" label="状态" align="center" v-if="columns.status.visible">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              @change="handleStatusChange(scope.row)"
              active-value="0"
              inactive-value="1"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" align="center" v-if="columns.createTime.visible" />
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template #default="scope">
            <el-button size="small" type="primary" @click="handleUpdate(scope.row)" icon="Edit" v-hasPermi="['system:user:edit']">修改</el-button>
            <el-dropdown @command="(command) => handleCommand(command, scope.row)">
              <el-button size="small" type="primary" icon="ArrowDown">更多操作</el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="handleResetPwd" v-hasPermi="['system:user:resetPwd']">重置密码</el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" v-hasPermi="['system:user:edit']">分配角色</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页组件 -->
      <Pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-card>

    <!-- 添加或修改用户对话框 -->
    <el-dialog
      v-model="open"
      :title="title"
      width="700px"
      append-to-body
      :before-close="cancel"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名称" prop="userName">
              <el-input v-model="form.userName" placeholder="请输入用户名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickName">
              <el-input v-model="form.nickName" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户部门" prop="deptId">
              <el-cascader
                v-model="form.deptId"
                :options="enabledDeptOptions"
                :props="defaultProps"
                placeholder="请选择部门"
                filterable
                clearable
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入手机号码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="电子邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入电子邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户性别" prop="sex">
              <el-radio-group v-model="form.sex">
                <el-radio value="0">男</el-radio>
                <el-radio value="1">女</el-radio>
                <el-radio value="2">未知</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户状态" prop="status">
              <el-switch v-model="form.status" active-value="0" inactive-value="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户密码" prop="password" v-if="!form.userId">
              <el-input v-model="form.password" placeholder="请输入用户密码" type="password" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="用户岗位" prop="postIds">
              <el-select v-model="form.postIds" multiple placeholder="请选择岗位">
                <el-option v-for="post in postOptions" :key="post.postId" :label="post.postName" :value="post.postId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="用户角色" prop="roleIds">
              <el-select v-model="form.roleIds" multiple placeholder="请选择角色">
                <el-option v-for="role in roleOptions" :key="role.roleId" :label="role.roleName" :value="role.roleId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" placeholder="请输入备注" type="textarea" :rows="4" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取消</el-button>
        <el-button type="primary" @click="submitForm">确定</el-button>
      </template>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog
      v-model="upload.open"
      :title="upload.title"
      width="400px"
      append-to-body
      :before-close="() => upload.open = false"
    >
      <el-upload
        ref="uploadRef"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="{ Authorization: 'Bearer ' + store.getters.token }"
        :action="import.meta.env.VITE_APP_BASE_API + '/system/user/importData'"
        name="file"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
      >
        <el-button slot="trigger" type="primary" icon="Upload">选取文件</el-button>
        <el-button type="primary" @click="submitFileForm" :loading="upload.isUploading" icon="UploadFilled">上传</el-button>
        <template #tip>
          <el-divider>导入说明：</el-divider>
          <el-alert title="请选择后缀为 xls 或 xlsx 的文件" type="info" show-icon :closable="false" />
          <el-alert title="文件模板下载" type="warning" show-icon :closable="false">
            <template #default>
              <el-button type="link" @click="importTemplate">下载模板</el-button>
            </template>
          </el-alert>
        </template>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox, ElAlert } from 'element-plus'
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus } from "@/api/system/user"
import { getDicts } from "@/api/system/dict/data"
import { getCurrentUserDept } from "@/api/system/user/dept"
import Pagination from "@/components/Pagination"
import RightToolbar from "@/components/RightToolbar"
import { parseTime, resetForm, download } from "@/utils/ruoyi"
import { useModal } from "@/composables/useModal"
import { Splitpanes, Pane } from "splitpanes"
import "splitpanes/dist/splitpanes.css"

// 获取store和路由
const store = useStore()
const router = useRouter()

// 获取modal实例
const { confirm, msgSuccess, msgError } = useModal()

// 响应式数据
const loading = ref(true)
const ids = ref([])
const single = ref(true)
const multiple = ref(true)
const showSearch = ref(true)
const total = ref(0)
const userList = ref([])
const title = ref("")
const open = ref(false)
const deptName = ref("")
const dateRange = ref([])
const initPassword = ref("")

// 查询参数
const queryParams = reactive({
  pageNum: 1,
  pageSize: 10,
  userName: undefined,
  phonenumber: undefined,
  status: undefined,
  deptId: undefined,
  params: {
    beginTime: undefined,
    endTime: undefined
  }
})

// 表单参数
const form = reactive({
  userId: undefined,
  deptId: undefined,
  userName: undefined,
  nickName: undefined,
  password: undefined,
  phonenumber: undefined,
  email: undefined,
  sex: undefined,
  status: "0",
  remark: undefined,
  postIds: [],
  roleIds: []
})

// 表单验证规则
const rules = reactive({
  userName: [
    { required: true, message: "用户名称不能为空", trigger: "blur" },
    { min: 2, max: 20, message: '用户名称长度必须介于 2 和 20 之间', trigger: 'blur' }
  ],
  nickName: [
    { required: true, message: "用户昵称不能为空", trigger: "blur" }
  ],
  password: [
    { required: true, message: "用户密码不能为空", trigger: "blur" },
    { min: 5, max: 20, message: '用户密码长度必须介于 5 和 20 之间', trigger: 'blur' },
    { pattern: /^[^<>\"'|\\]+$/, message: "不能包含非法字符：< > \" ' \\ |", trigger: "blur" }
  ],
  email: [
    {
      type: "email",
      message: "请输入正确的邮箱地址",
      trigger: ["blur", "change"]
    }
  ],
  phonenumber: [
    {
      pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
      message: "请输入正确的手机号码",
      trigger: "blur"
    }
  ]
})

// 部门树选项
const deptOptions = ref([])
const enabledDeptOptions = ref([])
const postOptions = ref([])
const roleOptions = ref([])

// 字典数据
const dict = reactive({
  status: []
})

// 部门树默认配置
const defaultProps = {
  children: "children",
  label: "label"
}

// 列信息
const columns = reactive({
  userId: { visible: true },
  userName: { visible: true },
  nickName: { visible: true },
  deptName: { visible: true },
  phonenumber: { visible: true },
  status: { visible: true },
  createTime: { visible: true }
})

// 上传参数
const upload = reactive({
  isUploading: false,
  title: "",
  open: false
})

// 表单引用
const queryFormRef = ref()
const formRef = ref()
const treeRef = ref()
const uploadRef = ref()

/** 查询用户列表 */
const getList = () => {
  loading.value = true
  listUser(queryParams).then(response => {
    userList.value = response.rows
    total.value = response.total
    loading.value = false
  })
}

/** 查询字典数据 */
const getDictData = (type) => {
  getDicts(type).then(response => {
    dict[type] = response.data
  })
}

/** 查询部门下拉树结构 */
const getDeptTree = () => {
  getCurrentUserDept().then(response => {
    deptOptions.value = response.data
    enabledDeptOptions.value = filterDisabledDept(JSON.parse(JSON.stringify(response.data)))
  })
}

// 过滤禁用的部门
const filterDisabledDept = (deptList) => {
  return deptList.filter(dept => {
    if (dept.disabled) {
      return false
    }
    if (dept.children && dept.children.length) {
      dept.children = filterDisabledDept(dept.children)
    }
    return true
  })
}

// 筛选节点
const filterNode = (value, data) => {
  if (!value) return true
  return data.label.indexOf(value) !== -1
}

// 节点单击事件
const handleNodeClick = (data) => {
  queryParams.deptId = data.id
  handleQuery()
}

// 用户状态修改
const handleStatusChange = (row) => {
  let text = row.status === "0" ? "启用" : "停用"
  confirm('确认要"' + text + '""' + row.userName + '"用户吗？').then(() => {
    return changeUserStatus(row.userId, row.status)
  }).then(() => {
    msgSuccess(text + "成功")
  }).catch(() => {
    row.status = row.status === "0" ? "1" : "0"
  })
}

// 取消按钮
const cancel = () => {
  open.value = false
  reset()
}

// 表单重置
const reset = () => {
  Object.assign(form, {
    userId: undefined,
    deptId: undefined,
    userName: undefined,
    nickName: undefined,
    password: undefined,
    phonenumber: undefined,
    email: undefined,
    sex: undefined,
    status: "0",
    remark: undefined,
    postIds: [],
    roleIds: []
  })
  if (formRef.value) {
    formRef.value.resetFields()
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNum = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  dateRange.value = []
  if (queryFormRef.value) {
    queryFormRef.value.resetFields()
  }
  queryParams.deptId = undefined
  if (treeRef.value) {
    treeRef.value.setCurrentKey(null)
  }
  handleQuery()
}

// 多选框选中数据
const handleSelectionChange = (selection) => {
  ids.value = selection.map(item => item.userId)
  single.value = selection.length != 1
  multiple.value = !selection.length
}

// 更多操作触发
const handleCommand = (command, row) => {
  switch (command) {
    case "handleResetPwd":
      handleResetPwd(row)
      break
    case "handleAuthRole":
      handleAuthRole(row)
      break
    default:
      break
  }
}

/** 新增按钮操作 */
const handleAdd = () => {
  reset()
  getUser().then(response => {
    postOptions.value = response.posts
    roleOptions.value = response.roles
    open.value = true
    title.value = "添加用户"
    form.password = initPassword.value
  })
}

/** 修改按钮操作 */
const handleUpdate = (row) => {
  reset()
  const userId = row?.userId || ids.value[0]
  getUser(userId).then(response => {
    Object.assign(form, response.data)
    postOptions.value = response.posts
    roleOptions.value = response.roles
    form.postIds = response.postIds
    form.roleIds = response.roleIds
    open.value = true
    title.value = "修改用户"
    form.password = ""
  })
}

/** 重置密码按钮操作 */
const handleResetPwd = (row) => {
  ElMessageBox.prompt('请输入"' + row.userName + '"的新密码', "提示", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    closeOnClickModal: false,
    inputPattern: /^.{5,20}$/,
    inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
    inputValidator: (value) => {
      if (/<|>"|'|\||\\|\//.test(value)) {
        return "不能包含非法字符：< > \" ' \\ |"
      }
    },
  }).then(({ value }) => {
      resetUserPwd(row.userId, value).then(response => {
        msgSuccess("修改成功，新密码是：" + value)
      })
    }).catch(() => {})
}

/** 分配角色操作 */
const handleAuthRole = (row) => {
  const userId = row.userId
  router.push("/system/user-auth/role/" + userId)
}

/** 提交按钮 */
const submitForm = () => {
  if (formRef.value) {
    formRef.value.validate(valid => {
      if (valid) {
        if (form.userId != undefined) {
          updateUser(form).then(response => {
            msgSuccess("修改成功")
            open.value = false
            getList()
          })
        } else {
          addUser(form).then(response => {
            msgSuccess("新增成功")
            open.value = false
            getList()
          })
        }
      }
    })
  }
}

/** 删除按钮操作 */
const handleDelete = (row) => {
  const userIds = row?.userId || ids.value.join(',')
  confirm('是否确认删除用户编号为"' + userIds + '"的数据项？').then(() => {
    return delUser(userIds)
  }).then(() => {
    getList()
    msgSuccess("删除成功")
  }).catch(() => {})
}

/** 导出按钮操作 */
const handleExport = () => {
  download('system/user/export', {
    ...queryParams
  }, `user_${new Date().getTime()}.xlsx`)
}

/** 导入按钮操作 */
const handleImport = () => {
  upload.title = "用户导入"
  upload.open = true
}

/** 下载模板操作 */
const importTemplate = () => {
  download('system/user/importTemplate', {}, `user_template_${new Date().getTime()}.xlsx`)
}

// 文件上传中处理
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true
}

// 文件上传成功处理
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false
  upload.isUploading = false
  if (uploadRef.value) {
    uploadRef.value.clearFiles()
  }
  ElMessageBox.alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true })
  getList()
}

// 提交上传文件
const submitFileForm = () => {
  if (uploadRef.value) {
    const file = uploadRef.value.uploadFiles
    if (!file || file.length === 0 || (!file[0].name.toLowerCase().endsWith('.xls') && !file[0].name.toLowerCase().endsWith('.xlsx'))) {
      msgError("请选择后缀为 xls或xlsx的文件。")
      return
    }
    uploadRef.value.submit()
  }
}

// 监听日期范围变化
watch(() => dateRange.value, (newVal) => {
  if (newVal && newVal.length > 0) {
    queryParams.params.beginTime = newVal[0]
    queryParams.params.endTime = newVal[1]
  } else {
    queryParams.params.beginTime = undefined
    queryParams.params.endTime = undefined
  }
}, { deep: true })

// 监听部门名称变化
watch(() => deptName.value, (val) => {
  if (treeRef.value) {
    if (val) {
      treeRef.value.filter(val)
    } else {
      treeRef.value.filter(null)
    }
  }
}, { immediate: true })

// 组件挂载时执行
onMounted(() => {
  getList()
  getDictData("sys_normal_disable")
  getDeptTree()
})
</script>