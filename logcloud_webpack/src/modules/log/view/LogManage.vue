<template>
  <div class="log">
    <el-form inline :model="formSearch" label-width="90px" ref="primaryForm" style="float:left">
      <el-form-item label="环境名称">
        <el-select
          class="input_width_lg"
          placeholder="请选择环境"
          v-model="formSearch.queryEnvId"
          filterable
          remote="true"
          :remote-method="getEnv"
          @change="changeEnv"
          clearable
          @clear="getEnv"
        >
          <el-option v-for="item in envList" :key="item.id" :label="item.envname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="微服务名称">
        <el-select
          class="input_width_lg"
          placeholder="请选择微服务名称"
          v-model="formSearch.queryMicroEnvId"
          filterable
          clearable
        >
          <el-option
            v-for="item in MicroEnvlist"
            :key="item.id"
            :label="item.microservicename"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="日志类型">
        <el-select v-model="formSearch.queryLogType" placeholder="请选择">
          <el-option label="ERROR" value="ERROR"></el-option>
          <el-option label="INFO" value="INFO"></el-option>
          <el-option label="DEBUG" value="DEBUG"></el-option>
          <el-option label="WARN" value="WARN"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="采集日期">
        <el-date-picker v-model="formSearch.querydate" type="date" placeholder="选择日期">
          <template slot="prepend">日期:</template>
        </el-date-picker>
      </el-form-item>
      <el-form-item label="查询内容">
        <el-input v-model="formSearch.querycontent" placeholder="请输入要查找的内容"></el-input>
      </el-form-item>
      <el-form-item class="d-block">
        <el-button type="primary" size="small" icon="el-icon-search" @click="getLogPage">查询</el-button>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handlecollect">采集</el-button>
      </el-form-item>
    </el-form>
    <div class="logtable">
      <el-table :data="loglist" style="width: 100%">
        <el-table-column prop="EnvName" label="环境名称" width="180"></el-table-column>
        <el-table-column prop="MicroServiceName" label="微服务名称" width="180"></el-table-column>
        <el-table-column prop="ipaddr" label="服务器ip"></el-table-column>
        <el-table-column prop="logtype" label="日志类型"></el-table-column>
        <el-table-column prop="collectdate" label="采集日期"></el-table-column>
        <el-table-column prop="updatetime" label="更新时间"></el-table-column>
        <el-table-column prop="originfilename" label="来源文件"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleView(scope.$index, scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      formSearch: {
        querydate: "",
        queryEnvId: "",
        queryMicroEnvId: "",
        queryLogType: "ERROR",
        queryServiceIP: "",
        querycontent: ""
      },
      loglist: [],
      MicroEnvlist: [],
      envList: []
    };
  },
  methods: {
    async getLogPage() {},
    changeEnv(val) {
      if (val === "") {
        return;
      }
      var url = BASIC_API + "/microenv/queryMicroByEnvId?queryEnvId=" + val;
      this.$httpWithMsg.get(url).then(response => {
        this.MicroEnvlist = response.data;
      });
    },
    getEnv() {
      var url = BASIC_API + "/env/queryEnvList";
      this.$httpWithMsg.get(url).then(response => {
        console.log(response);
        this.envList = response.data;
      });
    },
    handlecollect() {}
  }
};
</script>
<style scoped>
.input_width_lg {
  width: 180px;
}
</style>
