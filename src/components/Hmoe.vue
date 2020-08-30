<template>

    
    <el-container class="home-container">
      <!-- 头部区域 -->
      <el-header>
        <div>
          <img src="../assets/heima.png" alt="" >
          <span>电商管理后台管理系统</span>
        </div> 
          <el-button type="info" @click="lougout">退出</el-button>
      </el-header>
      <!-- 页面主题区域 -->
      <el-container>
        <!-- 侧边栏 -->
        <el-aside :width="isCollapse ? '64px': '200px'">
          <div class="toggle-button"   @click="toggleColl">|||</div>
          <!-- 侧边栏菜单 -->
          <el-menu background-color="#333744" text-color="#fff"
            :unique-opened="true"
            :collapse="isCollapse"
            :collapse-transition="false"
            :router="true"
            :default-active='activePath'
            active-text-color="#409eff">
            <!-- 一级菜单 -->
            <el-submenu :index="item.id + ''" :key="item.id"  v-for="item in menuList">
              <!-- 一级菜单的模板区 -->
              <template slot="title">
                <!-- 图标 -->
                <i :class="item.icon"></i>
                <!-- 文本 -->
                <span>{{item.authName}}</span>
              </template>
              <!-- 二级菜单 -->
              <el-menu-item :index=" '/' + subItem.path" :key="subItem.id" v-for="subItem in item.children"
                @click="saveNavClick('/' + subItem.path )"
                >
                <template slot="title">
                <!-- 图标 -->
                <i class="el-icon-menu"></i>
                <!-- 文本 -->
                <span>{{subItem.authName}}</span>
              </template>
              </el-menu-item>
              
            </el-submenu>
          </el-menu>

        </el-aside>
        <!-- 右侧主题内容 -->
        <el-main>
          <!-- 路由占位 -->
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>

</template>

<script>
export default {
  data() {
    return {
      // 左侧菜单数据
      menuList: [],
      //  侧边栏是否折叠
      isCollapse: false,
      // 被激活的链接地址
      activePath: '',
    }
  },
  created() {
    this.getMenuList(),
    this.activePath =  window.sessionStorage.getItem("activePath")
  },
  methods: {
    lougout: function() {
      window.sessionStorage.clear();
      this.$router.push("/login");
    },
    // 获取所有的菜单
    getMenuList: async function() {
      const { data: rest} = await this.$http.get("/menu/menus");
      console.log(rest);
      if (rest.code != 200) {
        return this.$message.error("获取菜单失败！")
      }
      this.menuList = rest.data;
    },
    // 点击按钮，实现菜单折叠
    toggleColl: function() {
      this.isCollapse = !this.isCollapse;
    },
    //保存链接的激活状态
    saveNavClick: function (path) {
      window.sessionStorage.setItem("activePath", path);
      this.activePath = path;
    }
  }
};
</script>

<style lang="less" scoped>
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between;
  padding-left: 0;
  align-items: center;
  color: #ffffff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}
.el-aside {
  background-color: #333744;
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: #ededf1;
}
.home-container {
  height: 100%;
}
.iconfont {
  margin-right: 10px;
}
.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #ffffff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
