<template>
  <el-aside width="" v-if="menuList.length > 0">
    <div style="height: 50px; margin-top: 20px; margin-left: 20px; font-size: 20px">
      <el-button type="text" class="float-right" size="mini" style="padding: 0; padding-right: 16px; outline: none;"
        @click="toggoleSidebar">
      </el-button>
    </div>
    <el-menu class="el-menu-vertical-demo" background-color="#222c32" text-color="#fff" active-text-color="#409eff"
      router :default-active="$route.path" :collapse="isCollapse">
      <el-menu-item v-for="menu2 in menuList" :index="menu2.ext5" :key="menu2.id" :route="{ path: menu2.ext5 }">
        <span style="margin-left: 9px;">{{ menu2.name }}</span>
      </el-menu-item>

    </el-menu>
  </el-aside>
</template>

<script>
  const routesToMenu = [{
      path: "/basic",
      name: "基础管理",
      groupCode: "BASIC_MENUS"
    },
    {
      path: "/log",
      name: "日志管理",
      groupCode: "LOG_MENUS"
    },
  ]

  const logMenuList=[{"id": 1000,
    "code": "org",
    "name": "日志管理",
    "parentId": null,
    "groupId": 1,
    "groupCode": "LOG_MENUS",
    "hasPrivilege": true,
    "description": "cao",
    "updateTime": "2018-06-19 10:55:36",
    "creationTime": "2018-06-08 13:25:10",
    "weight": 99,
    "ext1": "menu",
    "ext2": null,
    "ext3": null,
    "ext4": null,
    "ext5": "/log/logmanage",
    "nodeName": "日志管理",
    "nodeCode": "org",
    "nodeId": "1000",
    "parentNodeId": null
  }]
  const basicMenuList = [{
    "id": 1000,
    "code": "org",
    "name": "用户管理",
    "parentId": null,
    "groupId": 1,
    "groupCode": "BASIC_MENUS",
    "hasPrivilege": true,
    "description": "cao",
    "updateTime": "2018-06-19 10:55:36",
    "creationTime": "2018-06-08 13:25:10",
    "weight": 99,
    "ext1": "menu",
    "ext2": null,
    "ext3": null,
    "ext4": null,
    "ext5": "/basic/user",
    "nodeName": "用户管理",
    "nodeCode": "org",
    "nodeId": "1000",
    "parentNodeId": null
  }, {
    "id": 1103,
    "code": "index_env",
    "name": "环境管理",
    "parentId": null,
    "groupId": 1,
    "groupCode": "BASIC_MENUS",
    "hasPrivilege": true,
    "description": null,
    "updateTime": "2019-01-22 11:29:56",
    "creationTime": "2018-06-08 13:24:55",
    "weight": 0,
    "ext1": "menu",
    "ext2": "",
    "ext3": null,
    "ext4": null,
    "ext5": "/basic/env",
    "nodeName": "环境管理",
    "nodeCode": "index_env",
    "nodeId": "1103",
    "parentNodeId": null
  }, {
    "id": 1104,
    "code": "index_micro",
    "name": "微服务管理",
    "parentId": null,
    "groupId": 1,
    "groupCode": "BASIC_MENUS",
    "hasPrivilege": true,
    "description": null,
    "updateTime": "2019-01-22 11:29:56",
    "creationTime": "2018-06-08 13:24:55",
    "weight": 0,
    "ext1": "menu",
    "ext2": "",
    "ext3": null,
    "ext4": null,
    "ext5": "/basic/micro",
    "nodeName": "微服务管理",
    "nodeCode": "index_micro",
    "nodeId": "1104",
    "parentNodeId": null
  }]

  export default {
    data() {
      return {
        isCollapse: false,
        menuList: []
      }
    },
    methods: {
      toggoleSidebar() {
        this.isCollapse = !this.isCollapse;
      },
      updatePath() {
        let currentPaths = [];
        let part = this.menuList.find(v => v.ext5 === this.$route.path);
        if (!part) {
          this.UPDATE_CURRENT_PATHS([]);
          return;
        }
        currentPaths.unshift(part.name);
        while (this.menuList.find(v => v.id === part.parentId)) {
          part = this.menuList.find(v => v.id === part.parentId);
          currentPaths.unshift(part.name);
        }
        // console.log(currentPaths);
        this.UPDATE_CURRENT_PATHS(currentPaths);
      }
    },
    async created() {
           this.group = routesToMenu.find(v => this.$route.path.startsWith(v.path));  
           const groupCode = this.group && this.group.groupCode;
           console.log(groupCode)
           if (groupCode==="LOG_MENUS") {
             this.menuList = logMenuList;
             //this.UPDATE_MENU_LIST(this.menuList);
             //this.updatePath();
           }else if(groupCode==="BASIC_MENUS"){
               this.menuList = basicMenuList;
           }
    },
    watch: {
      $route() {
        // this.updatePath();
      }
    }
  }

</script>
