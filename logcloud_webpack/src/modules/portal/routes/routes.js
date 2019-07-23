import Login from "../views/Login.vue";
import Home from "../views/home/Home.vue";
import HomeMain from "../views/home/main/HomeMain.vue";
import Component404 from "../views/Component404.vue";

export default [
  {
    path: "/",
    name: "Root",
    meta: { pageName: "首页" },
    component: Login
  },
  {
    path: "/login",
    name: "Login",
    component: Login
  },
  {
    path: "/home",
    component: Home,
    meta: { pageName: "云平台概览" },
    children: [
      {
        path: "overview",
        component: HomeMain
      }
    ]
  },
  {
    // will match everything
    path: "*",
    component: Component404
  }
  // {
  //   path: "/about",
  //   name: "about",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "./views/About.vue")
  // }
];