import Home from "../../portal/views/home/Home.vue"
import envmanage from "../../basic/view/EnvManager.vue"
import microservicemanage from "../../basic/view/MicroServiceManage.vue"
import user from "../../basic/view/UserManage.vue"
export default [
    {
      path: "/basic", //基础信息
      component: Home,
      children: [
        {
          path: "env", //
          //meta: { privilegeCodes: "index_course" },
          component: envmanage
        },
        {
          path: "micro", //专业管理
         // meta: { privilegeCodes: "index_specially" },
          component: microservicemanage
        },
        {
          path: "user", //用户管理
          //meta: { privilegeCodes: "index_user" },
          component: user
        }
      ]
    }
  ];
  