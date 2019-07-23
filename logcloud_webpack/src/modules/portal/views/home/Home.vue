<template>
  <el-container>
    <el-header style="padding: 0;">
      <el-menu class="el-menu" mode="horizontal">
        <el-menu-item index="1" style="width: 200px;">
          <router-link
            to="/home/overview"
            style="display: inline-block; font-size: 20px; line-height: 48px; width: 100%; text-decoration-line: none"
            title="回到主页"
          >
            <img
              src="./icon.png"
              style="margin-bottom: 2px; line-height: 48px; display: inline-block"
            />
            云平台主页
          </router-link>
        </el-menu-item>
        <el-menu-item
          index="4"
          style="float: right;"
          title="退出系统"
          @click="logout"
        >
          <v-icon name="sign-out-alt" />
          <span style="cursor: pointer"> 退出 </span>
        </el-menu-item>
        <el-menu-item index="3" style="float: right;" title="个人信息管理">
          <v-icon name="user" />
          <span @click="openUserDialog" style="cursor: pointer">
            {{ user.displayName }}
          </span>
        </el-menu-item>
        <el-menu-item
          index="2"
          class="navbar-group-item"
          style="float: right;"
          title="机构名称"
        >
          <v-icon name="users" /> {{ user.rootOrgName }}
        </el-menu-item>
      </el-menu>
    </el-header>

    <el-container>
      <el-container class="main-body">
        <router-view class="main-content"></router-view>
        <el-footer class="footer">&copy; 启明泰和 2019</el-footer>
      </el-container>
    </el-container>

    <!-- 添加用户信息弹出框 -->
    <el-dialog
      title="个人信息"
      width="400px"
      :visible.sync="userDialog"
      @close="() => this.$refs.passForm.clearValidate()"
    >
      <el-tabs value="first">
        <el-tab-pane label="用户权限" name="first">
          <el-form :inline="true" label-position="right" label-width="90px">
            <el-row :gutter="10">
              <el-col>
                <el-tag
                  v-for="role in user.roleList"
                  :key="role.roleId"
                  type="primary"
                  style="margin-left:10px;margin-top:10px;"
                >
                  {{ role.roleName }}
                </el-tag>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="修改密码" name="second">
          <el-form
            :inline="true"
            inline-message
            :model="passForm"
            ref="passForm"
            :rules="passRules"
            label-position="right"
            label-width="80px"
          >
            <el-row>
              <el-form-item label="密码" prop="pass">
                <el-input
                  type="password"
                  style="width: 150px"
                  v-model="passForm.pass"
                  auto-complete="off"
                  placeholder="请输入密码"
                />
              </el-form-item>
            </el-row>
            <el-row>
              <el-form-item label="确认密码" prop="checkPass">
                <el-input
                  type="password"
                  style="width: 150px"
                  v-model="passForm.checkPass"
                  auto-complete="off"
                  placeholder="请输入确认密码"
                />
              </el-form-item>
            </el-row>
            <el-row style="margin-left:100px">
              <el-button type="primary" @click="submitForm">保 存</el-button>
              <el-button @click="userDialog = false">取 消</el-button>
            </el-row>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
  </el-container>
</template>

<script>
import { mapActions, mapState } from "vuex";
import { USER_SIGNOUT } from "../../store/user";
import { BASIC_API } from "../../../../../constants/constants.js";

export default {
  name: "Home",
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.passForm.checkPass !== "") {
          this.$refs.passForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入确认密码"));
      } else if (value !== this.passForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      userDialog: false,
      passForm: { pass: "", checkPass: "" },
      passRules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }]
      }
    };
  },
  computed: {
    //...mapState({ user: state => state.user }),
    ifShowHomeSide() {
      return this.$route.fullPath.startsWith("/home") === false;
    },
    sideKey() {
      const module = this.$route.fullPath.split("/")[1];
      return module;
    }
  },
  methods: {
    ...mapActions([USER_SIGNOUT]),
    openUserDialog() {
      this.passForm = { pass: "", checkPass: "" };
      this.userDialog = true;
    },
    //保存密码
    submitForm() {
      this.$refs.passForm.validate(valid => {
        if (valid) {
          var userId = this.user.userId;
          var password = encodeURIComponent(this.passForm.pass);
          var url =
            CORE_API +
            "/user/password?userId=" +
            userId +
            "&password=" +
            password;
          this.$httpWithMsg.put(url).then(() => {
            this.$notify({
              type: "success",
              message: "修改密码成功！"
            });
            this.resetForm();
            this.userDialog = false;
          });
        } else {
          console.log("error submit!");
          return false;
        }
      });
    },
    //重置
    resetForm() {
      this.$refs.passForm.resetFields();
    },
    logout() {
      const orgId = this.user.rootOrgId;
      const getRootOrgId = () => {
        if (location.hostname.includes("qmth.com.cn")) {
          return "";
        } else {
          return "?orgId=" + orgId;
        }
      };

      this.$http
        .post(CORE_API + "/auth/logout")
        .then(() => {
          this.USER_SIGNOUT();
          window.name = "";
          this.$router.replace({
            path: "/login" + getRootOrgId()
          });
        })
        .catch(response => {
          if (response.status == 500) {
            this.$notify({
              showClose: true,
              message: response.data.desc,
              type: "error"
            });
          }
          this.USER_SIGNOUT();
          window.name = "";
          this.$router.replace({
            path: "/login" + getRootOrgId()
          });
        });
    }
  }
};
</script>

<style scoped>
.el-menu,
.el-footer {
  background-color: #3c8dbd;
  color: #ffffff;
  text-align: center;
  line-height: 60px;
}
.el-footer {
  color: #878e93;
  background-color: #ecf0f5;
  line-height: 40px;
  height: 40px !important;
}

.el-menu.el-menu--horizontal {
  border-bottom: none;
}

.el-menu >>> .el-menu-item {
  color: white !important;
}

.el-menu >>> .el-menu-item:hover,
.el-menu >>> .el-menu-item:focus {
  color: white !important;
  background-color: rgba(40, 121, 169) !important;
}

.el-menu >>> .is-active.el-menu-item:focus {
  color: white !important;
}

.el-menu >>> .navbar-group-item.is-active.el-menu-item:focus {
  color: none !important;
}

.el-menu >>> .navbar-group-item.el-menu-item:hover,
.el-menu >>> .navbar-group-item.el-menu-item:focus {
  color: white !important;
  background-color: transparent !important;
  cursor: unset;
  border-bottom: none;
}

body > .el-container {
  margin-bottom: 40px;
}

.main-body {
  min-height: calc(100vh - 100px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 20px;
  margin-left: 20px;
}

.main-content {
  min-height: calc(100vh - 60px - 60px - 40px);
  margin-top: 20px;
  margin-right: 20px;
}

.footer {
  justify-self: flex-end;
  margin-left: -20px;
}
</style>