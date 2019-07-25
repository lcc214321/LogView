import logcollect from "../view/LogManage.vue"
export default [
    {
      path: "/basic", //基础信息
      component: Home,
      children: [
        {
          path: "log", //
          //meta: { privilegeCodes: "index_course" },
          component: logcollect
        }
      ]
    }
  ];
  