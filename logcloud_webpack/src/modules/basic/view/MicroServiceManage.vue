<template>
  <div class="microenv">
    <el-form inline :model="formSearch" label-width="70px" ref="primaryForm" style="float:left">
      <el-form-item label="环境名称">
        <el-select class="input_width_lg" placeholder="请选择环境" v-model="formSearch.queryEnvId" filterable remote
          :remote-method="getEnv" @change="changeEnv" clearable @clear="getEnv">
          <el-option v-for="item in envList" :key="item.id" :label="item.envname" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="微服务名称">
        <el-select class="input_width_lg" placeholder="请选择微服务名称" v-model="formSearch.queryMicroEnvId" filterable
          clearable>
          <el-option v-for="item in MicroEnvlist" :key="item.id" :label="item.microservicename" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item class="d-block">
        <el-button type="primary" size="small" icon="el-icon-search" @click="getEnvPage">查询</el-button>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleadd">创建</el-button>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleimport">导入</el-button>
      </el-form-item>
    </el-form>
    <div class="MicroEnvtable">
      <el-table :data="MicroEnvTablelist" style="width: 100%">
        <el-table-column prop="envname" label="环境名称" width="180"></el-table-column>
        <el-table-column prop="microservicename" label="微服务名称" width="180"></el-table-column>
        <el-table-column prop="ipaddr" label="IP地址"></el-table-column>
        <el-table-column prop="ostype" label="操作系统"></el-table-column>
        <el-table-column prop="loginuser" label="登录用户"></el-table-column>
        <el-table-column prop="logpath" label="采集日志路径"></el-table-column>
        <el-table-column prop="lognamepattern" label="采集日志名匹配规则"></el-table-column>
        <el-table-column prop="logpattern" label="采集日志起始行正则匹配方式(多个则以间隔符号隔开)"></el-table-column>
        <el-table-column prop="lognamepostfix" label="日志后缀名称(多个则以间隔符号隔开)"></el-table-column>
        <el-table-column prop="splitstr" label="间隔符号"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page.sync="curpageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="10"
        layout="total, sizes, prev, pager, next, jumper" :total="totalpage"></el-pagination>
      <el-dialog title="新增微服务" width="450px" :visible.sync="addingDialog" @close="closeAddingForm">
        <el-form :inline="true" inline-message :model="MircroEnvForm" ref="addingForm" :rules="rules"
          label-position="right" label-width="120px">
          <el-row>
            <el-form-item label="环境名称" prop="EnvId">
              <el-select class="input_width_lg" v-model="MircroEnvForm.EnvId" placeholder="请选择环境名称" filterable remote
                :remote-method="getEnv" clearable>
                <el-option v-for="item in envList" :label="item.envname" :value="item.id" :key="item.id"></el-option>
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
              <el-select class="input_width_lg" v-model="MircroEnvForm.ostype" placeholder="请选择操作系统类型">
                <el-option label="windows" value="windows"></el-option>
                <el-option label="linux" value="linux"></el-option>
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
            <el-button type="primary" @click="addMicroEnv">保 存</el-button>
            <el-button @click="closeAddingForm">取 消</el-button>
          </el-row>
        </el-form>
      </el-dialog>
      <el-dialog title="编辑环境" width="450px" :visible.sync="editDialog" @close="closeEditForm">
        <el-form :inline="true" inline-message :model="MircroEnvForm" ref="editForm" :rules="rules"
          label-position="right" label-width="120px">
          <el-row>
            <el-form-item label="环境名称" prop="EnvId">
              <el-select class="input_width_lg" v-model="MircroEnvForm.EnvId" placeholder="请选择环境名称" filterable remote
                :remote-method="getEnv" clearable>
                <el-option v-for="item in envList" :label="item.envname" :value="item.id" :key="item.id"></el-option>
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
              <el-select class="input_width_lg" v-model="MircroEnvForm.ostype" placeholder="请选择操作系统类型">
                <el-option label="windows" value="windows"></el-option>
                <el-option label="linux" value="linux"></el-option>
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
            <el-button type="primary" @click="editMicroEnv">保 存</el-button>
            <el-button @click="closeEditForm">取 消</el-button>
          </el-row>
        </el-form>
      </el-dialog>
      <!-- 导入弹窗 -->
      <el-dialog title="导入窗口" width="520px" :visible.sync="impDialog">
        <el-form>
          <el-row>
            <el-form-item style="margin-left:20px">
              <el-select class="input_width_lg" placeholder="请选择环境" v-model="importEnvId" filterable clearable>
                <el-option v-for="item in envList" :key="item.id" :label="item.envname" :value="item.id">
                </el-option>
              </el-select>
              <el-upload class="form_left" ref="upload"
                accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" :action="uploadAction"
                :headers="uploadHeaders" :data="uploadData" :before-upload="beforeUpload" :on-progress="uploadProgress"
                :on-success="uploadSuccess" :on-error="uploadError" :file-list="fileList" :auto-upload="false"
                :multiple="false">
                <el-button size="small" slot="trigger" type="primary" icon="el-icon-search">
                  选择文件
                </el-button>
                &nbsp;
                <el-button size="small" type="primary" @click="submitUpload" icon="el-icon-check">
                  确认上传
                </el-button>
                <el-button size="small" type="primary" @click="removeFile" icon="el-icon-refresh">
                  清空文件
                </el-button>
                <el-button size="small" type="primary" @click="exportFile" icon="el-icon-download">
                  下载模板
                </el-button>
                <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
              </el-upload>
            </el-form-item>
          </el-row>
        </el-form>
      </el-dialog>

      <!-- 导入错误信息列表 -->
      <el-dialog title="错误提示" :visible.sync="errDialog">
        <div class="text-danger" v-for="errMessage in errMessages" :key="errMessage.lineNum">
          第{{ errMessage.lineNum }}行：{{ errMessage.msg }}
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="errDialog = false">确定</el-button>
        </span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import {
    BASIC_API
  } from "../../../../constants/constants.js";
  export default {
    name: "MicroServiceManage",
    data() {
      return {
        envList: [],
        importEnvId: "",
        curpageIndex: 1,
        curpageSize: 10,
        impDialog: false,
        errDialog: false,
        totalpage: 0,
        addingDialog: false,
        editDialog: false,
        uploadHeaders: {},
        uploadData: { envid: ""},
        fileList: [],
        errMessages: "",
        formSearch: {
          queryEnvId: "",
          queryMicroEnvId: ""
        },
        uploadAction: "http://localhost:8081" + BASIC_API + "/microenv/import",
        MircroEnvForm: {
          MicroServiceId: "",
          EnvName: "",
          envList: [],
          MicroServiceNam: "",
          Remark: ""
        },
        MicroEnvTablelist: [],
        MicroEnvlist: [],
        rules: {
          EnvId: [{
            required: true,
            message: "请选择环境",
            trigger: "change"
          }],
          MicroServiceName: [{
            required: true,
            message: "请输入微服务名称",
            trigger: "blur"
          }],
          ipaddr: [{
            required: true,
            message: "请输入ip地址",
            trigger: "blur"
          }],
          ostype: [{
            required: true,
            message: "请选择操作系统",
            trigger: "change"
          }],
          loginuser: [{
            required: true,
            message: "请输入登录用户",
            trigger: "blur"
          }],
          loginpassword: [{
            required: true,
            message: "请输入登录密码",
            trigger: "blur"
          }],
          logpath: [{
            required: true,
            message: "请输入采集日志路径",
            trigger: "blur"
          }],
          lognamepattern: [{
            required: true,
            message: "请输入采集日志名称匹配规则(正则表达式)",
            trigger: "blur"
          }],
          logpattern: [{
            required: true,
            message: "采集日志起始行正则匹配方式",
            trigger: "blur"
          }],
          lognamepostfix: [{
            required: true,
            message: "请输入日志后缀名称",
            trigger: "blur"
          }]
        }
      };
    },
    methods: {
      getEnv() {
        var url =
          BASIC_API +
          "/env/queryEnvList";
        this.$httpWithMsg
          .get(url)
          .then(response => {
            console.log(response)
            this.envList = response.data;
          });
      },
      editMicroEnv() {
        this.$refs.editForm.validate(valid => {
          if (valid) {
            var url =
              BASIC_API +
              "/microenv/edit"
            var microinfo = {
              id: this.MircroEnvForm.id,
              envid: this.MircroEnvForm.EnvId,
              microservicename: this.MircroEnvForm.MicroServiceName,
              ostype: this.MircroEnvForm.ostype,
              ipaddr: this.MircroEnvForm.ipaddr,
              logpath: this.MircroEnvForm.logpath,
              lognamepattern: this.MircroEnvForm.lognamepattern,
              logpattern: this.MircroEnvForm.logpattern,
              lognamepostfix: this.MircroEnvForm.lognamepostfix,
              splitstr: this.MircroEnvForm.splitstr
            }
            this.$httpWithMsg
              .put(url, microinfo)
              .then(response => {
                console.log(response)
                this.editDialog = false;
                this.getEnvPage();
              });
          }

        });

      },
      addMicroEnv() {
        this.$refs.addingForm.validate(valid => {
          if (valid) {
            var url =
              BASIC_API +
              "/microenv/add"
            var microinfo = {
              envid: this.MircroEnvForm.EnvId,
              microservicename: this.MircroEnvForm.MicroServiceName,
              ostype: this.MircroEnvForm.ostype,
              ipaddr: this.MircroEnvForm.ipaddr,
              loginuser: this.MircroEnvForm.loginuser,
              loginpassword: this.MircroEnvForm.loginpassword,
              logpath: this.MircroEnvForm.logpath,
              lognamepattern: this.MircroEnvForm.lognamepattern,
              logpattern: this.MircroEnvForm.logpattern,
              lognamepostfix: this.MircroEnvForm.lognamepostfix,
              splitstr: this.MircroEnvForm.splitstr
            }
            this.$httpWithMsg
              .post(url, microinfo)
              .then(response => {
                console.log(response)
                this.addingDialog = false;
                this.getEnvPage();
              })
          }

        });

      },
      handleEdit(index, row) {
        this.editDialog = true
        this.MircroEnvForm = {
          id: row.id,
          EnvId: row.envid,
          MicroServiceName: row.microservicename,
          ostype: row.ostype,
          ipaddr: row.ipaddr,
          logpath: row.logpath,
          lognamepattern: row.lognamepattern,
          logpattern: row.logpattern,
          lognamepostfix: row.lognamepostfix,
          splitstr: row.splitstr
        }
      },
      handleDelete(index, row) {
        this.$confirm("是否删除微服务?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "error"
        }).then(() => {
          var url = BASIC_API + "/microenv/" + row.id;
          this.$httpWithMsg.delete(url).then(() => {
            this.$notify({
              type: "success",
              message: "删除微服务成功!"
            });
            this.getEnvPage();
          });
        });
      },
      async getEnvPage() {
        //获取微服务列表
        var param = new URLSearchParams(this.formSearch);
        var url =
          BASIC_API +
          "/microenv/microenvPage/" +
          (this.curpageIndex - 1) +
          "/" +
          this.curpageSize +
          "?" +
          param;
        this.$httpWithMsg
          .get(url)
          .then(response => {
            this.MicroEnvTablelist = response.data.content;
            this.totalpage = response.data.totalElements;
          })
      },
      handleadd() {
        this.addingDialog = true
        this.MircroEnvForm = {
          EnvId: "",
          MicroServiceName: "",
          ostype: "",
          ipaddr: "",
          loginuser: "",
          loginpassword: "",
          logpath: "",
          lognamepattern: "",
          logpattern: "",
          lognamepostfix: "",
          splitstr: ""
        }
      },
      changeEnv(val) {
        if (val === "") {
          return;
        }
        var url =
          BASIC_API +
          "/microenv/queryMicroByEnvId?queryEnvId=" + val;
        this.$httpWithMsg
          .get(url)
          .then(response => {
            this.MicroEnvlist = response.data;
          })
      },
      handleSizeChange(val) {
        this.curpageSize = val;
        this.curpageIndex = 1;
        this.getEnvPage();
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getEnvPage();
      },
      closeAddingForm() {
        this.$refs.addingForm.clearValidate();
        this.addingDialog = false
      },
      closeEditForm() {
        this.$refs.editForm.clearValidate();
        this.editDialog = false
      },
      beforeUpload(file) {
        console.log(file);
      },
      uploadProgress() {
        console.log("uploadProgress");
      },
      uploadSuccess(response) {
        if (!response.hasError) {
          this.$notify({
            message: "上传成功",
            type: "success"
          });
          this.fileLoading = false;
          this.impDialog = false;
          this.getEnvPage();
        } else {
          this.fileLoading = false;
          this.impDialog = false;
          this.errMessages = response.failRecords;
          this.errDialog = true;
        }
      },
      uploadError(response) {
        var json = JSON.parse(response.message);
        if (response.status == 500) {
          this.$notify({
            message: json.desc,
            type: "error"
          });
        }
        this.fileLoading = false;
      },
      //确定上传
      submitUpload() {
        if (!this.checkUpload()) {
          return false;
        }
        this.$refs.upload.submit();
        this.fileLoading = true;
      },
      checkUpload() {
        var fileList = this.$refs.upload.uploadFiles;
        if (this.importEnvId === "") {
          this.$notify({
            message: "必须选择环境",
            type: "error"
          });
          return false;
        } else {
          this.uploadData.envid = this.importEnvId;
        }
        if (fileList.length == 0) {
          this.$notify({
            message: "上传文件不能为空",
            type: "error"
          });
          return false;
        }
        if (fileList.length > 1) {
          this.$notify({
            message: "每次只能上传一个文件",
            type: "error"
          });
          return false;
        }
        for (let file of fileList) {
          if (!file.name.endsWith(".xlsx")) {
            this.$notify({
              message: "上传文件必须为xlsx格式",
              type: "error"
            });
            this.initUpload();
            return false;
          }
        }
        return true;
      },
      handleimport() {
        this.impDialog = true
      },
      //清空文件
      removeFile() {
        // this.fileList = [];
        this.$refs.upload.clearFiles();
      },
      //下载模板
      exportFile() {
        window.location.href="http://localhost:8081"+BASIC_API+"/microenv/importTemplate"

      }
    },
    created() {
      //获取当前路径
      if (JSON.stringify(this.$route.query) != '{}') {
        this.formSearch.queryEnvId = parseInt(this.$route.query.envid)
      }
      this.getEnvPage();
      this.getEnv();
    }
  };

</script>
<style scoped>
  .input_width_lg {
    width: 180px;
  }

</style>
