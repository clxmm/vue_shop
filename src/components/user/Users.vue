<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card class="box-card">
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="9">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>

      <!-- 用户列表 -->
      <el-table :data="userList" border stripe>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="电话" prop="mobile"></el-table-column>
        <el-table-column label="角色" prop="roleName"></el-table-column>
        <el-table-column label="状态">
          <template slot-scope="scope">
            <el-switch
                v-model="scope.row.mgState" @change="userStateChange(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary" size="mini" @click="showEditDialog(scope.row.id)" icon="el-icon-edit"></el-button>
            <!-- 删除按钮 -->
            <el-button type="danger" size="mini"  @click="deleteButton(scope.row.id)" icon="el-icon-delete"></el-button>
            <!-- 分配角色按钮 -->
            <el-tooltip effect="dark"  content="分配角色" placement="top-start" :enterable="false">
              <el-button type="warning" @click="showRolesDialog(scope.row)"  size="mini" icon="el-icon-setting"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryInfo.pageNumber"
          :page-sizes="[2, 3, 5, 10]"
          :page-size="queryInfo.pageSize"
          layout=" sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>

      <!-- 添加用户的对话框 -->
      <el-dialog
          title="提示"
          :visible.sync="addDialogVisible"
          @close="addDialogClose"
          width="50%">
        <!-- 内容主题区 -->
        <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="addForm.username"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="addForm.password"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="addForm.email"></el-input>
          </el-form-item>
          <el-form-item label="用户名" prop="mobile">
            <el-input v-model="addForm.mobile"></el-input>
          </el-form-item>
        </el-form>
        <!-- 底部区 -->
        <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
      </el-dialog>


      <!-- 修改用户的对话框 -->
      <el-dialog
          title="修改用户"
          :visible.sync="editDialogVisible"
          @close="editDialogClose"
          width="50%">
        <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
          <el-form-item label="用户名">
            <el-input v-model="editForm.username" disabled></el-input>
          </el-form-item>
          <el-form-item label="邮箱"  prop="email">
            <el-input v-model="editForm.email"></el-input>
          </el-form-item>
          <el-form-item label="手机" prop="mobile">
            <el-input v-model="editForm.mobile" ></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editUserInfo">确 定</el-button>
        </span>
      </el-dialog>


    </el-card>


    <!-- 分配角色 -->
    <el-dialog
        title="分配权限"
        :visible.sync="setRolesDialogVisible"
        width="50%"
        @close="closeRolesDialog()"
    >
      <div>
        <p>当前的用户：{{userInfo.name}}</p>
        <p>当前的角色：{{userInfo.roleName}}</p>
        <el-select v-model="selectRoleId" placeholder="请选择">
          <el-option
              v-for="item in rolesList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
          </el-option>
        </el-select>

      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRolesDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRolesDialog()">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    // 验证邮箱规则
    var checkEmail = (rule, value, cb) => {
      let reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      if (reg.test(value)) {
        return cb()
      }
      cb(new Error('请输入合法的邮箱'))
    }
    // 验证手机号
    var checkMobile = (rule, value, cb) => {
      let reg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      if (reg.test(value)) {
        return cb()
      }
      cb(new Error('请输入合法的手机号'))
    }
    return {

      // 获取用户列表的参数对象
      queryInfo: {
        query: '',
        pageSize: 2,
        pageNumber: 1
      },
      userList: [],
      total: 0,
      addDialogVisible: false,
      // 添加用户的表单数据
      addForm: {
        username: '',
        password: '',
        email: '',
        mobile: '',
        id: '',
      },
      // 添加表单的验证规则对象
      addFormRules: {
        username: [
          {required: true, message: "请输入用户名", trigger: 'blur'},
          {min: 3, max: 10, message: '用户名在3~10字符之间', trigger: 'blur'}
        ],
        password: [
          {required: true, message: "请输入密码", trigger: 'blur'},
          {min: 6, max: 15, message: '用户名在6~15字符之间', trigger: 'blur'}
        ],
        email: [
          {required: true, message: "请输入邮箱", trigger: 'blur'},
          {validator: checkEmail, trigger: 'blur'}
        ],
        mobile: [
          {required: true, message: "请输入手机号", trigger: 'blur'},
          {validator: checkMobile, trigger: 'blur'}
        ]
      },
      // 展示修改用户的对话框的展示
      editDialogVisible: false,
      // 查询到的用户对象
      editForm: {},
      // 修改表单的验证规则对象
      editFormRules: {
        email: [
          {required: true, message: "请输入邮箱", trigger: 'blur'},
          {validator: checkEmail, trigger: 'blur'}
        ],
        mobile: [
          {required: true, message: "请输入手机号", trigger: 'blur'},
          {validator: checkMobile, trigger: 'blur'}
        ]
      },
      setRolesDialogVisible: false,
      userInfo : {},
      // 所有角色的数据列表
      rolesList: [],
      selectRoleId: '',
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    // 保存分配的角色
    saveRolesDialog: function () {
        if (!this.selectRoleId) {
          return this.$message.error("请选择角色")
        }
        console.log(this.selectRoleId)


      // this.userInfo.id    用户id
      // this.selectRoleId  选择的角色id
      // 发送保存信息
      // this.$http.put();

      this.setRolesDialogVisible = false;
    },
    // 关闭分配角色对话框
    closeRolesDialog: function() {
        this.selectRoleId = '';
    },
    // 分配角色
    showRolesDialog: async function (userInfo) {
      this.userInfo = userInfo

      const  {data: res } = await this.$http.get("/roles/getAll")

      if (res.code != 200) {
        return this.$message.error("获取角色失败");
      }
      this.rolesList = res.data;
      // console.log(res)
      this.setRolesDialogVisible = true;
    },
    getUserList: async function () {
      const {data: res} = await this.$http.get('users/queryPaged', {params: this.queryInfo})
      // console.log(res);
      if (res.code != 200) {
        return this.$message.error('获取用户列表失败!');
      }
      this.userList = res.data.list
      this.total = res.data.total
    },
    // 监听pageSize 值改变
    handleSizeChange: function (newSize) {
      this.queryInfo.pageSize = newSize;
      this.getUserList();
    },
    //  监听页面值改变
    handleCurrentChange: function (newpage) {
      this.queryInfo.pageNumber = newpage
      this.getUserList()
    },
    // 监听 switch 开关状态的改变
    userStateChange: async function (userInfo) {
      // console.log(userInfo)
      const upDate = {
        id: userInfo.id,
        state: userInfo.mgState ? 0 : 1
      }
      // console.log(upDate)
      const {data: res} = await this.$http.put("/users/changeState", upDate)

      if (res.code != 200) {
        userInfo.mgState = !userInfo.mgState;
        return this.$message.error('更新转态失败!');
      }
      return this.$message.suucess('更新状态成功!');
    },
    // 对话框关闭
    addDialogClose: function () {
      this.$refs.addFormRef.resetFields()
    },
    // 添加用户
    addUser: function () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        // 可以发起添加用户的网络请求
        const {data: res} = await this.$http.post('/users/save', this.addForm)
        if (res.code != 200) {
          this.$message.error("添加用户失败")
        }
        this.$message.success("添加用户成功")
        // 隐藏添加用户的对话框
        this.addDialogVisible = false
        this.getUserList()
      })
    },
    // 展示编辑用户的对话框
    showEditDialog: async function (id) {
      this.editDialogVisible = true
      // console.log(id)

      const {data: res} = await this.$http.get("users/getById?id=" + id);
      // console.log(res);
      if (res.code != 200) {
        return this.$message.error("查询用户失败！")
      }
      this.editForm = res.data
    },
    // 监听x修改用户对话框的关闭事件
    editDialogClose: function () {
      this.$refs.editFormRef.resetFields()
    },
    // 修改用户信息并提交
    editUserInfo: function () {
      this.$refs.editFormRef.validate( async valid => {
        // console.log(valid);
        if (valid) {
          // 发起真正的请求
          const {data: res} = await this.$http.post('/users/save', this.editForm)
          if (res.code != 200) {
            return  this.$message.error("修改失败")
          }
        this.$message.success("修改成功")
          this.editDialogVisible = false
          this.getUserList()
        }
      })
    },
    // 确认删除
    deleteButton: function (id) {
      this.$confirm('此操作将永久该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then( async () => {
         const  {data: res } = await this.$http.delete("/users/delete/"+id);
        if (res.code != 200) {
          return this.$message.error("删除失败")
        }

        this.getUserList();
        this.$message({
          type: 'success',
          message: '删除成功!'
        });


      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });


    }

  },

};
</script>

<style lang="less" scoped>
</style>

