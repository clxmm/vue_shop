import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Hmoe.vue'

import Welcome from '../components/Welcome.vue'
import Users from '../components/user/Users.vue'
import Rights from "@/components/power/Rights";
import Roles from "@/components/power/Roles";
import Cate from "@/components/goods/Cate";
import Params from "@/components/goods/Params";

Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: 'login' },
  { path: '/login', component: Login },
  { path: '/home', component: Home,
    redirect: '/welcome',
    children: [
      {path: '/welcome', component: Welcome},
      {path: '/users', component: Users},
      {path: '/rights', component: Rights},
      {path: '/roles', component: Roles},
      {path: '/categories', component: Cate},
      {path: '/params', component: Params},

    ]
  },
]

const router = new VueRouter({
  routes
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
  // to将要访问的路径
  // from 从哪里来
  //next 是一个函数 放行  next()  放行  next("/login")  强制跳转
  if (to.path === '/login') return next();
  //鉴权
  //获取token
  const tokenStr = window.sessionStorage.getItem('token')
  if ( tokenStr!=''&& !tokenStr) return next('/login');
  next();
})

export default router
