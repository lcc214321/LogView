<template>
  <el-main style="display: flex; align-items: center; margin-bottom: 20px;">
    <el-row width="100vw">
      <el-col
        :span="11"
        :offset="1"
        v-for="(menu, index) in menuList.filter(m => m.parentId === null)"
        :key="menu.id"
      >
        <div
          :class="['module-card', index % 2 ? 'float-left' : 'float-right']"
          @click="() => $router.push('/' + menu.ext4)"
        >
          <div style="position: relative">
            <img :class="['module-image', menu.ext4 + '-main-module']" />
            <div class="cover"></div>
          </div>
          <div
            class="align-self-left d-flex d-flex flex-column align-items-start module-desc"
          >
            <div class="h4">{{ menu.name }}</div>
            <div style="width: 400px; font-size: 14px; text-align: left;">
              {{ moduleDesc[menu.ext4] && moduleDesc[menu.ext4].detail }}
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </el-main>
</template>

<script>
const moduleDesc = {
  basic: {
    detail: "包括用户管理和环境管理"
  },
  log: {
    detail: "包括日志查询和下载"
  }
};

import { mapState } from "vuex";
import { BASIC_API } from "../../../../../../constants/constants.js";

export default {
  name: "HomeMain",
  data() {
    return {
      moduleDesc: moduleDesc,
      menuList: []
    };
  },
  components: {},
  computed: {
    ...mapState({ user: state => state.user })
  },
  methods: {
    async getUserPrivileges(groupCode) {
      var url = BASIC_API + "/rolePrivilege/getUserPrivileges";
      const params = new URLSearchParams();
      params.append("groupCode", groupCode);
      params.append("full", false);
      const res = await this.$httpWithMsg.post(url, params, {
        headers: { "content-type": "application/x-www-form-urlencoded" }
      });

      return res.data;
    }
  },
  async created() {
    //this.menuList = await this.getUserPrivileges("PORTAL_MENUS");
    this.menuList=[{"code":"BASIC","name":"基础管理","parentId":null,"hasPrivilege":true,"description":null,"updateTime":"2018-11-20 13:55:46","creationTime":"2018-06-19 12:42:12","weight":100,"ext1":"8848","ext2":"#icon-core","ext3":"BASIC_MENUS","ext4":"basic","ext5":null,"nodeName":"基础信息","nodeId":"6414","parentNodeId":null,"nodeCode":"BASIC"},{"code":"LOG","name":"日志管理","parentId":null,"hasPrivilege":true,"description":null,"updateTime":"2018-12-17 11:22:05","creationTime":"2018-06-19 12:42:53","weight":90,"ext1":"8858","ext2":"#icon-log-work","ext3":"LOG_MENUS","ext4":"log","ext5":null,"nodeName":"日志管理","nodeId":"6415","parentNodeId":null,"nodeCode":"LOG"}]
  }
};
</script>

<style scoped>
.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.main-content {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  justify-items: center;
}

.module-card {
  height: 120px;
  margin-bottom: 50px;
  margin-right: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: space-around;

  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
}
</style>

<style scoped>
.module-card:hover .h4 {
  color: #4d7cc4 !important;
}
.module-card:hover div {
  color: #65738b !important;
}
.module-image {
  width: 90px;
  height: 90px;
  background-position: center;
  background-repeat: no-repeat;
  background-color: #4d7cc4;
  border-top-left-radius: 20px 20px;
  border-bottom-left-radius: 20px 20px;
}
.module-card:hover .module-image {
  background-color: #55bfff;
  box-shadow: 0px 5px 20px 0px #55bfff;
  color: #4d7cc4 !important;
}
.module-card .cover {
  width: 100px;
  height: 116px;
  position: absolute;
  top: -13px;
  left: 0px;
  transition: all 1s ease-in-out;
  border-top-left-radius: 10px;
  border-bottom-left-radius: 10px;
  background-color: rgba(255, 255, 255, 0.8);
}
.module-card:hover .cover {
  /* background-color: transparent !important; */
  /* left: -100px; */
  width: 0px !important;
  left: -80px !important;
  /* transition: all 2s ease-in-out; */
}
.module-desc {
  background-color: white;
  padding: 10px;
}
.module-card:hover .module-desc {
  box-shadow: 7px 7px 10px 0px rgba(0, 0, 0, 0.1);
}
.basic-main-module {
  background-image: url("./images/basic.png");
}
.module-card:hover .basic-main-module {
  background-image: url("./images/basic-hover.png");
}
.log-main-module {
  background-image: url("./images/log.png");
}
.module-card:hover .log-main-module {
  background-image: url("./images/log-hover.png");
}

</style>