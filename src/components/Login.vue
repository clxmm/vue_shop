<template>
    <div class="login_container">
       <div class="login_box">
           <!-- 头像区 -->
           <div class="avator_box">
               <img src="../assets/logo.png" alt="">
           </div>
           <!-- 表单区 -->
           <el-form ref="loginFormRef" :model="loginForm"  :rules="loginFormRuls" label-width="0px" class="login_form">
               <!-- 用户名 -->
                <el-form-item prop="username">
                    <el-input v-model="loginForm.username" prefix-icon="iconfont icon-user"></el-input>
                </el-form-item>
                <!-- 密码 -->
                <el-form-item prop="password">
                    <el-input type="password" v-model="loginForm.password" prefix-icon="iconfont icon-3702mima"></el-input>
                </el-form-item>
                <!-- 按钮区域 -->
                <el-form-item class="btns" >
                    <el-button type="primary" @click="loginFormButton">登录</el-button>
                    <el-button type="info" @click="restLoginForm">重置</el-button>
                </el-form-item>
           </el-form>
       </div>
    </div>
</template>

<script>
export default {
  data () {
    return {
    // 登录表单的数据绑定对象
      loginForm: {
        username: 'admin',
        password: '123'
      },
      // 表单验证规则对象
      loginFormRuls: {
        // 验证用户名是否合法
        // 验证用户名是否合法
        username: [
          { required: true, message: '请输入登录名称', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 11 个字符', trigger: 'blur' }
        ],
        // 验证用户名是否合法
        password: [
          { required: true, message: '请输入登录密码', trigger: 'blur' },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 重置
    restLoginForm: function () {
      this.$refs.loginFormRef.resetFields()
      this.loginForm.username = ''
      this.loginForm.password = ''
    },
    // 登录
    loginFormButton: function () {
      this.$refs.loginFormRef.validate(async valid => {
        // console.log(valid)
        // eslint-disable-next-line no-useless-return
        if (!valid) return
        const { data: res } = await this.$http.post('login/', this.loginForm)
        if(res.code != 200) return this.$message.error("登陆失败")
            this.$message.success("登录成功！")
            // 将登录成功后的token 保存到客户端的 sessionStorage 中
            window.sessionStorage.setItem("token",res.token);
            // 2. 通过编程式导航，跳转到 /home主页
            this.$router.push('/home')
      })
    }
  }
}
</script>

<style lang="less" scoped>
.login_container {
  background: #2b4b6b;
  height: 100%;
}

.login_box {
  width: 450px;
  height: 300px;
  background: white;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  .avator_box {
    height: 130px;
    width: 130px;
    border: 1px solid #eeeeee;
    border-radius: 50%;
    padding: 10px;
    box-shadow: 0 0 10px #eee;
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #fff;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
  .login_form {
    position: absolute;
    bottom: 0px;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }

  .btns {
    display: flex;
    justify-content: flex-end;
  }
}
</style>
