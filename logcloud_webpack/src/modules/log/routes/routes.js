import logcollect from "../view/LogManage.vue"
import Home from "../../portal/views/home/Home.vue"
export default [
    {
      path: "/log", //基础信息
      component: Home,
      children: [
        {
          path: "logmanage", //
          //meta: { privilegeCodes: "index_course" },
          component: logcollect
        }
      ]
    }
  ];
  