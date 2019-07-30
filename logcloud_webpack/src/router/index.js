import Vue from 'vue'
import Router from 'vue-router'
import PortalRoutes from "../modules/portal/routes/routes";
import BasicRoutes from "../modules/basic/routes/routes"
import LogRoutes from "../modules/log/routes/routes"

Vue.use(Router)

let router = new Router({
  mode: "history",
  base: "/admin",
  routes: [
    ...PortalRoutes,
    ...BasicRoutes,
    ...LogRoutes
  ]
});
export default router;