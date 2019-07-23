import Vue from "vue";

let ___networkInformationHintLastTime = Date.now();
const sessionNoNetworkHint = "networkInformationHint-nohint";

export default function networkInformationHint() {
  if (navigator.connection && navigator.connection.downlink) {
    if (navigator.connection.downlink < 1 || navigator.connection.rtt > 1000) {
      window._hmt.push([
        "_trackEvent",
        "网络状况不佳",
        "下载小于1Mb，延迟大于1000ms"
      ]);

      if (
        Date.now() - ___networkInformationHintLastTime > 3000 &&
        !sessionStorage.getItem(sessionNoNetworkHint)
      ) {
        Vue.prototype.$notify({
          showClose: true,
          duration: 2000,
          title: "当前网络状况不佳！",
          message: `当前网速：下载(${
            navigator.connection.downlink
          }Mb) 网络延时(${navigator.connection.rtt}ms) (点击此行可不再提示)`,
          type: "warning",
          onClick: () => {
            Vue.prototype
              .$alert("不在提示网络状况？", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消"
              })
              .then(() => {
                sessionStorage.setItem(sessionNoNetworkHint, true);
              });
          }
        });
        ___networkInformationHintLastTime = Date.now();
      }
    }
  }
}
