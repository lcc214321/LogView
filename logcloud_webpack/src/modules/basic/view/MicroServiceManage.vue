<template>
  <div class="microenv">
    <el-row>
      <el-input v-model="queryEnvName" placeholder="请输入要查找的环境名称">
        <template slot="prepend">环境名称:</template>
      </el-input>
      <el-input v-model="queryMicroServiceName" placeholder="请输入要查找的微服务名称">
        <template slot="prepend">微服务名称:</template>
      </el-input>
      <el-button type="primary" icon="el-icon-search">查询</el-button>
      <el-button type="primary" icon="el-icon-plus">创建</el-button>
      <el-button type="primary" icon="el-icon-upload">导入</el-button>
    </el-row>
    <div class="MicroEnvtable">
      <el-table :data="MicroEnvlist" style="width: 100%">
        <el-table-column prop="EnvName" label="环境名称" width="180">
        </el-table-column>
        <el-table-column prop="MicroServiceName" label="微服务名称" width="180">
        </el-table-column>
        <el-table-column prop="ipaddr" label="IP地址">
        </el-table-column>
        <el-table-column prop="ostype" label="操作系统">
        </el-table-column>
        <el-table-column prop="loginuser" label="登录用户">
        </el-table-column>
        <el-table-column prop="logpath" label="采集日志路径">
        </el-table-column>
        <el-table-column prop="lognamepattern" label="采集日志名匹配规则">
        </el-table-column>
        <el-table-column prop="logpattern" label="采集日志起始行正则匹配方式(多个则以间隔符号隔开)">
        </el-table-column>
        <el-table-column prop="lognamepostfix" label="日志后缀名称(多个则以间隔符号隔开)">
        </el-table-column>
        <el-table-column prop="splitstr" label="间隔符号">
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog title="新增微服务" width="450px" :visible.sync="addingDialog">
        <el-form :inline="true" inline-message :model="EnvForm" ref="addingForm" :rules="rules" label-position="right"
          label-width="120px">
          <el-row>
            <el-form-item label="环境名称" prop="EnvName">
              <el-select class="input_width_lg" multiple v-model="MircroEnvForm.EnvName" placeholder="请选择环境名称"
                @change="rolesChanged">
                <el-option v-for="item in EnvNameList" :label="item.EnvName" :value="item.id" :key="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="微服务名称" prop="MicroServiceName">
              <el-input class="input_width_lg" v-model="MircroEnvForm.MicroServiceName" auto-complete="off"
                placeholder="请输入环境名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="操作系统" prop="ostype">
              <el-select class="input_width_lg" multiple v-model="MircroEnvForm.ostype" placeholder="请选择操作系统类型"
                @change="ostypeChanged">
                <el-option :label="windows" value="windows">
                </el-option>
                <el-option :label="linux" value="linux">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="IP地址" prop="ipaddr">
              <el-input class="input_width_lg" v-model="MircroEnvForm.ipaddr" auto-complete="off"
                placeholder="请输入微服务ip地址" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="微服务登录用户名称" prop="loginuser">
              <el-input class="input_width_lg" v-model="MircroEnvForm.loginuser" auto-complete="off"
                placeholder="请输入登录用户名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="微服务登录用户密码" prop="loginpassword">
              <el-input type="password" class="input_width_lg" v-model="MircroEnvForm.loginpassword" auto-complete="off"
                placeholder="请输入登录用户密码" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采集日志路径" prop="logpath">
              <el-input class="input_width_lg" v-model="MircroEnvForm.logpath" auto-complete="off"
                placeholder="请输入采集日志所在路径" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采集日志名称匹配规则" prop="lognamepattern">
              <el-input class="input_width_lg" v-model="MircroEnvForm.lognamepattern" auto-complete="off"
                placeholder="请输入采集日志名称匹配规则(正则表达式)" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采集日志起始行正则匹配方式" prop="logpattern">
              <el-input class="input_width_lg" v-model="MircroEnvForm.logpattern" auto-complete="off"
                placeholder="请输入采集日志起始行正则匹配方式" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="日志后缀名称" prop="lognamepostfix">
              <el-input class="input_width_lg" v-model="MircroEnvForm.lognamepostfix" auto-complete="off"
                placeholder="请输入日志后缀名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="间隔符号" prop="splitstr">
              <el-input class="input_width_lg" v-model="MircroEnvForm.splitstr" auto-complete="off"
                placeholder="请输入间隔符号(默认;)" />
            </el-form-item>
          </el-row>
          <el-row class="d-flex justify-content-center">
            <el-button type="primary" @click="add">保 存</el-button>
            <el-button @click="addingDialog = false">取 消</el-button>
          </el-row>
        </el-form>
      </el-dialog>
      <el-dialog title="编辑环境" width="450px" :visible.sync="editDialog">
        <el-form :inline="true" inline-message :model="EnvForm" ref="addingForm" :rules="rules" label-position="right"
          label-width="120px">
          <el-row>
            <el-form-item label="环境名称" prop="EnvName">
              <el-select class="input_width_lg" multiple v-model="MircroEnvForm.EnvName" placeholder="请选择环境名称"
                @change="rolesChanged">
                <el-option v-for="item in EnvNameList" :label="item.EnvName" :value="item.id" :key="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="微服务名称" prop="MicroServiceName">
              <el-input class="input_width_lg" v-model="MircroEnvForm.MicroServiceName" auto-complete="off"
                placeholder="请输入环境名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="操作系统" prop="ostype">
              <el-select class="input_width_lg" multiple v-model="MircroEnvForm.ostype" placeholder="请选择操作系统类型"
                @change="ostypeChanged">
                <el-option :label="windows" value="windows">
                </el-option>
                <el-option :label="linux" value="linux">
                </el-option>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="IP地址" prop="ipaddr">
              <el-input class="input_width_lg" v-model="MircroEnvForm.ipaddr" auto-complete="off"
                placeholder="请输入微服务ip地址" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="微服务登录用户名称" prop="loginuser">
              <el-input class="input_width_lg" v-model="MircroEnvForm.loginuser" auto-complete="off"
                placeholder="请输入登录用户名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="微服务登录用户密码" prop="loginpassword">
              <el-input type="password" class="input_width_lg" v-model="MircroEnvForm.loginpassword" auto-complete="off"
                placeholder="请输入登录用户密码" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采集日志路径" prop="logpath">
              <el-input class="input_width_lg" v-model="MircroEnvForm.logpath" auto-complete="off"
                placeholder="请输入采集日志所在路径" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采集日志名称匹配规则" prop="lognamepattern">
              <el-input class="input_width_lg" v-model="MircroEnvForm.lognamepattern" auto-complete="off"
                placeholder="请输入采集日志名称匹配规则(正则表达式)" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="采集日志起始行正则匹配方式" prop="logpattern">
              <el-input class="input_width_lg" v-model="MircroEnvForm.logpattern" auto-complete="off"
                placeholder="请输入采集日志起始行正则匹配方式" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="日志后缀名称" prop="lognamepostfix">
              <el-input class="input_width_lg" v-model="MircroEnvForm.lognamepostfix" auto-complete="off"
                placeholder="请输入日志后缀名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="间隔符号" prop="splitstr">
              <el-input class="input_width_lg" v-model="MircroEnvForm.splitstr" auto-complete="off"
                placeholder="请输入间隔符号(默认;)" />
            </el-form-item>
          </el-row>
          <el-row class="d-flex justify-content-center">
            <el-button type="primary" @click="add">保 存</el-button>
            <el-button @click="addingDialog = false">取 消</el-button>
          </el-row>
        </el-form>
      </el-dialog>
    </div>
  </div>

</template>
<script>
  export default {
    name:"MicroServiceManage",
    data() {
      return {
        addingDialog: false,
        editDialog: false,
        queryEnvName: "",
        queryMicroServiceName: "",
        MircroEnvForm: {
          MicroServiceId: "",
          EnvName: "",
          EnvNameList: [],
          MicroServiceNam: "",
          Remark: ""
        },
        MicroEnvlist: []
      }
    }
  };

</script>
