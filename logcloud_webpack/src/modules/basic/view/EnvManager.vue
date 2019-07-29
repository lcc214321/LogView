<template>
  <div class="env">
    <el-form inline :model="formSearch" label-width="70px" ref="primaryForm" style="float:left">
      <el-form-item label="环境名称">
        <el-input class="input_width_lg" placeholder="请输入环境名称" v-model="formSearch.queryEnvName" />
      </el-form-item>
      <el-form-item class="d-block">
        <el-button type="primary" size="small" icon="el-icon-search" @click="getEnvPage">查询</el-button>
        <el-button type="primary" size="small" icon="el-icon-plus" @click="handleadd">创建</el-button>
      </el-form-item>
    </el-form>
    <div class="Envtable">
      <el-table :data="Envlist" style="width: 100%" border>
        <el-table-column prop="envname" label="环境名称" width="180"></el-table-column>
        <el-table-column prop="remark" label="备注" width="180"></el-table-column>
        <el-table-column prop="updatetime" label="更新时间"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleMicroEdit(scope.$index, scope.row)">编辑微服务</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page.sync="curpageIndex" :page-sizes="[10, 20, 50, 100]" :page-size="10"
        layout="total, sizes, prev, pager, next, jumper" :total="totalpage"></el-pagination>
      <el-dialog title="新增环境" width="450px" :visible.sync="addingDialog" @close="closeAddingForm">
        <el-form :inline="true" inline-message :model="EnvForm" ref="addingForm" :rules="rules" label-position="right"
          label-width="120px">
          <el-row>
            <el-form-item label="环境名称" prop="EnvName">
              <el-input class="input_width_lg" v-model="EnvForm.EnvName" auto-complete="off" placeholder="请输入环境名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="备注信息" prop="Remark">
              <el-input class="input_width_lg" v-model="EnvForm.Remark" auto-complete="off" placeholder="请输入备注信息" />
            </el-form-item>
          </el-row>
          <el-row class="d-flex justify-content-center">
            <el-button type="primary" @click="addenv">保 存</el-button>
            <el-button @click="closeAddingForm">取 消</el-button>
          </el-row>
        </el-form>
      </el-dialog>
      <el-dialog title="编辑环境" width="450px" :visible.sync="editDialog" @close="closeEditForm">
        <el-form :inline="true" inline-message :model="EnvForm" ref="editForm" :rules="rules" label-position="right"
          label-width="120px">
          <el-row>
            <el-form-item label="环境名称" prop="EnvName">
              <el-input class="input_width_lg" v-model="EnvForm.EnvName" auto-complete="off" placeholder="请输入环境名称" />
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="备注信息" prop="Remark">
              <el-input class="input_width_lg" v-model="EnvForm.Remark" auto-complete="off" placeholder="请输入备注信息" />
            </el-form-item>
          </el-row>
          <el-row class="d-flex justify-content-center">
            <el-button type="primary" @click="editenv">保 存</el-button>
            <el-button @click="closeEditForm">取 消</el-button>
          </el-row>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>
<script>
  import {
    BASIC_API
  } from "../../../../constants/constants.js";
  export default {
    data() {
      return {
        formSearch: {
          queryEnvName: ""
        },
        curpageIndex: 1,
        curpageSize: 10,
        addingDialog: false,
        editDialog: false,
        queryEnvName: "",
        EnvForm: {
          EnvName: "",
          Remark: ""
        },
        Envlist: [],
        EnvPage: [],
        totalpage: 0,
        rules: {
          EnvName: [{
            required: true,
            message: "请输入环境名称",
            trigger: "blur"
          }]
        }
      };
    },
    async created() {
      this.getEnvPage();
    },
    methods: {
      async getEnvPage() {
        var param = new URLSearchParams(this.formSearch);
        var url =
          BASIC_API +
          "/env/envPage/" +
          (this.curpageIndex - 1) +
          "/" +
          this.curpageSize +
          "?" +
          param;
        this.userPage = this.$httpWithMsg
          .get(url)
          .then(response => {
            this.Envlist = response.data.content;
            this.totalpage = response.data.totalElements;
          })
          .then(() => {
            console.log(123);
          });
      },
      handleadd() {
        this.EnvForm = {
          EnvName: "",
          Remark: ""
        };
        this.addingDialog = true;
      },
      addenv() {
        this.$refs.addingForm.validate(valid => {
          if (valid) {
            var url = BASIC_API + "/env/add";
            var userinfo = {
              envname: this.EnvForm.EnvName,
              remark: this.EnvForm.Remark
            };
            this.$httpWithMsg.post(url, userinfo).then(() => {
              this.$notify({
                type: "success",
                message: "新增环境成功！"
              });
              this.getEnvPage();
              this.addingDialog = false;
            });
          }

        });

      },
      handleEdit(index, row) {
        this.editDialog = true;
        this.EnvForm = {
          EnvName: row.envname,
          Remark: row.remark,
          EnvId: row.id
        };
      },
      handleDelete(index, row) {
        this.$confirm("是否删除环境?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "error"
        }).then(() => {
          var url = BASIC_API + "/env/" + row.id;
          this.$httpWithMsg.delete(url).then(() => {
            this.$notify({
              type: "success",
              message: "删除环境成功!"
            });
            this.getEnvPage();
          });
        });
      },
      handleCurrentChange(val) {
        this.currentPage = val;
        this.getEnvPage();
      },
      handleSizeChange(val) {
        this.curpageSize = val;
        this.curpageIndex = 1;
        this.getEnvPage();
      },
      handleMicroEdit(index, row) {
        //将param付在url上一起传过去
        this.$router.replace({
          path: "/basic/micro?envid=" + row.id
        });
      },
      editenv() {
        this.$refs.editForm.validate(valid => {
          if (valid) {
            var url = BASIC_API + "/env/edit";
            var envinfo = {
              id: this.EnvForm.EnvId,
              envname: this.EnvForm.EnvName,
              remark: this.EnvForm.Remark
            };
            this.$httpWithMsg.put(url, envinfo).then(() => {
              this.$notify({
                type: "success",
                message: "修改用户成功！"
              });
              this.getEnvPage();
              this.editDialog = false;
            });
          }
        });

      },
      closeAddingForm(){
        this.$refs.addingForm.clearValidate();
        this.addingDialog=false
      },
      closeEditForm(){
        this.$refs.editForm.clearValidate();
        this.editDialog=false
      }
    }
  };

</script>
