<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>权限管理</el-breadcrumb-item>
      <el-breadcrumb-item>角色列表</el-breadcrumb-item>
    </el-breadcrumb>

    <el-card>
      <!-- 添加角色按钮 -->
      <el-row>
        <el-col>
          <el-button type="primary">添加角色</el-button>
        </el-col>
      </el-row>

      <!-- 角色列表去 -->
      <el-table :data="rolesList" border stripe>

        <el-table-column type="expand">
          <template slot-scope="scope">
<!--            {{scope.row}}-->
            <el-row :class="['dbootom',i1 === 0 ? 'bdtop':'','vcenter' ]"    v-for="(item1,i1) in scope.row.rightsList" >
              <!-- 一级权限 -->
              <el-col :span="5">
                <el-tag> {{item1.name}}</el-tag>
                <i class="el-icon-caret-right"></i>
              </el-col>
              <!-- 二级权限 -->
              <el-col :span="19">
                <el-row :class="[i2 === 0 ? '':'bdtop','vcenter']" v-for="(item2,i2) in item1.rightsList">
                  <el-col :span="6">
                    <el-tag type="success"> {{item2.name}}</el-tag>
                    <i class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="18">
                    <el-tag :closable="true" @close="rmoveRightsById(scope.row,item3.id)" type="warning" v-for="(item3,i3) in item2.rightsList">
                         {{item3.name}}
                    </el-tag>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </template>

        </el-table-column>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="角色名称" prop="name"></el-table-column>
        <el-table-column label="角色描述" prop="remark"></el-table-column>
        <el-table-column label="操作" width="300px">
          <template slot-scope="scope">
            <el-button type="primary" size="mini"  icon="el-icon-edit">编辑</el-button>
            <el-button type="danger" size="mini"  icon="el-icon-delete">删除</el-button>
            <el-button type="warning" size="mini"  @click="showAllRigths(scope.row)" icon="el-icon-setting">分配权限</el-button>
          </template>
        </el-table-column>

      </el-table>
    </el-card>

    <!-- 分配权限 -->
    <el-dialog
        title="分配权限"
        :visible.sync="setRightsDialogVisible"
        width="50%"
        @close="closeRightsDialog()"
        >
      <!-- 树形组件 -->
      <el-tree :data="rightsList" :props="treeProps" :default-expand-all="true"
               :default-checked-keys="defKeys"
               ref="treeRef"
               node-key="id" :show-checkbox="true"></el-tree>


      <span slot="footer" class="dialog-footer">
        <el-button @click="setRightsDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRightsDialog()">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  name: "Roles",
  data() {
    return {
      rolesList: [],
      // 控制分配权限对话框的显示
      setRightsDialogVisible: false,
      rightsList: [],
      // 树形控件的属性绑定对象
      treeProps: {
        label: 'name',
        children: 'menus',
      },
      // 默认选中的节点id值数组
      defKeys: [4,3],
      // 要被分配权限的角色id
      roleId: '',
    }
  },
  created() {
    // 获取全部角色
    this.getRolesList()
  },
  methods: {
    // 保存权限
    saveRightsDialog: function() {
        const  keys = [
            ...this.$refs.treeRef.getCheckedKeys(),
          ...this.$refs.treeRef.getHalfCheckedKeys()
        ];
        console.log(keys)
      console.log(this.roleId)
      const  idStr = keys.join(",")
      // 发送请求去保存
      // this.$http.post()


      this.setRightsDialogVisible  = false;
    },
    closeRightsDialog: function() {
      this.defKeys = [];
    },
    // 展示分配权限对话框，并填充数据
    showAllRigths: async function(role) {
      this.roleId = role.id;
      this.setRightsDialogVisible = true;
      const { data: res } =await  this.$http.get("/rights/rights?type=tree");
      if (res.code != 200) {
        return this.$message.error("获取权限失败");
      }

      this.rightsList = res.data;
      // console.log(this.rolesList)
      },
    // 获取全部角色
    getRolesList: async function () {

      const {data: res} = await this.$http.get("roles/getAllRoles");

      if (res.code != 200) {
        this.$message.error("获取角色成")
      }
      this.rolesList = res.data;
      console.log(this.rolesList)
    },
    // 根据id删除
    rmoveRightsById: function (role,rightsId) {
        this.$confirm('此操作将永久该权限, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then( async () => {
          // // 还没有接口
          // const  {data: res } = await this.$http.delete("/rights/delete/"+id);
          // if (res.code != 200) {
          //   return this.$message.error("删除失败")
          // }


          // 会有刷新
          // this.getRolesList();


          // role.rightsList = res.data;

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
  }
}
</script>

<style lang="less" scoped>

.el-tag {
  margin: 7px;
}
.bdtop {
  border-top: 1px solid #eee;
}
.dbootom {
  border-bottom: 1px solid #eeeeee;
}

.vcenter {
  display: flex;
  align-items: center;
}

</style>