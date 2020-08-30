<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>商品管理</el-breadcrumb-item>
      <el-breadcrumb-item>商品分类</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 卡片视图区 -->
    <el-card>

      <el-row>
        <el-col>
          <el-button type="primary" @click="showAddCateDialog()">添加分类</el-button>
        </el-col>
      </el-row>

      <!-- 表格 -->
      <tree-table class="treeTable" :data="cateList" :columns="columns"
                  :selection-type="false"
                  :expand-type="false"
                  show-index
                  tree-type
                  index-text="#序"
                  border
      >
        <!-- 是否有效 -->
        <template slot="clxOk" slot-scope="scope">
          <i class="el-icon-success" v-if="scope.row.valid==1" style="color: #55a532"></i>
          <i class="el-icon-error" v-else style="color: red"></i>
        </template>
        <!-- 排序 -->
        <template slot="clxLevel" slot-scope="scope">
          <el-tag size="small" v-if="scope.row.level == 1 ">一级</el-tag>
          <el-tag size="mini" type="success" v-else-if="scope.row.level==2">二级</el-tag>
          <el-tag size="mini" type="warning" v-else>三级</el-tag>
        </template>
        <!-- 操作 -->
        <template slot="clxOpt" slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini">编辑</el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini">删除</el-button>

        </template>

      </tree-table>
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

    </el-card>

    <!-- 添加分类的对话框 -->
    <el-dialog
        title="添加分类"
        :visible.sync="addCateDialogVisible"
        width="50%"
        @close="addCateFormClose()"
    >
      <!-- 添加分类的表单 -->
      <el-form :model="addCatForm" :rules="addCatFormRules" ref="addCatFormRef" label-width="100px"
               class="demo-ruleForm"
      >

        <el-form-item label="分类名称" prop="name">
          <el-input v-model="addCatForm.name"></el-input>
        </el-form-item>
        <el-form-item label="父级分类">
          <!--  options指定数据源  -->
          <!-- props 用户指定配置对象 -->
          <el-cascader
              v-model="selectedKeys"
              :options="parentCateList"
              :props="cascaderProps"
              @change="parentCateChange()"
              clearable
          >
          </el-cascader>

        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addCateDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addCate()">确 定</el-button>
    </span>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "Cate",
  data() {
    return {
      // 选中的父级分类id 数组
      selectedKeys: [],
      // 级联选择器的配置对象
      cascaderProps: {
        checkStrictly: true,
        expandTrigger: 'hover',
        value: 'id',
        label: 'name',
        children: 'children'
      },
      // 父级分类的列表
      parentCateList: [],
      // 添加分类表单的验证规则对象
      addCatFormRules: {
        name: [
          {required: true, message: '请输入分类名称', trigger: 'blur'},
        ],
        cat_pid: []
      },
      // 添加分类的表单数据对象
      addCatForm: {
        // 分类的名称
        name: '',
        // 父级分类的id
        cat_pid: 0,
        // 默认没有选择，level为1
        cat_level: 1,
      },
      // 查询条件
      queryInfo: {
        type: 3,
        pageNumber: 1,
        pageSize: 5
      },
      cateList: [],
      // 总数据条数
      total: 0,
      // 为table 指定列的定义
      columns: [
        {
          label: '分类名称',
          prop: 'name',
        }, {
          label: '是否有效',
          // 当前列为模板列
          type: 'template',
          // 当前列使用的模板名称
          template: 'clxOk'
        }, {
          label: '排序',
          // 当前列为模板列
          type: 'template',
          // 当前列使用的模板名称
          template: 'clxLevel'
        }, {
          label: '操作',
          // 当前列为模板列
          type: 'template',
          // 当前列使用的模板名称
          template: 'clxOpt'
        }
      ],
      // 控制添加分类对话框的显示与隐藏
      addCateDialogVisible: false,
    }
  },
  created() {
    this.getCateList();
  },
  methods: {
    // 表单关闭
    addCateFormClose: function () {
      // console.log("表单关闭")
      // console.log(this.$refs)
      this.$refs.addCatFormRef.resetFields();
      this.selectedKeys = [];
      this.addCatForm.cat_pid = 0;
      this.addCatForm.cat_level = 0;
    },
    // 点击按钮，添加分类
    addCate: function () {
      console.log(this.addCatForm)
      this.$refs.addCatFormRef.validate(async valid => {
        if (!valid) {
          return;
        }
        const {data: res} = await this.$http.post("/categories/addCate", this.addCatForm);
        if (res.code != 200) {
          return this.$message.error("添加失败");
        }
        this.$message.success("添加成功");
        this.getCateList();
        this.addCateDialogVisible = false;
      })
    },
    // 选择项发生变化
    parentCateChange: function () {
      // console.log(this.selectedKeys)
      // 如果selectedKeys 数组的length 》 0 选择了父级分类
      if (this.selectedKeys.length > 0) {
        // 父级分类的id
        this.addCatForm.cat_pid = this.selectedKeys[this.selectedKeys.length - 1];
        // 当前分类的等级
        this.addCatForm.cat_level = this.selectedKeys.length + 1;
        return;
      } else {
        this.addCatForm.cat_pid = 0;
        this.addCatForm.cat_level = 0;
      }


    },
    //  获取父级分类的数据列表，不含第三极
    getParentCateList: async function () {
      const {data: res} = await this.$http("/categories/queryPage?type=2");
      // console.log(res);

      if (res.code != 200) {
        return this.$message.error("获取父级分类数据失败！")
      }
      this.parentCateList = res.data;


    },
    // 点击按钮添加分类的对话框
    showAddCateDialog: function () {
      this.getParentCateList()
      this.addCateDialogVisible = true;
    },
    handleSizeChange: function (newSize) {
      this.queryInfo.pageSize = newSize;
      this.getCateList();
    },
    //  监听页面值改变
    handleCurrentChange: function (newpage) {
      this.queryInfo.pageNumber = newpage
      this.getCateList();
    },
    getCateList: async function () {
      var info = this.queryInfo;
      const {data: res} = await this.$http.get("/categories/queryPage", {params: this.queryInfo});
      // console.log(res)
      if (res.code != 200) {
        return this.$message.error("分类数据失败！")
      }
      this.cateList = res.data.list;
      this.total = res.data.total;
      // console.log(this.cateList)
    }

  }

}
</script>

<style lang="less" scoped>
.treeTable {
  margin-top: 15px;
}

.el-cascader {
  width: 100%;
}

</style>