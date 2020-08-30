<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品参数</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图 -->
    <el-card>
      <!-- 警告区域 -->
      <el-alert
          title="只允许为第三极分类设置相关参数！"
          type="warning"
          :closable="false"
          show-icon>
      </el-alert>

      <!-- 商品分类区域 -->
      <el-row class="cat_opt">
        <el-col>
          <span>商品分类选择： </span>
          <!-- 选择商品分类的级联选择框 -->
          <el-cascader
              v-model="selectedKeys"
              :options="cateList"
              :props="cascaderProps"
              @change="handleChange()"
              clearable
          >
          </el-cascader>
        </el-col>
      </el-row>

      <!-- tab 页签区域 -->
      <el-tabs v-model="activeName" @tab-click="handleTabClick">
        <!-- 天机动态参数的面板 -->
        <el-tab-pane label="添加参数" name="many">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDialogVisible = true">添加参数
          </el-button>
          <!-- 动态参数表格 -->
          <el-table :data="manyTableData" border stripe>
            <!-- 展开行 -->
            <el-table-column type="expand"></el-table-column>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="参数名称" prop="attrName"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">编辑
                </el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeParams(scope.row.id)">删除
                </el-button>
              </template>

            </el-table-column>

          </el-table>
        </el-tab-pane>
        <!-- 添加静态属性的面板 -->
        <el-tab-pane label="添加属性" name="only">
          <el-button type="primary" size="mini" :disabled="isBtnDisabled" @click="addDialogVisible = true">静态属性
          </el-button>
          <!-- 静态属性表格 -->
          <el-table :data="onlyTableData" border stripe>
            <!-- 展开行 -->
            <el-table-column type="expand"></el-table-column>
            <el-table-column type="index"></el-table-column>
            <el-table-column label="属性名称" prop="attrName"></el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">编辑
                </el-button>
                <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeParams(scope.row.id)">删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>


    </el-card>


    <!-- 添加参数的对话框 -->
    <el-dialog
        :title=" '添加' + titleTxt"
        :visible.sync="addDialogVisible"
        width="50%"
        @close="addDialogClosed"
    >
      <!-- 添加参数的对话框 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item :label="titleTxt" prop="attrName">
          <el-input v-model="addForm.attrName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addParams">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改参数的对话框 -->
    <el-dialog
        :title=" '修改' + titleTxt"
        :visible.sync="editDialogVisible"
        width="50%"
        @close="editDialogClosed"
    >
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item :label="titleTxt" prop="attrName">
          <el-input v-model="editForm.attrName"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editParams">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data() {
    return {

      // 修改表单的验证规则对象
      editFormRules: {
        attrName: [
          {required: true, message: "请输入参数名称", trigger: 'blur'}
        ],
      },
      // 编辑参数的表单数据对象
      editForm: {
        attrName: '',
      },
      editDialogVisible: false,
      // 添加表单的验证规则对象
      addFormRules: {
        attrName: [
          {required: true, message: "请输入参数名称", trigger: 'blur'}
        ],
      },
      // 添加参数的表单数据对象
      addForm: {
        attrName: '',
        id: ''
      },
      // 控制添加参数的对话框
      addDialogVisible: false,
      // 级联选择双向绑定到的数组
      selectedCateKeys: [],
      // 商品分类列表
      cateList: [],
      // 级联选择框的配置属性
      cascaderProps: {
        expandTrigger: 'hover',
        checkStrictly: true,
        value: 'id',
        label: 'name',
        children: 'children'
      },
      // 选中的数组
      selectedKeys: [],
      // 被激活的页签的名称
      activeName: 'many',
      // 动态参数的数据
      manyTableData: [],
      // 静态属性的数据
      onlyTableData: [],
    }
  },
  methods: {
    // 根据id 删除参数
    removeParams: async function (id) {

      const confirmResult = await this.$confirm('此操作将永久删除该参数, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(error => error);

      // 取消

      console.log(confirmResult);



      if (confirmResult !== 'confirm') {
        this.$message.info("取消删除");
        return;
      }

      // 删除
      const {data: res} = await this.$http.delete("/attrs/deleteById/" + id);
      if (res.code!=200) {
        this.$message.error("删除失败");
      }
      this.$message.success("删除成功")

      this.getParamData();

    },
    // editParams   保存修改
    editParams: function () {
      this.$refs.editFormRef.validate(async valid => {
        if (!valid) return;
        const {data: res} = await this.$http.post("/attrs/save", this.editForm);

        if (res.code != 200) {
          this.$message.error("修改失败");
        }

        this.editDialogVisible = false;
        this.$refs.editFormRef.resetFields();
        this.getParamData();
      })

    },
    // 重置修改的表单
    editDialogClosed: function () {
      this.$refs.editFormRef.resetFields();
    },
    // 修改按钮的点击事件
    showEditDialog: async function (id) {
      this.editDialogVisible = true

      // 根据id查询当前参数的信息
      const {data: res} = await this.$http.get("attrs/getAttrById?id=" + id);

      if (res.code != 200) {
        this.$message.error("获取参数信息失败！");
        return;
      }
      // console.log(res)
      this.editForm = res.data;
    },
    // 点击按钮，添加参数
    addParams: function () {
      this.$refs.addFormRef.validate(async valid => {
        if (!valid) return;
        const catId = this.catId;
        const {data: res} = await this.$http.post("/attrs/save", {
          catId: catId,
          attrSel: this.activeName,
          attrName: this.addForm.attrName,
        })

        if (res.code != 200) {
          return this.$message.error("添加参数失败");
        }

        this.$message.success("添加成功");
        this.addDialogVisible = false;
        this.getParamData();

      })
    },
    // 添加对话框的关闭
    addDialogClosed: function () {
      this.$refs.addFormRef.resetFields();
    },
    // 获取参数的列表数据
    getParamData: async function () {
      // 选择的不是三级分类
      if (this.selectedKeys.length != 3) {
        this.selectedKeys = [];
        this.$message.error("只能选择三级分类添加属性")
        return;
      }
      // console.log(this.selectedKeys)
      // 根据所选分类的id,和当前所处的面板，获取对应的参数，发起请求
      const id = this.catId;
      const type = this.activeName;
      const query = {
        id: id,
        sel: type,
      }

      const {data: rest} = await this.$http.get("/attrs/getAttrsByCatIdAndSel", {params: query});

      if (rest.code != 200) {
        this.$message.error("获取数据失败");
      }

      if (type === 'many') {
        this.manyTableData = rest.data;
      } else {
        this.onlyTableData = rest.data;
      }

    },

    // tab 页签点击时间的名字
    handleTabClick: function () {
      // console.log(this.activeName)
      this.getParamData();
    },
    // 级联选择框变化触发的事件
    handleChange: function () {
      this.getParamData();
    },
    //
    getCateList: async function () {
      const {data: rest} = await this.$http.get("/categories/queryPage?type=3")
      if (rest.code != 200) {
        return this.$message.success("获取列表数据失败")
      }
      this.cateList = rest.data;
      // console.log(this.cateList)
    }
  },
  created() {
    this.getCateList();
  },
  computed: {
    // 如果按钮需要被禁用，返回true,否则--> false
    isBtnDisabled() {
      if (this.selectedKeys.length != 3) {
        return true;
      }
      return false;
    },
    // 当前选中的三级分类的id
    catId: function () {
      if (this.selectedKeys.length == 3) {
        return this.selectedKeys[2];
      }
      return null;
    },
    // 动态计算标题的文本
    titleTxt: function () {
      if (this.activeName == 'many') {
        return '动态参数'
      } else {
        return '静态属性'
      }

    }

  },
  name: "Params"
}
</script>

<style lang="less" scoped>
.cat_opt {
  margin: 15px 0;
}

</style>