<template>
  <div class="bg">
    <!-- <header class="login-header">
          <img v-if="!jwptCustomize" src="../assets/images/login_footer_logo.jpg" />
          <img v-else src="../assets/images/new_login_logo.png" />
          <span class="qm-logo-text" v-if="!jwptCustomize">考试云平台</span>
          <span class="qm-logo-text" v-else>{{ title }}</span>
        </header> -->

    <main class="login-main">
      <!-- <img class="left_tree" src="../assets/images/login_main_left_tree.png" /> -->

      <div class="logo-text">日志采集工具</div>
      <div class="right_login">
        <h1 style="font-size: 20px; color: #666">欢迎登录</h1>
        <div class="username">
          <input
            @keyup.enter="login"
            type="text"
            id="accountValue"
            v-model="loginInfo.username"
            placeholder="请输入账号"
          />
        </div>
        <div class="password">
          <input
            @keyup.enter="login"
            type="password"
            id="password"
            v-model="loginInfo.password"
            placeholder="请输入密码"
          />
        </div>
        <button class="login-btn" @click="login">登 录</button>
      </div>
      <footer class="login-footer">
        Copyright &copy; 2019 <a href="javascript:void(0)">武汉启明软件</a>.
      </footer>
    </main>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import { USER_SIGNIN } from "../store/user";
import { BASIC_API } from "../../../../constants/constants.js";

export default {
  data() {
    return {
      errorInfo: "",
      title: "日志采集工具",
      jwptCustomize: false,
      dialogVisible: false,
      loginInfo: {
        username: "",
        password: "",
      }
    };
  },
  methods: {
    ...mapActions([USER_SIGNIN]),

    checkAccountValue() {
      this.errorInfo = "";
      if (!this.loginInfo.username) {
        this.errorInfo += "账号不能为空!\n";
      }
      if (this.errorInfo) {
        this.$notify({
          showClose: true,
          message: this.errorInfo,
          type: "error"
        });
        return false;
      }
      return true;
    },

    checkPassword() {
      this.errorInfo = "";
      if (!this.loginInfo.password) {
        this.errorInfo += "密码不能为空!\n";
      }
      if (this.errorInfo) {
        this.$notify({
          showClose: true,
          message: this.errorInfo,
          type: "error"
        });
        return false;
      }
      return true;
    },

    login() {
      if (!this.checkAccountValue()) {
        return;
      }
      if (!this.checkPassword()) {
        return;
      }
      var url =BASIC_API+ "/auth/login";
      this.$httpWithMsg
        .post(url, this.loginInfo)
        .then(response => {
          var user = response.data;
          this.USER_SIGNIN(user);
          this.$router.replace({ path: "/home/overview" });
          this.$notify({
            message: "登录成功",
            type: "success",
            duration: 1000
          });
        })
        .catch(error => {
          if (error.response.data.code === "B-003100") {
            this.dialogVisible = true;
          }
        });
    },
    loginWithSms() {
      this.dialogVisible = false;
      this.login();
    },
  },
  created() {
    sessionStorage.clear();
  }
};
</script>

<style scoped>
.bg {
  width: 100vw;
  height: 100vh;
  background-image: url("../assets/images/bg.jpg");
  background-size: cover;
}

.qm-logo-text {
  font-size: 36px;
  font-weight: 700;
  font-stretch: normal;
  line-height: 36px;
  letter-spacing: 0px;
  margin-left: 40px;
}

.logo-text {
  /* font-family: "Xingkai SC", "STXingkai", "KaiTi"; */
  width: 100%;
  height: 40px;
  font-size: 40px;
  margin-bottom: 30px;
  font-weight: bold;
  font-stretch: normal;
  line-height: 48px;
  letter-spacing: 0px;
  color: #3968d7;
  text-shadow: 0px 7px 4px rgba(77, 124, 196, 0.3);
  text-align: center;
  letter-spacing: 0.2em;
}

.login-main {
  width: 100%;
  height: 90vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.right_login {
  width: 480px;
  height: 330px;
  background-color: #ffffff;
  box-shadow: 0px 7px 20px 0px rgba(77, 124, 196, 0.1);
  border-radius: 38px;
  opacity: 0.8;
  padding: 0 80px;
}

.right_login h1 {
  text-align: center;
  height: 20px;
  font-size: 20px;
  font-weight: normal;
  font-stretch: normal;
  line-height: 24px;
  letter-spacing: 0px;
  color: #666666;
  margin: 30px 0;
}

.right_login .username {
  height: 46px;
  display: flex;
  align-items: center;
  justify-items: center;
}
.right_login .password {
  height: 46px;
  display: flex;
  align-items: center;
  justify-items: center;
  margin-top: 30px;
}

.right_login input {
  width: 100%;
  padding: 10px 30px;
  margin-left: -28px;
  font-size: 16px;
  border-radius: 10px;
}

.right_login input::placeholder {
  font-size: 16px;
  color: #000000;
  opacity: 0.3;
}

.login-btn {
  margin-top: 30px;
  margin-bottom: 20px;
  width: 100%;
  background-color: #4d7cc4;
  font-size: 25px;
  color: #fff;
  cursor: pointer;
  border-radius: 27px;
}

.login-btn:hover {
  box-shadow: 0 16px 29px rgba(29, 170, 240, 0.3);
}

.login-footer {
  clear: both;
  width: 240px;
  height: 40px;
  line-height: 40px;
  background-color: #fff;
  color: #999999;
  margin: -20px auto;
  text-align: center;
  border-radius: 25px;
  z-index: 3;
}
</style>