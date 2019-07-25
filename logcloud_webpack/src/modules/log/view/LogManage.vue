<template>
  <div class="log">
    <el-row>
      <el-input v-model="queryEnvName" placeholder="请输入要查找的环境名称">
        <template slot="prepend">环境名称:</template>
      </el-input>
      <el-input v-model="querymicroserviceName" placeholder="请输入要查找的微服务名称">
        <template slot="prepend">微服务名称:</template>
      </el-input>
      <el-input v-model="queryServiceIP" placeholder="请输入要查找的微服务名称">
        <template slot="prepend">微服务ip:</template>
      </el-input>
    </el-row>
    <el-row>
      <el-input v-model="queryLogType" placeholder="请输入要查找的环境名称">
        <template slot="prepend">日志类型:</template>
        <el-select v-model="queryLogType" slot="append" placeholder="请选择">
          <el-option label="ERROR" value="ERROR"></el-option>
          <el-option label="INFO" value="INFO"></el-option>
          <el-option label="DEBUG" value="DEBUG"></el-option>
          <el-option label="WARN" value="WARN"></el-option>
        </el-select>
      </el-input>
      <el-date-picker v-model="querydate" type="date" placeholder="选择日期">
          <template slot="prepend">日期:</template>
      </el-date-picker>
      <el-input v-model="querycontent" placeholder="请输入要查找的内容">
        <template slot="prepend">查询内容:</template>
      </el-input>
    </el-row>
    <el-row>
      <el-button type="primary" icon="el-icon-search">查询</el-button>
      <el-button type="primary" icon="el-icon-plus">采集</el-button>
    </el-row>
    <div class="logtable">
      <el-table :data="loglist" style="width: 100%">
        <el-table-column prop="EnvName" label="环境名称" width="180">
        </el-table-column>
        <el-table-column prop="MicroServiceName" label="微服务名称" width="180">
        </el-table-column>
        <el-table-column prop="ipaddr" label="服务器ip">
        </el-table-column>
         <el-table-column prop="logtype" label="日志类型">
        </el-table-column>
         <el-table-column prop="collectdate" label="采集日期">
        </el-table-column>
         <el-table-column prop="updatetime" label="更新时间">
        </el-table-column>
         <el-table-column prop="originfilename" label="来源文件">
        </el-table-column>
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
        querydate: "",
        queryEnvName: "",
        querymicroserviceName: "",
        queryLogType: "ERROR",
        queryServiceIP: "",
        querycontent: "",
        loglist: []
      }
    }
  }

</script>
