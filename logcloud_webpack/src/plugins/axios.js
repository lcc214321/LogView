import Vue from "vue";
import axios from "axios";
import router from "../router";
import { loadProgressBar } from "axios-progress-bar";
import networkInformationHint from "./networkInformationHint.js";

const ERROR_MSG_CONFIG = require("./errorMsgConfig").default;

// Full config:  https://github.com/axios/axios#request-config
// axios.defaults.baseURL = process.env.baseURL || process.env.apiUrl || '';
// axios.defaults.headers.common['Authorization'] = AUTH_TOKEN;
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

let config = {
  // baseURL: process.env.baseURL || process.env.apiUrl || ""
  timeout: 60 * 1000, // Timeout
  withCredentials: true, // Check cross-site Access-Control
  headers:{
    "Access-Control-Allow-Headers":"Authorization,Origin, X-Requested-With, Content-Type, Accept",
    "Access-Control-Allow-Origin":"*",
    'Content-Type':'application/json;charset=utf-8',
    "Access-Control-Allow-Methods":"GET,POST"
  }
};

const _$httpWith500Msg = axios.create(config);
const _$http = axios.create(config); // no auto 500 error UI
const _$httpWithoutBar = axios.create(config);

/**
 * A. token lifecycle
 * 1. /login UI => localStorage.removeItem('token') && localStorage.setItem('token')
 * 2. non /login UI => axios if(!wk_token) wk_token = window.sessionStorage.getItem("token"), send request
 * 3. if axios request fail with 401/403, wk_token = null, redirect to /login removeItem('token')
 * 4. logout to /login, before send request, invalidate wk_token
 * */

let wk_token, wk_key;


_$httpWith500Msg.interceptors.request.use(
  function(config) {
    networkInformationHint();
    // Do something before request is sent
    if (
      config.url.includes("/login") === false &&
      config.url.includes("/auth/thirdPartyAccess") === false
    ) {
      if (!wk_token) {
        const user = JSON.parse(window.sessionStorage.getItem("user"));
        if (!user) {
          if (
            window.___lastInvalidDate === undefined ||
            window.___lastInvalidDate < Date.now() - 300
          ) {
            Vue.prototype.$alert("登录失效,请重新登录！", "提示", {
              confirmButtonText: "确定",
              callback: () => {
                // if (process.env.NODE_ENV !== "production") {
                //   router.push("/login/" + "?orgId=" + wk_orgId);
                // } else {
                //   router.push("/login");
                // }
                router.push("/login/");
              }
            });
            window.___lastInvalidDate = Date.now();
          }
          return;
        }
        wk_token = user.token;
        wk_key = user.key;
      }
      if (wk_token && config.headers.common["token"] == null) {
        config.headers.common["token"] = wk_token;
        config.headers.common["key"] = wk_key;
      }
    } else {
      wk_token = null;
    }
    return config;
  },
  function(error) {
    // Do something with request error
    Vue.prototype.$notify({
      showClose: true,
      message: error,
      type: "error"
    });
    return Promise.reject(error);
  }
);

_$http.interceptors.request.use(
  // no auto 500 error UI
  function(config) {
    networkInformationHint();
    // Do something before request is sent
    if (config.url.includes("/login") === false) {
      if (!wk_token) {
        const user = JSON.parse(window.sessionStorage.getItem("user"));
        if (!user) {
          if (
            window.___lastInvalidDate === undefined ||
            window.___lastInvalidDate < Date.now() - 300
          ) {
            Vue.prototype.$alert("登录失效,请重新登录！", "提示", {
              confirmButtonText: "确定",
              callback: () => {
                router.push("/login/");
              }
            });
            window.___lastInvalidDate = Date.now();
          }
          return;
        }
        wk_token = user.token;
        wk_key = user.key;
      }
      if (wk_token && config.headers.common["token"] == null) {
        config.headers.common["token"] = wk_token;
        config.headers.common["key"] = wk_key;
      }
    } else {
      wk_token = null;
    }
    return config;
  },
  function(error) {
    // Do something with request error
    Vue.prototype.$notify({
      showClose: true,
      message: error,
      type: "error"
    });
    return Promise.reject(error);
  }
);

const recordRequest = response => {
  let matchedRoutePath;
  try {
    const matched = router.resolve(location).route.matched;
    const exactMatched = matched[matched.length - 1];
    matchedRoutePath = exactMatched.path;
  } catch (error) {
    console.log(error);
    // window._hmt.push([
    //   "_trackEvent",
    //   `页面-${location.pathname}`,
    //   "网络请求-响应",
    //   "解析出错"
    // ]);
  }
//   window._hmt.push([
//     "_trackEvent",
//     `页面-${matchedRoutePath || location.pathname}`,
//     "网络请求-响应",
//     new URL(response.config.url, "http://www.qmth.com.cn").pathname
//   ]);
};
// Add a response interceptor
_$httpWith500Msg.interceptors.response.use(
  response => {
    recordRequest(response);
    return response;
  },
  error => {
    console.log(error);
    if (!error.response) {
      // "Network Error" 网络不通，直接返回

      Vue.prototype.$notify({
        showClose: true,
        message: "网络连接异常，请检查网络设置。",
        type: "error"
      });
      return Promise.reject(error);
    }
    // 这里是返回状态码不为200时候的错误处理
    let status = error.response.status;

    // 登录失效 跳转登录页面
    if (status == 403 || status == 401) {
      if (
        window.___lastInvalidDate === undefined ||
        window.___lastInvalidDate < Date.now() - 300
      ) {
        Vue.prototype.$alert("登录失效，请重新登录！", "提示", {
          confirmButtonText: "确定",
          callback: () => {
            router.push("/login/");
          }
        });
        window.___lastInvalidDate = Date.now();
      }
      return Promise.reject(error);
    } else if (status == 405) {
      Vue.prototype.$alert("没有权限！", "提示", {
        confirmButtonText: "确定",
        callback: () => {
          router.push("/login/");
        }
      });
      return Promise.reject(error);
    } else if (status == 502) {
      Vue.prototype.$alert("服务器异常！", "提示", {
        confirmButtonText: "确定"
      });
      return Promise.reject(error);
    }

    if (status != 200) {
      const data = error.response.data;
      if (ERROR_MSG_CONFIG.map(v => v.code).includes(data.code)) {
        const MSG = ERROR_MSG_CONFIG.find(v => v.code === data.code);
        if (MSG.display) {
          Vue.prototype.$notify({
            showClose: true,
            message: MSG.message,
            type: "error"
          });
        }
      } else {
        if (data && data.desc) {
          Vue.prototype.$notify({
            showClose: true,
            message: data.desc,
            type: "error"
          });
        } else {
          Vue.prototype.$notify({
            showClose: true,
            message: "未定义异常: " + JSON.stringify(data, 2),
            type: "error"
          });
        }
      }
      return Promise.reject(error);
    }
  }
);

// Add a response interceptor
_$http.interceptors.response.use(
  // no auto 500 error UI
  response => {
    recordRequest(response);
    return response;
  },
  error => {
    if (!error.response) {
      Vue.prototype.$notify({
        showClose: true,
        message: "网络连接异常，请检查网络设置。",
        type: "error"
      });
      return Promise.reject(error);
    }
    // 这里是返回状态码不为200时候的错误处理
    let status = error.response.status;

    // 登录失效 跳转登录页面
    if (status == 403 || status == 401) {
      if (
        window.___lastInvalidDate === undefined ||
        window.___lastInvalidDate < Date.now() - 300
      ) {
        Vue.prototype.$alert("登录失效，请重新登录！", "提示", {
          confirmButtonText: "确定",
          callback: () => {
            router.push("/login/");
          }
        });
        window.___lastInvalidDate = Date.now();
      }
      return Promise.reject(error);
    } else if (status == 405) {
      Vue.prototype.$alert("没有权限！", "提示", {
        confirmButtonText: "确定",
        callback: () => {
          router.push("/login/");
        }
      });
      return Promise.reject(error);
    } else if (status == 502) {
      Vue.prototype.$alert("服务器异常！", "提示", {
        confirmButtonText: "确定"
      });
      return Promise.reject(error);
    }

    if (status != 200) {
      return Promise.reject(error);
    }
  }
);

_$httpWithoutBar.interceptors.request.use(
  // no auto 500 error UI
  function(config) {
    networkInformationHint();
    // Do something before request is sent
    if (config.url.includes("/login") === false) {
      if (!wk_token) {
        const user = JSON.parse(window.sessionStorage.getItem("user"));
        if (!user) {
          if (
            window.___lastInvalidDate === undefined ||
            window.___lastInvalidDate < Date.now() - 300
          ) {
            Vue.prototype.$alert("登录失效,请重新登录！", "提示", {
              confirmButtonText: "确定",
              callback: () => {
                router.push("/login/");
              }
            });
            window.___lastInvalidDate = Date.now();
          }
          return;
        }
        wk_token = user.token;
        wk_key = user.key;
      }
      if (wk_token && config.headers.common["token"] == null) {
        config.headers.common["token"] = wk_token;
        config.headers.common["key"] = wk_key;
      }
    } else {
      wk_token = null;
    }
    return config;
  },
  function(error) {
    // Do something with request error
    Vue.prototype.$notify({
      showClose: true,
      message: error,
      type: "error"
    });
    return Promise.reject(error);
  }
);
_$httpWithoutBar.interceptors.response.use(
  // no auto 500 error UI
  response => {
    recordRequest(response);
    return response;
  },
  error => {
    if (!error.response) {
      Vue.prototype.$notify({
        showClose: true,
        message: "网络连接异常，请检查网络设置。",
        type: "error"
      });
      return Promise.reject(error);
    }
    // 这里是返回状态码不为200时候的错误处理
    let status = error.response.status;

    // 登录失效 跳转登录页面
    if (status == 403 || status == 401) {
      if (
        window.___lastInvalidDate === undefined ||
        window.___lastInvalidDate < Date.now() - 300
      ) {
        Vue.prototype.$alert("登录失效，请重新登录！", "提示", {
          confirmButtonText: "确定",
          callback: () => {
            router.push("/login/");
          }
        });
        window.___lastInvalidDate = Date.now();
      }
      return Promise.reject(error);
    } else if (status == 405) {
      Vue.prototype.$alert("没有权限！", "提示", {
        confirmButtonText: "确定",
        callback: () => {
          router.push("/login/");
        }
      });
      return Promise.reject(error);
    } else if (status == 502) {
      Vue.prototype.$alert("服务器异常！", "提示", {
        confirmButtonText: "确定"
      });
      return Promise.reject(error);
    }

    if (status != 200) {
      return Promise.reject(error);
    }
  }
);

Plugin.install = function(Vue) {
  Vue.$http = _$http; // no auto 500 error UI
  Object.defineProperties(Vue.prototype, {
    $http: {
      get() {
        return _$http; // no auto 500 error UI
      }
    }
  });

  Vue.$httpWithMsg = _$httpWith500Msg;
  Object.defineProperties(Vue.prototype, {
    $httpWithMsg: {
      get() {
        return _$httpWith500Msg;
      }
    }
  });

  // for below request
  // config.url.includes("/api/ecs_ques/paper/") === false &&
  // config.url.includes("/api/ecs_ques/questionAudio") === false
  const _a = axios.create(config);
  Vue.$httpWithoutAuth = _a;
  Object.defineProperties(Vue.prototype, {
    $httpWithoutAuth: {
      get() {
        return _a;
      }
    }
  });

  Vue.$httpWithoutBar = _$httpWithoutBar;
  Object.defineProperties(Vue.prototype, {
    $httpWithoutBar: {
      get() {
        return _$httpWithoutBar;
      }
    }
  });
};

Vue.use(Plugin);

loadProgressBar({}, Vue.$http);
loadProgressBar({}, Vue.$httpWithMsg);
loadProgressBar({}, Vue.$httpWithoutAuth);

// const update = (type, e) => {
//   // debugger;
//   console.log(type);
//   console.log(
//     "e.target.url: ",
//     e.target.responseURL,
//     " timeStamp: ",
//     e.timeStamp.toFixed(2),
//     " loaded:",
//     e.loaded,
//     " total: ",
//     e.total
//   );
//   console.log(e);
// };
// Vue.$httpWithMsg.defaults.onDownloadProgress = e => {
//   update("下载", e);
// };
// Vue.$httpWithMsg.defaults.onUploadProgress = e => {
//   update("上传", e);
// };

import "axios-progress-bar/dist/nprogress.css";
export default Plugin;