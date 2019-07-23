import Vue from 'vue'
import Router from 'vue-router'
import PortalRoutes from "../modules/portal/routes/routes";

Vue.use(Router)

let router = new Router({
  mode: "history",
  base: "/admin",
  routes: [
    ...PortalRoutes
  ]
});
export default router;